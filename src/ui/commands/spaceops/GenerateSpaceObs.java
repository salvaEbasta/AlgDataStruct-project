package ui.commands.spaceops;

import java.util.Map.Entry;
import comportamental_fsm.labels.ObservableLabel;
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
		ObservationsList obsList = new ObservationsList();
		String ans = "";
		context.getIOStream().writeln(context.getCurrentNet().obsLabel());
		do {
			String label = context.getIOStream().read("Inserire nome per l'etichetta di Osservabilità: ");
			if(context.getCurrentNet().hasObservableLabel(label)) {
				obsList.add(new ObservableLabel(label));
				ans = context.getIOStream().yesOrNo("Inserire un'altra label di Osservabilità? ");
			} else 
				context.getIOStream().writeln("La label di Osservabilità inserita non esiste nella rete!");
		}while(!ans.equalsIgnoreCase("n"));
		
		
		if(net.hasLinObsCompSpace(obsList)) {
			SpaceAutomaObsLin spaceAutoma = net.getLinObsCompSpace(obsList);
			context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO per osservazione " + 
					obsList.toString() + ":\n*****************************************************");
			context.getIOStream().writeln(spaceAutoma.toString());		
			return true;
		}

		Entry<SpaceAutomaObsLin, Performance> result = null;			
		
		SpazioComportamentaleObs spaceComp = new SpazioComportamentaleObs(net.getNet(), obsList);
	
		result = new StoppableOperation().compute(context.getIOStream(), spaceComp);
		if(result == null)
			return false;
		
		context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO per osservazione " + 
				obsList.toString() + ":\n*****************************************************");
		context.getIOStream().writeln(result.getKey().toString());
		
		boolean stopped = result.getValue().wasStopped();
		
		if(!stopped)
			context.getCurrentNet().addObservation(obsList, result.getKey());
		
		return true;

	}

}
