package ui.commands;

import commoninterfaces.State;
import comportamentale_fa.ComportamentaleState;
import ui.Context;

public class SetInitial implements CommandInterface, OneParameter{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		State s = context.getStateFromId(id);
		boolean set = false;;
		if(s != null)
			set = context.setInitialStateOnNewCFA((ComportamentaleState) s);
		if(set)
			context.getIOStream().writeln(String.format("Nuovo stato iniziale %s impostato correttamente!", id));
		else
			context.getIOStream().writeln(String.format("ERRORE: Uno Stato con id %s non esiste!", id));
		return true;
	}

}
