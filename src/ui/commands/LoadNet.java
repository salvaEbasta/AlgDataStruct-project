package ui.commands;

import ui.Context;

public class LoadNet implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.getIOStream().writeln("scegli file");
		
		return true;
	}

}
