package ui.commands.spaceops;

import diagnosticatore.ClosureSpace;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import utility.Constants;

public class ShowDiagnosticatore implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}						

		CurrentNet net = context.getCurrentNet();		
		if(net.hasDiagnosticatore()) {
			ClosureSpace diag = net.getDiagnosticatore();
			context.getIOStream().writeln("\nDIAGNOSTICATORE:\n*****************************************************");
			context.getIOStream().writeln(String.format("Risorse usate:"));
			context.getIOStream().writeln(String.format("\t* tempo: %.2f", context.getCurrentNet().diagnosticatoreTime()));
			context.getIOStream().writeln(String.format("\t* spazio: %.2f", context.getCurrentNet().diagnosticatoreSpace()));
			context.getIOStream().writeln(diag.toString());		
			return true;
		} else {
			context.getIOStream().writeln("Impossibile visualizzare il diagnosticatore: deve prima essere creato");
			return true;
		}
	}

}
