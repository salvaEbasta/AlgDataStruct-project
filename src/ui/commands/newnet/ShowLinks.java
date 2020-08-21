package ui.commands.newnet;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;

public class ShowLinks implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.getIOStream().writeln(context.getWorkSpace().savedLinksList());
		return true;
	}

}