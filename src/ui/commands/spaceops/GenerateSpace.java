package ui.commands.spaceops;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpazioComportamentale;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import ui.context.UserWait;
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

		CurrentNet net = context.getCurrentNet();	
		
		SpaceAutomaComportamentale result = null;	
			
		if(net.hasComportamentalSpace()) {
			result = net.getComportamentalSpace();
			context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO:\n*****************************************************");
			context.getIOStream().writeln(result.toString());		
			return true;
		}
		
		String ans = context.getIOStream().yesOrNo("Vuoi inserire un tempo massimo per l'esecuzione?");
		long maxTime = 0;
		
		if(ans.equals("y")) {
			String maxString = "";
			do {
				maxString = context.getIOStream().read("Inserire un tempo massimo in secondi per l'esecuzione: ");			
			} while(!maxString.matches("\\d+"));
			maxTime = Long.parseLong(maxString);
		}
		
		SpazioComportamentale spaceComp = new SpazioComportamentale(net.getNet());
		
		
		ExecutorService executor = Executors.newFixedThreadPool(2);		
		Future<SpaceAutomaComportamentale> futureSAC = executor.submit(spaceComp);
		UserWait<SpaceAutomaComportamentale> uw = new UserWait<SpaceAutomaComportamentale>(context.getIOStream(), futureSAC);
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
				result = spaceComp.midResult();
		} catch (InterruptedException | TimeoutException e) {
			result = spaceComp.midResult();
			futureSAC.cancel(true);
		} catch (ExecutionException e) {
			context.getIOStream().writeln("ERRORE: Impossibile completare l'esecuzione!");
			return true;
		}	
		executor.shutdownNow();
		context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO:\n*****************************************************");
		context.getIOStream().writeln(result.toString());
		context.getCurrentNet().setSpaceAutomaComportamentale(result);
		
		return true;
	}

}
