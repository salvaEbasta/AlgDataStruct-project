package ui.commands;

import ui.Context;

public class NewTransition implements CommandInterface, OneParameter{

	@Override
	public boolean run(String[] args,  Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		if(context.hasSavedTransition(id)) {
			context.getIOStream().writeln(String.format("ERRORE: Una Transition con id %s è già presente!", id));
			return false;
		}
		context.getIOStream().writeln(String.format("Nuova transizione con id %s creato correttamente!", id));
		return true;
	}

}
