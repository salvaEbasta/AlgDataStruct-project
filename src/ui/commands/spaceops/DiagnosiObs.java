package ui.commands.spaceops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import comportamental_fsm.labels.ObservationsList;
import fsm_algorithms.MultipleRelRegexBuilder;
import spazio_comportamentale.SpaceTransition;
import spazio_comportamentale.oss_lineare.BuilderSpaceComportamentaleObsLin;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpaceStateObs;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.Performance;
import ui.context.StoppableOperation;
import utility.Constants;
import utility.RegexSimplifier;

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
		
		
		StringBuilder sb = new StringBuilder("Osservazioni Lineari disponibili:\n");
		ArrayList<ObservationsList> obsList = new ArrayList<>();
		Set<ObservationsList> obsSet = context.getCurrentNet().linObss();
		Iterator<ObservationsList> iter = obsSet.iterator();
		while(iter.hasNext()) {
			ObservationsList obs = iter.next();
			if(context.getCurrentNet().hasLinObsCompSpace(obs)) {
				sb.append(String.format("%d - %s\n", obsList.size(), obs));
				obsList.add(obs);
			}
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
		SpaceAutomaObsLin obsSpace = context.getCurrentNet().getLinObsCompSpace(obs);
		
		MultipleRelRegexBuilder<SpaceStateObs, SpaceTransition<SpaceStateObs>> diagnosi = 
				new MultipleRelRegexBuilder<SpaceStateObs, SpaceTransition<SpaceStateObs>>(new SpaceAutomaObsLin(obsSpace), new BuilderSpaceComportamentaleObsLin());		
		
		
		Entry<HashMap<SpaceStateObs, String>, Performance> result = new StoppableOperation().compute(context.getIOStream(), diagnosi);
		if(result == null)
			return false;
		
		Iterator<String> iterator = result.getKey().values().iterator();
		String relDiagnosi = null;
		if(iterator.hasNext()) {
			StringBuilder sbDiagn = new StringBuilder(iterator.next());
			while(iterator.hasNext())
				sbDiagn.append("|").append(iterator.next());
			relDiagnosi = new RegexSimplifier().simplify(sbDiagn.toString(), context.getCurrentNet().getNet().getRelevantLabels());
		} else 
			relDiagnosi = Constants.EPSILON;
			
		context.getIOStream().writeln(String.format("Diagnosi Lineare trovata per osservazione %s: %s", obs, relDiagnosi));
		
		boolean stopped = result.getValue().wasStopped();
		
		if(!stopped)
			context.getCurrentNet().addObsSpaceResult(obs, relDiagnosi, result.getValue().getTime(), result.getValue().getSpace());
		
		
		return true;
	}

}