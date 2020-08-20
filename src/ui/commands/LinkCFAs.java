package ui.commands;

import comportamentale_fa.ComportamentaleFA;
import ui.Context;

public class LinkCFAs implements CommandInterface, OneParameter{

	private static final int CFAS_REQUIRED = 2;
	
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		if(!context.hasSavedLink(id)) {
			context.getIOStream().writeln(String.format("ERRORE: Non esiste nessun Link con id %s", id));
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
		boolean linked = context.linkCFAs(id, source, destination);
		if(linked)
			context.getIOStream().writeln(String.format("CFA %s e %s collegate correttamente tramite link %s!", source, destination, id));
		else
			context.getIOStream().writeln(String.format("ERRORE: Impossibile collegare le CFA!", id));
		return linked;
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
