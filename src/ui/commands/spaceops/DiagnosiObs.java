package ui.commands.spaceops;

import java.util.Map.Entry;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import comportamental_fsm.labels.ObservationsList;
import fsm_algorithms.RelevanceRegexBuilder;
import spazio_comportamentale.SpaceTransition;
import spazio_comportamentale.oss_lineare.BuilderSpaceComportamentaleObsLin;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpaceStateObs;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.UserWait;
import utility.Constants;

public class DiagnosiObs implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
		if(context.getCurrentNet().computedObservationsLenght() == 0) {
			context.getIOStream().writeln("Nessuna Osservazione Lineare calcolata per questa rete");
			return false;
		}
		
		context.getIOStream().writeln(context.getCurrentNet().generatedObsSpacesDescription());
		
		int index = 0;
		do {
			String ans = context.getIOStream().read("Inserire indice della Osservazione Lineare (oppure inserire 'exit' per uscire): ");
			if(ans.equals("exit"))
				return false;		
			if(ans.matches("\\d+"))
				index = Integer.parseInt(ans);				
			else
				index = -1;
			
		}while(index < 0 || index >= context.getCurrentNet().computedObservationsLenght());
		
		Entry<ObservationsList, SpaceAutomaObsLin> obsSpace = context.getCurrentNet().getSpaceObsByIndex(index);
		
		String result = null;	
			
		String ans = context.getIOStream().yesOrNo("Vuoi inserire un tempo massimo per l'esecuzione?");
		long maxTime = 0;
		
		if(ans.equals("y")) {
			String maxString = "";
			do {
				maxString = context.getIOStream().read("Inserire un tempo massimo in secondi per l'esecuzione: ");			
			} while(!maxString.matches("\\d+"));
			maxTime = Long.parseLong(maxString);
		}
		
		long start = System.currentTimeMillis();

		RelevanceRegexBuilder<SpaceStateObs, SpaceTransition<SpaceStateObs>> diagnosi = 
				new RelevanceRegexBuilder<SpaceStateObs, SpaceTransition<SpaceStateObs>>(obsSpace.getValue(), new BuilderSpaceComportamentaleObsLin());	
		
		ExecutorService executor = Executors.newFixedThreadPool(2);		
		Future<String> futureDiagnosi = executor.submit(diagnosi);
		UserWait<String> uw = new UserWait<String>(context.getIOStream(), futureDiagnosi);
		Future<String> futureUserResponse = executor.submit(uw);				
		
		Thread task = new Thread() {			
			public void run() {
				while(true) {
					if(futureDiagnosi.isDone()) {
						futureUserResponse.cancel(true);
						return;
					}
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						return;
					}
				}
			}               
		};

		task.start();
		
		try {
			if(maxTime == 0)
				futureUserResponse.get();
			else
				futureUserResponse.get(maxTime, TimeUnit.SECONDS);
			
			if(!futureDiagnosi.isDone())
				futureDiagnosi.cancel(true);
		} catch (InterruptedException | CancellationException e) {
			
			context.getIOStream().writeln("");
		} catch (ExecutionException e1) {
			context.getIOStream().writeln("ERRORE: Impossibile completare l'esecuzione!");
			return false;
		} catch (TimeoutException e1) {
			context.getIOStream().writeln("\nTempo Massimo Scaduto!");
		}


		try {
			if(futureDiagnosi.isDone() && !futureDiagnosi.isCancelled()) {
				if(maxTime == 0)
					result = futureDiagnosi.get(maxTime, TimeUnit.SECONDS);
				else
					result = futureDiagnosi.get();
				if(!futureUserResponse.isDone())
					futureUserResponse.cancel(true);
			}
			else
				result = diagnosi.midResult();
		} catch (InterruptedException | TimeoutException e) {
			result = diagnosi.midResult();
			futureDiagnosi.cancel(true);
		} catch (ExecutionException e) {
			context.getIOStream().writeln("ERRORE: Impossibile completare l'esecuzione!");
			return true;
		}	
		executor.shutdownNow();

		long finish = System.currentTimeMillis();
		double elapsed =  (double)(finish - start)/1000;
		context.getIOStream().writeln(String.format("Diagnosi trovata per l'osservazione %s: %s\nTempo impiegato %.2fs", obsSpace.getKey(), result, elapsed));
		
		return true;
	}

}