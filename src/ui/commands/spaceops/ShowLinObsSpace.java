package ui.commands.spaceops;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import comportamental_fsm.labels.ObservationsList;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import utility.Constants;

public class ShowLinObsSpace implements CommandInterface, NoParameters{

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
		
		
		if(context.getCurrentNet().hasLinObsCompSpace(obs)) {
			SpaceAutomaObsLin spaceAutoma = context.getCurrentNet().getLinObsCompSpace(obs);
			context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE relativo a "+obs+":\n*****************************************************");
			context.getIOStream().writeln("Risorse utilizzate:");
			context.getIOStream().writeln(String.format("\t* tempo: %.2f", context.getCurrentNet().obsSpaceGenTime(obs)));
			context.getIOStream().writeln(String.format("\t* spazio: %.2f", context.getCurrentNet().obsSpaceGenSpace(obs)));
			context.getIOStream().writeln(spaceAutoma.toString());	
			return true;
		} else {
			context.getIOStream().writeln("Non è ancora stato generato uno spazio comportamentale relativo all'osservazione "+obs);
			return true;
		}
	}

}
