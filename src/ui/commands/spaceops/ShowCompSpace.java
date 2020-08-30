package ui.commands.spaceops;

import spazio_comportamentale.SpaceAutomaComportamentale;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import utility.Constants;

public class ShowCompSpace implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}						

		CurrentNet net = context.getCurrentNet();		
		if(net.hasComportamentalSpace()) {
			SpaceAutomaComportamentale spaceAutoma = net.getComportamentalSpace();
			context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE:\n*****************************************************");
			context.getIOStream().writeln(spaceAutoma.toString());		
			return true;
		} else {
			context.getIOStream().writeln("Impossibile visualizzare lo spazio comportamentale: deve prima essere creato");
			return true;
		}
	}

}
