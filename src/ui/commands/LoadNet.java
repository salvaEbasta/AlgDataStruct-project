package ui.commands;

import comportamentale_fa.ComportamentaleFANet;
import ui.Context;
import utility.FileHandler;

public class LoadNet implements CommandInterface, OneParameter{

	private static final String PARENT = "Saved Nets/";
	
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String filename = PARENT.concat(args[0]).concat(".ser");
		ComportamentaleFANet loadedNet = (ComportamentaleFANet) new FileHandler().load(filename);
		if(loadedNet == null) {
			context.getIOStream().writeln(String.format("ERRORE: Nessuna rete CFA trovata nel file '%s'", filename));
			return false;
		}
		context.loadNet(loadedNet);
		System.out.println(loadedNet.getActualStates() + " <> " + loadedNet.getActiveEvents());
		
		return true;
	}

}
