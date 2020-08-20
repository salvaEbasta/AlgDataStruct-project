package ui.commands;

import ui.Context;
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
