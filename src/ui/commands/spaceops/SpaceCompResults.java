package ui.commands.spaceops;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import utility.Constants;

public class SpaceCompResults implements CommandInterface, NoParameters{

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
			context.getIOStream().writeln("Risorse utilizzate per generazione Spazio Comportamentale:");
			context.getIOStream().writeln(String.format("\t* tempo: %.2fs", net.spazioComportamentaleTime()));
			context.getIOStream().writeln(String.format("\t* spazio: %.2fMB", net.spazioComportamentaleSpace()));
			context.getIOStream().writeln("\n\nRisorse utilizzate per generazione Diagnosticatore:");
			context.getIOStream().writeln(String.format("\t* tempo: %.2fs", net.diagnosticatoreTime()));
			context.getIOStream().writeln(String.format("\t* spazio: %.2fMB", net.diagnosticatoreSpace()));
			return true;
		} else {
			context.getIOStream().writeln("Impossibile visualizzare le performance della generazione dello spazio comportamentale: deve prima essere creato");
			return true;
		}
	}

}
