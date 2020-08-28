package ui.commands.spaceops;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import comportamental_fsm.labels.ObservationsList;
import diagnosticatore.algorithms.LinearDiagnosis;
import spazio_comportamentale.SpazioComportamentale;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.Stats;
import ui.context.StoppableOperation;
import utility.Constants;

public class DiagnosiObsDiagn  implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
		if(context.getCurrentNet().observationsNumber() == 0) {
			context.getIOStream().writeln("Nessuna Osservazione Lineare calcolata per questa rete");
			return false;
		}
		
		if(!context.getCurrentNet().hasDiagnosticatore()) {
			context.getIOStream().writeln("Il diagnosticatore per questa rete è non definito.\nPer poter procedere è necessario aver prima generato il diagnosticatore");
			return false;
		}
		
		context.getIOStream().writeln(context.getCurrentNet().getDiagnosticatore().toString());
		
		
		StringBuilder sb = new StringBuilder("Osservazioni disponibili per la rete:\n");
		ArrayList<ObservationsList> obsList = new ArrayList<>();
		Set<ObservationsList> obsSet = context.getCurrentNet().observations();
		Iterator<ObservationsList> iter = obsSet.iterator();
		while(iter.hasNext()) {
			ObservationsList obs = iter.next();
			sb.append(String.format("%d - %s\n", obsList.size(), obs.toString()));
			obsList.add(obs);
		}
		
		context.getIOStream().writeln(sb.toString());
		
		
		int index = 0;
		do {
			String ans = context.getIOStream().read("Inserire indice dell' Osservazione Lineare (oppure inserire 'exit' per uscire): ");
			if(ans.equals("exit"))
				return false;		
			if(ans.matches("\\d+"))
				index = Integer.parseInt(ans);				
			else
				index = -1;
		}while(index < 0 || index >= context.getCurrentNet().observationsNumber());
		
		ObservationsList obs = obsList.get(index);
		
		LinearDiagnosis diagnosi = new LinearDiagnosis(context.getCurrentNet().getDiagnosticatore(), obs);		
		
	
		Entry<String, Stats> result = new StoppableOperation().compute(context.getIOStream(), diagnosi);
		if(result == null)
			return false;
		
		context.getIOStream().writeln(String.format("Diagnosi Lineare (con Diagnosticatore) trovata per osservazione %s: %s", obs, result.getKey()));
		
		boolean stopped = result.getValue().wasStopped();
		
		if(!stopped)
			context.getCurrentNet().addDiagnosticatoreResult(obs, result.getKey(), result.getValue().getTime(), -1);
		
		return true;
	}

}