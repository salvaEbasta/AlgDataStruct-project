package ui.commands.spaceops;

import java.util.Map.Entry;
import diagnosticatore.ClosureSpace;
import diagnosticatore.algorithms.DiagnosticatoreBuilder;
import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import ui.context.CurrentNet;
import ui.context.Performance;
import ui.context.StoppableOperation;
import utility.Constants;

public class GenerateDiagnosticatore implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
		if(!context.getCurrentNet().hasComportamentalSpace()) {
			context.getIOStream().writeln("Per costruire il diagnosticatore è necessario disporre dello spazio comportamentale");
			context.getIOStream().writeln("Genera lo spazio comportamentale\n");
			return false;
		}
		
		Entry<ClosureSpace, Performance> result = null;
		CurrentNet net = context.getCurrentNet();
		
		DiagnosticatoreBuilder diagnBuilder = new DiagnosticatoreBuilder(net.getComportamentalSpace());
	
		result = new StoppableOperation().compute(context.getIOStream(), diagnBuilder);
		if(result == null)
			return false;

		boolean stopped = result.getValue().wasStopped();
		
		if(!stopped) {
			context.getCurrentNet().setDiagnosticatorePerformance(result.getValue().getTime(), result.getValue().getSpace());
			context.getCurrentNet().setDiagnosticatore(result.getKey());
			context.getIOStream().writeln(String.format("Diagnosticatore calcolato correttamente!"));
		}
		context.getIOStream().writeln(result.getKey().toString());
		
		return true;
	}
}
