package ui.context;

import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import algorithm_interfaces.Algorithm;
import ui.stream.InOutStream;

public class StoppableOperation {
	
	
	public <O, A extends Algorithm<O>> Entry<O, Performance> compute(InOutStream io, A algorithm) {
		
		O result = null;
		
		String ans = io.yesOrNo("Vuoi inserire un tempo massimo per l'esecuzione?");
		long maxTime = 0;
		
		if(ans.equals("y")) {
			String maxString = "";
			do {
				maxString = io.read("Inserire un tempo massimo in secondi per l'esecuzione: ");			
			} while(!maxString.matches("\\d+"));
			maxTime = Long.parseLong(maxString);
		}
		
		Performance stats = new Performance();
		stats.start();
		
		ExecutorService executor = Executors.newSingleThreadExecutor();		
		Future<O> future = executor.submit(algorithm);
		
		boolean stopped = false;
		
		Thread inputThread = new Thread(new Runnable() {

			@Override
			public void run() {
				if(!future.isDone()) {
					String ans = "";
					while(!ans.equals("stop") && !future.isDone()) {
						ans = io.read("Esecuzione in corso... Inserire 'stop' per terminare: ");	
						if(ans.equals("stop") && !future.isDone()) {
							future.cancel(true);
							return;
						}
				}	}
					
				
			}
			
		});
		inputThread.start();
		
		try {
			if(maxTime != 0)
				result = future.get(maxTime, TimeUnit.SECONDS);
			else
				result = future.get();
			
			stats.stop();
			io.writeln("\nEsecuzione completata, premi INVIO per vedere il risultato!");		
			while(!inputThread.getState().equals(Thread.State.TERMINATED));
		} catch (InterruptedException e) {
			stats.stop();
			result = algorithm.midResult();
			future.cancel(true);
			stopped = true;
		} catch (ExecutionException e) {
			stats.stop();
			e.printStackTrace();
			io.writeln("ERRORE: Impossibile eseguire l'operazione! Premi INVIO per continuare");
			while(!inputThread.getState().equals(Thread.State.TERMINATED));
			return null;
		} catch (TimeoutException e) {
			stats.stop();
			io.writeln("\nEsecuzione completata, premi INVIO per vedere il risultato!");		
			result = algorithm.midResult();
			future.cancel(true);
			stopped = true;
			while(!inputThread.getState().equals(Thread.State.TERMINATED));
		}catch (CancellationException e) {
			result = algorithm.midResult();
			stopped = true;
		}
		
		stats.stop();
		stats.setStopped(stopped);		
		
		if(stopped) {
			io.writeln("--------------------------------------------------------------");	
			io.writeln("|ATTENZIONE: il risultato ottenuto è un risultato intermedio!|");	
			io.writeln("--------------------------------------------------------------\n");	
		}

		io.writeln(String.format("Tempo Impiegato: %.2fs\n", stats.getTime()));
		io.writeln(String.format("Spazio Occupato: %.2fMB\n", stats.getSpace()));		
				
		
		 return new AbstractMap.SimpleEntry<O, Performance>(result, stats);
	}

}
