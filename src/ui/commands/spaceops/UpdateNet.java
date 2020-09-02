package ui.commands.spaceops;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;
import utility.Constants;
import utility.FileHandler;

public class UpdateNet implements CommandInterface, NoParameters{	

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;	

		if(context.getCurrentNet() == null) {
			context.getIOStream().writeln(Constants.NO_LOADED_NET);
			return false;
		}
		
				
		String fileName = context.getNetFileName();
		boolean saved = new FileHandler().save(fileName, context.getCurrentNet());
		if(saved)
			context.getIOStream().writeln(String.format("Rete di CFA salvata correttamente nel percorso %s!", fileName));
		else
			context.getIOStream().writeln(String.format("ERRORE: Impossibile salvare la rete CFA sul percorso %s!", fileName));
		return saved;
	}
	
}