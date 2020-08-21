package ui.commands.newnet;

import comportamental_fsm.Event;
import ui.commands.general.CommandInterface;
import ui.commands.general.OneParameter;
import ui.context.Context;

public class NewEvent implements CommandInterface, OneParameter{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		boolean added = context.getWorkSpace().saveEvent(new Event(id));
		if(added)
			context.getIOStream().writeln(String.format("Nuovo Evento con id %s creato correttamente!", id));
		else
			context.getIOStream().writeln(String.format("ERRORE: Un Evento con id %s è già presente!", id));
		return added;
	}

}
