package ui.commands.spaceops;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import diagnosticatore.ClosureSpace;
import diagnosticatore.algorithms.DiagnosticatoreBuilder;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.UserWait;
import utility.Constants;

public class GenerateDiagnosticatore implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
		if(!context.getCurrentNet().hasComportamentalSpace()) {
			context.getIOStream().writeln("Per costruire il diagnosticatore è necessario disporre dello spazio comportamentale");
			context.getIOStream().writeln("Genera lo spazio comportamentale\n");
			return false;
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
		
		long start = System.currentTimeMillis();
		DiagnosticatoreBuilder diagnostBuilder = new DiagnosticatoreBuilder(context.getCurrentNet().getComportamentalSpace());
		
		ExecutorService executor = Executors.newFixedThreadPool(2);		
		Future<ClosureSpace> futureDiagnosticatore = executor.submit(diagnostBuilder);
		UserWait<ClosureSpace> uw = new UserWait<ClosureSpace>(context.getIOStream(), futureDiagnosticatore);
		Future<String> futureUserResponse = executor.submit(uw);				
		
		ClosureSpace diagnosticatore = null;
		
		Thread task = new Thread() {			
			public void run() {
				while(true) {
					if(futureDiagnosticatore.isDone()) {
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
			
			if(!futureDiagnosticatore.isDone())
				futureDiagnosticatore.cancel(true);
		} catch (InterruptedException | CancellationException e) {
			
			context.getIOStream().writeln("");
		} catch (ExecutionException e1) {
			context.getIOStream().writeln("ERRORE: Impossibile completare l'esecuzione!");
			return false;
		} catch (TimeoutException e1) {
			context.getIOStream().writeln("\nTempo Massimo Scaduto!");
		}


		try {
			if(futureDiagnosticatore.isDone() && !futureDiagnosticatore.isCancelled()) {
				if(maxTime == 0)
					diagnosticatore = futureDiagnosticatore.get(maxTime, TimeUnit.SECONDS);
				else
					diagnosticatore = futureDiagnosticatore.get();
				if(!futureUserResponse.isDone())
					futureUserResponse.cancel(true);
			}
			else
				diagnosticatore = diagnostBuilder.midResult();
		} catch (InterruptedException | TimeoutException e) {
			diagnosticatore = diagnostBuilder.midResult();
			futureDiagnosticatore.cancel(true);
		} catch (ExecutionException e) {
			context.getIOStream().writeln("ERRORE: Impossibile completare l'esecuzione!");
			return true;
		}	
		executor.shutdownNow();
		context.getCurrentNet().setDiagnosticatore(diagnosticatore);
		context.getIOStream().writeln(String.format("Diagnosticatore calcolato correttamente!"));
		context.getIOStream().writeln(diagnosticatore.toString());
		
		return true;
	}
}
