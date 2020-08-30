package ui.commands.spaceops;

import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.ObservationsList;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import utility.Constants;

public class NewLinObs implements CommandInterface, NoParameters{

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
		
		context.getIOStream().writeln("La nuova LinOss: "+obsList);
		if(context.getCurrentNet().addLinObs(obsList)) {
			context.getIOStream().writeln("L'osservazione lineare "+obsList+" è stata aggiunta con successo");
		}else
			context.getIOStream().writeln("Impossibile aggiungere "+obsList+": osservazione già inserita.");
		
		return true;
	}

}
