package ui.commands.spacecomp;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import comportamental_fsm.labels.ObservableLabel;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpazioComportamentale;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpazioComportamentaleObs;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import ui.context.UserWait;
import utility.Constants;

public class GenerateSpaceObs implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
		CurrentNet net = context.getCurrentNet();				
		ArrayList<ObservableLabel> labelList = new ArrayList<ObservableLabel>();
		String ans = "";
		context.getIOStream().writeln(context.getCurrentNet().obsLabel());
		do {
			String label = context.getIOStream().read("Inserire nome per l'etichetta di Osservabilità: ");
			if(context.getCurrentNet().hasObservableLabel(label)) {
				labelList.add(new ObservableLabel(label));
				ans = context.getIOStream().yesOrNo("Inserire un'altra label di Osservabilità? ");
			} else 
				context.getIOStream().writeln("La label di Osservabilità inserita non esiste nella rete!");
		}while(!ans.equalsIgnoreCase("n"));
		
		ObservableLabel[] obsList = labelList.toArray(new ObservableLabel[0]);
		
		SpaceAutomaObsLin result = null;	
		if(net.hasGeneratedSpaceObs(obsList)) {
			result = net.getGeneratedSpaceObs(obsList);
			context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO per osservazione " + 
					labelList.toString() + ":\n*****************************************************");
			context.getIOStream().writeln(result.toString());		
			return true;
		}

		ans = context.getIOStream().yesOrNo("Vuoi inserire un tempo massimo per l'esecuzione?");
		long maxTime = 0;
		
		if(ans.equals("y")) {
			String maxString = "";
			do {
				maxString = context.getIOStream().read("Inserire un tempo massimo in secondi per l'esecuzione: ");			
			} while(!maxString.matches("\\d+"));
			maxTime = Long.parseLong(maxString);
		}
	
		
		
		SpazioComportamentaleObs spaceComp = new SpazioComportamentaleObs(net.getNet(), obsList);		
		
		ExecutorService executor = Executors.newFixedThreadPool(2);		
		Future<SpaceAutomaObsLin> futureSAC = executor.submit(spaceComp);
		UserWait<SpaceAutomaObsLin> uw = new UserWait<SpaceAutomaObsLin>(context.getIOStream(), futureSAC);
		Future<String> futureUserResponse = executor.submit(uw);		

		Thread task = new Thread() {			
			public void run() {
				while(true) {
					if(futureSAC.isDone()) {
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
			
			if(!futureSAC.isDone())
				futureSAC.cancel(true);
		} catch (InterruptedException | CancellationException e) {
			//uw.close();
			context.getIOStream().writeln("");
		} catch (ExecutionException e1) {
			context.getIOStream().writeln("ERRORE: Impossibile completare l'esecuzione!");
			return false;
		} catch (TimeoutException e1) {
			context.getIOStream().writeln("\nTempo Massimo Scaduto!");
		}


		try {
			if(futureSAC.isDone() && !futureSAC.isCancelled()) {
				if(maxTime == 0)
					result = futureSAC.get(maxTime, TimeUnit.SECONDS);
				else
					result = futureSAC.get();
				if(!futureUserResponse.isDone())
					futureUserResponse.cancel(true);
			}
			else
				result = spaceComp.getMidSpazioComportamentale();
		} catch (InterruptedException | TimeoutException e) {
			result = spaceComp.getMidSpazioComportamentale();
			futureSAC.cancel(true);
		} catch (ExecutionException e) {
			context.getIOStream().writeln("ERRORE: Impossibile completare l'esecuzione!");
			return false;
		}	
	
		context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO per osservazione " + 
						labelList.toString() + ":\n*****************************************************");
		context.getIOStream().writeln(result.toString());
		
		context.getCurrentNet().addObservation(obsList, result);
		executor.shutdownNow();
		return true;
	}

}
