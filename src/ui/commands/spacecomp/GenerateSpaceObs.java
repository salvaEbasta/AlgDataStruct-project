package ui.commands.spacecomp;

import java.util.ArrayList;

import comportamental_fsm.labels.ObservableLabel;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
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
		ArrayList<ObservableLabel> labelList = new ArrayList<ObservableLabel>();
		String ans = "";
		context.getIOStream().writeln(context.getCurrentNet().obsLabel());
		do {
			String label = context.getIOStream().read("Inserire nome per l'etichetta di Osservabilità: ");
			if(context.getCurrentNet().hasObservableLabel(label)) {
				labelList.add(new ObservableLabel(label));
				ans = context.getIOStream().yesOrNo("Inserire un'altra label di Osservabilità? ");
			} else 
				context.getIOStream().writeln("La label di Osservabilità inserita non esiste nella rete!");
		}while(!ans.equalsIgnoreCase("n"));

		SpaceAutomaObsLin space = net.generateSpaceObs(labelList.toArray(new ObservableLabel[0]));
		context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO per osservazione " + 
						labelList.toString() + ":\n*****************************************************");
		context.getIOStream().writeln(space.toString());
		
		return true;
	}

}
