package ui.commands.spacecomp;

import spazio_comportamentale.SpaceAutomaComportamentale;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import utility.Constants;

public class GenerateSpace implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
		CurrentNet net = context.getCurrentNet();
		SpaceAutomaComportamentale spazio = net.generateSpace();
		context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO:\n*****************************************************");
		context.getIOStream().writeln(spazio.toString());
		
		return true;
	}

}
