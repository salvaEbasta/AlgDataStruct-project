package ui.commands.spaceops;

import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpazioComportamentale;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import ui.context.StoppableOperation;
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
		
		SpaceAutomaComportamentale result = null;	
			
		if(net.hasComportamentalSpace()) {
			result = net.getComportamentalSpace();
			context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO:\n*****************************************************");
			context.getIOStream().writeln(result.toString());		
			return true;
		}
		
		
		SpazioComportamentale spaceComp = new SpazioComportamentale(net.getNet());
	
		result = new StoppableOperation().compute(context.getIOStream(), spaceComp);
		if(result == null)
			return false;
		
		context.getIOStream().writeln(result.toString());
		
		return true;
	}

}
