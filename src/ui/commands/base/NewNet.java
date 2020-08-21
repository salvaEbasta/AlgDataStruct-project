package ui.commands.base;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import utility.Constants;

public class NewNet implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.getIOStream().writeln("Creazione di una nuova rete di FA Comportamentali:");
		context.getIOStream().writeln(Constants.INSERT_HELP);
		
		return true;
	}

}
