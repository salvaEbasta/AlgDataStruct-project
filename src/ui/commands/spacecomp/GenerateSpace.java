package ui.commands.spacecomp;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceCompCallable;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import utility.Constants;

public class GenerateSpace implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
		String maxString = null;
		do {
			maxString = context.getIOStream().read("Inserire un tempo massimo in secondi per l'esecuzione: ");			
		} while(!maxString.matches("\\d+"));
		long maxTime = Long.parseLong(maxString);
		
		CurrentNet net = context.getCurrentNet();
		
		ExecutorService executor = Executors.newSingleThreadExecutor();		
		
		SpaceAutomaComportamentale spazio = null;
		try {
			spazio = executor.submit(new SpaceCompCallable(net.getNet())).get(maxTime, TimeUnit.SECONDS);
			String ans = null;
			do {
				ans = context.getIOStream().read("Esecuzione in corso... (Inserire 'stop' per terminare)");	
				if(ans.equals("stop")) {
					executor.shutdownNow();
				}
			} while (!ans.equals("stop") || !executor.isTerminated());
		} catch (InterruptedException e) {
			context.getIOStream().writeln("Esecuzione Annullata!");
		} catch (ExecutionException e) {
			context.getIOStream().writeln("ERRORE: Impossibile completare l'esecuzione!");
			return false;
		} catch (TimeoutException e) {
			context.getIOStream().writeln("Tempo Massimo Scaduto!");
		}
		
		
	
		context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO:\n*****************************************************");
		context.getIOStream().writeln(spazio.toString());
		
		return true;
	}

}
