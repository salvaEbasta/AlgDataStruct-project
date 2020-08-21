package ui.commands.base;

import ui.commands.general.CommandInterface;
import ui.commands.general.OneParameter;
import ui.context.Context;
import ui.context.CurrentNet;
import utility.FileHandler;

public class LoadNet implements CommandInterface, OneParameter{

	
	private static String fileName;
	
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		context.getIOStream().writeln(String.format("Ricerca del file nella cartella '%s'...", FileHandler.NETS_PATH));
		fileName = FileHandler.NETS_PATH.concat(args[0]).concat(".ser");
		//CFSMnetwork loadedNet = (CFSMnetwork) new FileHandler().load(filename);
		CurrentNet loadedNet = (CurrentNet) new FileHandler().load(fileName);
		if(loadedNet == null) {
			context.getIOStream().writeln(String.format("ERRORE: Nessuna rete CFA trovata nel file '%s'", fileName));
			return false;
		}
		context.loadNet(loadedNet);
		System.out.println("Rete caricata correttamente!");
		return true;
	}

	
	public static String getFileName() {
		return fileName;
	}
}
