package ui.commands.newcfa;

import comportamental_fsm.ComportamentalState;
import fsm_interfaces.State;
import ui.commands.general.CommandInterface;
import ui.commands.general.OneParameter;
import ui.context.Context;

public class SetInitial implements CommandInterface, OneParameter{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		State s = context.getWorkSpace().getSavedStateFromId(id);
		boolean set = false;;
		if(s != null)
			set = context.getCurrentCFA().setInitialStateOnNewCFA((ComportamentalState) s);
		if(set)
			context.getIOStream().writeln(String.format("Nuovo stato iniziale %s impostato correttamente!", id));
		else
			context.getIOStream().writeln(String.format("ERRORE: Uno Stato con id %s non esiste!", id));
		return true;
	}

}
