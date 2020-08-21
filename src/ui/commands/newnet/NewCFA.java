package ui.commands.newnet;

import ui.commands.general.CommandInterface;
import ui.commands.general.OneParameter;
import ui.context.Context;
import utility.Constants;

public class NewCFA implements CommandInterface, OneParameter{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		if(context.getWorkSpace().hasSavedCFA(id)) {
			context.getIOStream().writeln(String.format("ERRORE: Un CFA con id %s è già presente!", id));
			return false;
		}
		context.createNewCFA(id);	
		context.getIOStream().writeln(String.format("Creazione di un nuovo FA Comportamentale con id %s:", id));
		context.getIOStream().writeln(Constants.INSERT_HELP);
		return true;
	}

}
