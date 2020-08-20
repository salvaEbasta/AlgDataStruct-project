package ui.commands;

import ui.Context;
import ui.InOutStream;
import utility.Constants;

public class Exit implements CommandInterface, NoParameters{

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		System.exit(0);
		return true;
	}

}
