package ui.commands;

import ui.Context;
import utility.Constants;

public class NewCFA implements CommandInterface, OneParameter{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		if(context.hasSavedCFA(id)) {
			context.getIOStream().writeln(String.format("ERRORE: Un CFA con id %s è già presente!", id));
			return false;
		}
		context.createNewCFA(id);	
		context.getIOStream().writeln(String.format("Creazione di un nuovo FA Comportamentale con id %s:", id));
		context.getIOStream().writeln(Constants.INSERT_HELP);
		return true;
	}

}
