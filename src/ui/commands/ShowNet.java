package ui.commands;

import ui.Context;

public class ShowNet implements CommandInterface, NoParameters{

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		System.out.println(context.currentNetDescription());
		return true;
	}

}
