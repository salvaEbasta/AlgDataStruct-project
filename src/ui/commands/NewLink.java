package ui.commands;

import comportamentale_fa.ComportamentaleFA;
import comportamentale_fa.Link;
import ui.Context;

public class NewLink implements CommandInterface, OneParameter{

	private static final int CFAS_REQUIRED = 2;

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		if(context.hasSavedLink(id)) {
			context.getIOStream().writeln(String.format("ERRORE: Un Link con id %s è già presente!", id));
			return false;
		}
		if(context.savedCFAsSize() < CFAS_REQUIRED) {
			context.getIOStream().writeln(String.format("ERRORE: Non sono stati creati CFA sufficienti per poter creare un Link!", id));
			return false;
		}
		context.getIOStream().writeln(context.savedCFAsList());
		ComportamentaleFA source = getCFA(context, "Indicare l'id del CFA sorgente (oppure 'exit' per annullare): ");
		if(source == null)
			return false;
		ComportamentaleFA destination = getCFA(context, "Indicare l'id del CFA destinazione (oppure 'exit' per annullare): ");
		if(destination == null)
			return false;
		Link l = new Link(id, source, destination);
		boolean added = context.saveLink(l);
		if(added)
			context.getIOStream().writeln(String.format("Nuovo Link con id %s creato correttamente!", id));
		else
			context.getIOStream().writeln(String.format("ERRORE: Impossibile salvare il Link!", id));
		return added;
	}
	
	private ComportamentaleFA getCFA(Context context, String message) {
		boolean found = false;
		String id = null;
		do {
			id = context.getIOStream().read(message);
			if(id.equals("exit"))
				return null;
			found = context.hasSavedCFA(id);
		} while(!found);
		return context.getSavedCFAfromId(id);
	}

}
