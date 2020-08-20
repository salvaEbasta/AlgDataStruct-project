package ui.commands;

import ui.Context;

public class ShowEvents implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.getIOStream().writeln(context.savedEventsList());
		return true;
	}

}