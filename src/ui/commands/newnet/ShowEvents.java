package ui.commands.newnet;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;

public class ShowEvents implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.getIOStream().writeln(context.getWorkSpace().savedEventsList());
		return true;
	}

}