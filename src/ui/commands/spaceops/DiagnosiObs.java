package ui.commands.spaceops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import comportamental_fsm.labels.ObservationsList;
import fsm_algorithms.RelevanceRegexBuilder;
import spazio_comportamentale.SpaceTransition;
import spazio_comportamentale.oss_lineare.BuilderSpaceComportamentaleObsLin;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpaceStateObs;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.Stats;
import ui.context.StoppableOperation;
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
		
		if(context.getCurrentNet().observationsNumber() == 0) {
			context.getIOStream().writeln("Nessuna Osservazione Lineare calcolata per questa rete");
			return false;
		}
		
		
		StringBuilder sb = new StringBuilder("Spazi generati per la rete:\n");
		ArrayList<ObservationsList> obsList = new ArrayList<>();
		HashMap<ObservationsList, SpaceAutomaObsLin> obsMap = context.getCurrentNet().osbSpaces();
		Iterator<ObservationsList> iter = obsMap.keySet().iterator();
		while(iter.hasNext()) {
			ObservationsList obs = iter.next();
			sb.append(String.format("%d - %s\n", obsList.size(), obsMap.get(obs).id()));
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
			
		}while(index < 0 || index >= obsList.size());
		
		ObservationsList obs = obsList.get(index);
		SpaceAutomaObsLin obsSpace = obsMap.get(obs);
		
		RelevanceRegexBuilder<SpaceStateObs, SpaceTransition<SpaceStateObs>> diagnosi = 
				new RelevanceRegexBuilder<SpaceStateObs, SpaceTransition<SpaceStateObs>>(obsSpace, new BuilderSpaceComportamentaleObsLin());		
		
	
		Entry<String, Stats> result = new StoppableOperation().compute(context.getIOStream(), diagnosi);
		if(result == null)
			return false;
		
		context.getIOStream().writeln(String.format("Diagnosi Lineare trovata per osservazione %s: %s", obs, result.getKey()));
		
		boolean stopped = result.getValue().wasStopped();
		
		if(!stopped)
			context.getCurrentNet().addObsSpaceResult(obs, result.getKey(), result.getValue().getTime(), result.getValue().getSpace());
		
		
		return true;
	}

}