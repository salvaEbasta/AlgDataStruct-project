package ui.commands;

import ui.Context;

public class Annulla implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.resetUnsavedWork();
		return true;
	}

}
