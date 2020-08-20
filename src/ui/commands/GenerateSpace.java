package ui.commands;

import comportamental_fsm.CFSMnetwork;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpazioComportamentale;
import ui.Context;

public class GenerateSpace implements CommandInterface, NoParameters{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		
		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(String.format("ERRORE: Nessuna rete di CFA caricata!"));
			return false;
		}
		
		CFSMnetwork net = context.getCurrentNet();
		SpazioComportamentale sc = new SpazioComportamentale(net);
		SpaceAutomaComportamentale spazio = sc.generaSpazioComportamentale();
		context.getIOStream().writeln("\nSPAZIO COMPORTAMENTALE GENERATO:\n*****************************************************");
		context.getIOStream().writeln(spazio.toString());
		
		return true;
	}

}
