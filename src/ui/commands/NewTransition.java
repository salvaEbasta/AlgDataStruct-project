package ui.commands;

import java.util.HashMap;

import commoninterfaces.State;
import commoninterfaces.Transition;
import comportamentale_fa.ComportamentaleState;
import comportamentale_fa.ComportamentaleTransition;
import comportamentale_fa.Event;
import comportamentale_fa.Link;
import comportamentale_fa.labels.ObservableLabel;
import comportamentale_fa.labels.RelevantLabel;
import ui.Context;

public class NewTransition implements CommandInterface, OneParameter{

	private static final int STATES_REQUIRED = 2;

	@Override
	public boolean run(String[] args,  Context context) {
		if(!check(args, context))
			return false;
		String id = args[0];
		
		if(context.hasSavedTransition(id)) {
			context.getIOStream().writeln(String.format("ERRORE: Una Transition con id %s è già presente!", id));
			return false;
		}
		
		if(context.savedStatesSize() < STATES_REQUIRED) {
			context.getIOStream().writeln(String.format("ERRORE: Non sono stati creati Stati sufficienti per poter creare una Transizione!", id));
			return false;
		}
		context.getIOStream().writeln(context.savedStatesList());
		ComportamentaleState source = getState(context, "Indicare l'id dello Stato sorgente (oppure 'exit' per annullare): ");
		if(source == null)
			return false;
		ComportamentaleState destination = getState(context, "Indicare l'id delo Stato di destinazione (oppure 'exit' per annullare): ");
		if(destination == null)
			return false;
		String ans = context.getIOStream().yesOrNo("Inserire un evento di input?");
		Event eIn = new Event();
		Link lIn = null;
		if(ans.equalsIgnoreCase("y")) {
			context.getIOStream().writeln(context.savedEventsList());
			eIn = getEvent(context, "Indicare l'id dell'evento di input (oppure 'exit' per annullare): ");
		}			
		if(!eIn.isEmpty()) {
			context.getIOStream().writeln(context.savedLinksList());
			lIn = getLink(context, "Indicare l'id del Link di input (oppure 'exit' per annullare): ");
			if(lIn == null)
				return false;
		}
		ans = context.getIOStream().yesOrNo("Inserire eventi di output?");
		HashMap<Event, Link> eOut = new HashMap<Event, Link>();
		if(ans.equalsIgnoreCase("y")) {
			context.getIOStream().writeln(context.savedEventsList());
			context.getIOStream().writeln(context.savedLinksList());
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
		ObservableLabel obs = null;
		ans = context.getIOStream().yesOrNo("Creare una etichetta di Osservabilità?");
		if(ans.equalsIgnoreCase("y")){
			String label = context.getIOStream().read("Inserire nome per l'etichetta di Osservabilità: ");
			if(label.isEmpty())
				obs = new ObservableLabel();
			else
				obs = new ObservableLabel(label);
		}
		RelevantLabel rel = null;
		ans = context.getIOStream().yesOrNo("Creare una etichetta di Rilevanza?");
		if(ans.equalsIgnoreCase("y")){
			String label = context.getIOStream().read("Inserire nome per l'etichetta di Rilevanza: ");
			if(label.isEmpty())
				rel = new RelevantLabel();
			else
				rel = new RelevantLabel(label);
		}
		ComportamentaleTransition transition = null;
		if(eIn.isEmpty())
			transition = new ComportamentaleTransition(id, source, destination, eOut, obs, rel);
		else
			transition = new ComportamentaleTransition(id, source, destination, eIn, lIn, eOut, obs, rel);
		
		boolean added = context.saveTransition((Transition)transition);
		if(added) {
			context.getIOStream().writeln(String.format("Nuova transizione con id %s creata correttamente!", id));
			context.addTransitionToNewCFA(transition);
		}
		else
			context.getIOStream().writeln(String.format("ERRORE: Impossibile salvare la Transizione di id %s!", id));	
		
		return true;
	}

	
	private ComportamentaleState getState(Context context, String message) {
		boolean found = false;
		String id = null;
		do {
			id = context.getIOStream().read(message);
			if(id.equals("exit"))
				return null;
			found = context.hasSavedState(id);
		} while(!found);
		return (ComportamentaleState) context.getSavedStateFromId(id);
	}

	private Event getEvent(Context context, String message) {
		boolean found = false;
		String id = null;
		do {
			id = context.getIOStream().read(message);
			if(id.equals("exit"))
				return new Event();
			found = context.hasSavedEvent(id);
		} while(!found);
		return context.getSavedEventFromId(id);
	}
	
	private Link getLink(Context context, String message) {
		boolean found = false;
		String id = null;
		do {
			id = context.getIOStream().read(message);
			if(id.equals("exit"))
				return null;
			found = context.hasSavedLink(id);
		} while(!found);
		return context.getSavedLinkFromId(id);
	}

}
