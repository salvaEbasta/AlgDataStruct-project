package ui.commands.newcfa;

import comportamental_fsm.ComportamentalState;
import ui.commands.general.CommandInterface;
import ui.commands.general.OneParameter;
import ui.context.Context;

public class NewState implements CommandInterface, OneParameter{

	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		boolean added = context.getWorkSpace().saveState(new ComportamentalState(id));
		if(added) {
			context.getIOStream().writeln(String.format("Nuovo Stato con id %s creato correttamente!", id));
			context.getCurrentCFA().addStateToNewCFA((ComportamentalState) context.getWorkSpace().getSavedStateFromId(id));
		}
		else
			context.getIOStream().writeln(String.format("ERRORE: Uno Stato con id %s è già presente!", id));
		return added;
	}

}
