package ui.commands.spaceops;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import comportamental_fsm.labels.ObservationsList;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpazioComportamentaleObs;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import ui.context.Performance;
import ui.context.StoppableOperation;
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
		StringBuilder sb = new StringBuilder("Osservazioni Lineari disponibili:\n");
		ArrayList<ObservationsList> obsList = new ArrayList<>();
		Set<ObservationsList> obsSet = context.getCurrentNet().linObss();
		Iterator<ObservationsList> iter = obsSet.iterator();
		while(iter.hasNext()) {
			ObservationsList obs = iter.next();
			sb.append(String.format("%d - %s\n", obsList.size(), obs));
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

		Entry<SpaceAutomaObsLin, Performance> result = null;			
		
		SpazioComportamentaleObs spaceComp = new SpazioComportamentaleObs(net.getNet(), obs);
	
		result = new StoppableOperation().compute(context.getIOStream(), spaceComp);
		if(result == null)
			return false;
		
		boolean stopped = result.getValue().wasStopped();
		
		boolean notraettoria = false;
		if(!stopped) {
			if(result.getKey().states().size() == 1 && result.getKey().transitions().isEmpty())
				notraettoria = true;
			else {
				context.getCurrentNet().addObservation(obs, result.getKey());
				context.getCurrentNet().setObsSpaceGenerationPerformance(obs, result.getValue().getTime(), result.getValue().getSpace());
			}
		}
		
		
		if(!notraettoria) {
			context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO per osservazione " + 
					obs.toString() + ":\n*****************************************************");
			context.getIOStream().writeln(result.getKey().toString());
		}
		else
			context.getIOStream().writeln("ERRORE: Non esiste nessuna traettoria nello spazio per l'osservazione lineare ".concat(obs.toString()));
		
		
		return true;

	}

}
