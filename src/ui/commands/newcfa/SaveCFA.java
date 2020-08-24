package ui.commands.newcfa;

import ui.commands.general.CommandInterface;
import ui.commands.general.NoParameters;
import ui.context.Context;

public class SaveCFA implements CommandInterface, NoParameters{

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args,  Context context) {
		if(!check(args, context))
			return false;
		boolean created = context.saveCFA();
		if(created) {
			context.getIOStream().writeln("CFA creata correttamente!");
			context.getWorkSpace().resetCFA();
		}
		else
			context.getIOStream().writeln(String.format("ERRORE: Nella CFA non sono presenti abbastanza Stati o Transizioni, oppure non è stato impostato uno Stato Iniziale!"));	
		return created;
	}

}
