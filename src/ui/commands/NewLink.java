package ui.commands;

import comportamentale_fa.Link;
import ui.Context;

public class NewLink implements CommandInterface, OneParameter{


	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		if(context.hasSavedLink(id)) {
			context.getIOStream().writeln(String.format("ERRORE: Un Link con id %s è già presente!", id));
			return false;
		}
		Link l = new Link(id, null, null);
		boolean added = context.saveLink(l);
		if(added)
			context.getIOStream().writeln(String.format("Nuovo Link con id %s creato correttamente!", id));
		else
			context.getIOStream().writeln(String.format("ERRORE: Impossibile salvare il Link!", id));
		return added;
	}

}
