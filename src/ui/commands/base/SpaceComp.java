package ui.commands.base;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import utility.Constants;

public class SpaceComp implements CommandInterface, NoParameters{

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
		context.getIOStream().writeln("Sottomenù di generazione Spazio Comportamentale.");
		context.getIOStream().writeln(Constants.INSERT_HELP);
		return true;	
		
	}

}
