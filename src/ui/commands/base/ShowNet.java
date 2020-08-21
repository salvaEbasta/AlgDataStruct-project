package ui.commands.base;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import utility.Constants;

public class ShowNet implements CommandInterface, NoParameters{

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String description = context.getCurrentNet() == null? Constants.NO_LOADED_NET : context.getCurrentNet().toString();
		context.getIOStream().writeln(description);
		return true;
	}

}
