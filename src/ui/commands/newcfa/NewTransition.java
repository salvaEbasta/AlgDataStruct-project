package ui.commands.newcfa;

import java.util.HashMap;

import comportamental_fsm.ComportamentalState;
import comportamental_fsm.ComportamentalTransition;
import comportamental_fsm.Event;
import comportamental_fsm.Link;
import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.RelevantLabel;
import fsm_interfaces.Transition;
import ui.commands.general.CommandInterface;
import ui.commands.general.OneParameter;
import ui.context.Context;

public class NewTransition implements CommandInterface, OneParameter{

	private static final int STATES_REQUIRED = 2;

	@Override
	public boolean run(String[] args,  Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		
		if(context.getWorkSpace().hasSavedTransition(id)) {
			context.getIOStream().writeln(String.format("ERRORE: Una Transition con id %s è già presente!", id));
			return false;
		}
		
		if(context.getWorkSpace().savedStatesSize() < STATES_REQUIRED) {
			context.getIOStream().writeln(String.format("ERRORE: Non sono stati creati Stati sufficienti per poter creare una Transizione!", id));
			return false;
		}
		context.getIOStream().writeln(context.getWorkSpace().savedStatesList());
		ComportamentalState source = getState(context, "Indicare l'id dello Stato sorgente (oppure 'exit' per annullare): ");
		if(source == null)
			return false;
		ComportamentalState destination = getState(context, "Indicare l'id dello Stato di destinazione (oppure 'exit' per annullare): ");
		if(destination == null)
			return false;
		String ans = context.getIOStream().yesOrNo("Inserire un evento di input?");
		Event eIn = new Event();
		Link lIn = null;
		if(ans.equalsIgnoreCase("y")) {
			context.getIOStream().writeln(context.getWorkSpace().savedEventsList());
			eIn = getEvent(context, "Indicare l'id dell'evento di input (oppure 'exit' per annullare): ");
		}			
		if(!eIn.isEmpty()) {
			context.getIOStream().writeln(context.getWorkSpace().savedLinksList());
			lIn = getLink(context, "Indicare l'id del Link di input (oppure 'exit' per annullare): ");
			if(lIn == null)
				return false;
		}
		ans = context.getIOStream().yesOrNo("Inserire eventi di output?");
		HashMap<Event, Link> eOut = new HashMap<Event, Link>();
		if(ans.equalsIgnoreCase("y")) {
			context.getIOStream().writeln(context.getWorkSpace().savedEventsList());
			context.getIOStream().writeln(context.getWorkSpace().savedLinksList());
			do {
				Event e = getEvent(context, "Indicare l'id dell'evento di output (oppure 'exit' per annullare): ");
				if(e.isEmpty())
					return false;
				Link l = getLink(context, "Indicare l'id del Link di output (oppure 'exit' per annullare): ");
				if(l == null)
					return false;
				eOut.put(e, l);
				ans = context.getIOStream().yesOrNo("Inserire un altro evento di output?");
			} while(ans.equalsIgnoreCase("y"));
		}			
		ObservableLabel obs = new ObservableLabel();;
		ans = context.getIOStream().yesOrNo("Creare una etichetta di Osservabilità?");
		if(ans.equalsIgnoreCase("y")){
			String label = context.getIOStream().read("Inserire nome per l'etichetta di Osservabilità: ");
			if(!label.isEmpty())
				obs = new ObservableLabel(label);
		}
		RelevantLabel rel = new RelevantLabel();
		ans = context.getIOStream().yesOrNo("Creare una etichetta di Rilevanza?");
		if(ans.equalsIgnoreCase("y")){
			String label = context.getIOStream().read("Inserire nome per l'etichetta di Rilevanza: ");
			if(!label.isEmpty())
				rel = new RelevantLabel(label);
		}
		ComportamentalTransition transition = null;
		if(eIn.isEmpty())
			transition = new ComportamentalTransition(id, source, destination, eOut, obs, rel);
		else
			transition = new ComportamentalTransition(id, source, destination, eIn, lIn, eOut, obs, rel);
		
		boolean added = context.getWorkSpace().saveTransition((Transition)transition);
		if(added) {
			context.getIOStream().writeln(String.format("Nuova transizione con id %s creata correttamente!", id));
			context.getCurrentCFA().addTransitionToNewCFA(transition);
		}
		else
			context.getIOStream().writeln(String.format("ERRORE: Impossibile salvare la Transizione di id %s!", id));	
		
		return true;
	}

	
	private ComportamentalState getState(Context context, String message) {
		boolean found = false;
		String id = null;
		do {
			id = context.getIOStream().read(message);
			if(id.equals("exit"))
				return null;
			found = context.getWorkSpace().hasSavedState(id);
		} while(!found);
		return (ComportamentalState) context.getWorkSpace().getSavedStateFromId(id);
	}

	private Event getEvent(Context context, String message) {
		boolean found = false;
		String id = null;
		do {
			id = context.getIOStream().read(message);
			if(id.equals("exit"))
				return new Event();
			found = context.getWorkSpace().hasSavedEvent(id);
		} while(!found);
		return context.getWorkSpace().getSavedEventFromId(id);
	}
	
	private Link getLink(Context context, String message) {
		boolean found = false;
		String id = null;
		do {
			id = context.getIOStream().read(message);
			if(id.equals("exit"))
				return null;
			found = context.getWorkSpace().hasSavedLink(id);
		} while(!found);
		return context.getWorkSpace().getSavedLinkFromId(id);
	}

}
