package ui.commands.newnet;

import comportamental_fsm.Link;
import ui.commands.general.CommandInterface;
import ui.commands.general.OneParameter;
import ui.context.Context;

public class NewLink implements CommandInterface, OneParameter{


	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		if(context.getWorkSpace().hasSavedLink(id)) {
			context.getIOStream().writeln(String.format("ERRORE: Un Link con id %s è già presente!", id));
			return false;
		}
		Link l = new Link(id, null, null);
		boolean added = context.getWorkSpace().saveLink(l);
		if(added)
			context.getIOStream().writeln(String.format("Nuovo Link con id %s creato correttamente!", id));
		else
			context.getIOStream().writeln(String.format("ERRORE: Impossibile salvare il Link!", id));
		return added;
	}

}
