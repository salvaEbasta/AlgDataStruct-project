package ui.commands.newcfa;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;

public class ResetCFA implements CommandInterface, NoParameters{

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args,  Context context) {
		if(!check(args, context))
			return false;
		context.getWorkSpace().resetCFA();
		return true;
	}

}
