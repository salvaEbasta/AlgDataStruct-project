package ui.commands.general;

import ui.context.Context;

public class Exit implements CommandInterface, NoParameters{

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.getIOStream().writeln("Programma terminato");
		System.exit(0);
		return true;
	}

}
