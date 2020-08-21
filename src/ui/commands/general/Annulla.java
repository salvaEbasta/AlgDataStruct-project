package ui.commands.general;

import ui.context.Context;

public class Annulla implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.reset();
		return true;
	}

}
