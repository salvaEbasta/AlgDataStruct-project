package ui.commands.spaceops;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import utility.Constants;

public class ShowObservations implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
		if(context.getCurrentNet().observationsNumber() == 0) {
			context.getIOStream().writeln("Nessuna Osservazione Lineare calcolata per questa rete");
			return false;
		}
		
		CurrentNet net = context.getCurrentNet();				
		context.getIOStream().writeln(net.observationDescription());		
		return true;
	}

}
