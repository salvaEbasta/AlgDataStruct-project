package ui.commands;

import ui.Context;

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
		if(created)
			context.getIOStream().writeln("CFA creata correttamente!");
		else
			context.getIOStream().writeln(String.format("ERRORE: Nella CFA non sono presenti abbastanza Stati o Transizioni, oppure non è stato impostato uno Stato Iniziale!"));	
		return created;
	}

}
