package ui.commands.base;

import comportamental_fsm.CFSMnetwork;
import ui.commands.general.CommandInterface;
import ui.commands.general.OneParameter;
import ui.context.Context;
import utility.FileHandler;

public class LoadNet implements CommandInterface, OneParameter{

	
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.getIOStream().writeln(String.format("Ricerca del file nella cartella '%s'...", FileHandler.NETS_PATH));
		String filename = FileHandler.NETS_PATH.concat(args[0]).concat(".ser");
		CFSMnetwork loadedNet = (CFSMnetwork) new FileHandler().load(filename);
		if(loadedNet == null) {
			context.getIOStream().writeln(String.format("ERRORE: Nessuna rete CFA trovata nel file '%s'", filename));
			return false;
		}
		context.loadNet(loadedNet);
		System.out.println("Rete caricata correttamente!");
		return true;
	}

}
