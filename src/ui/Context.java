package ui;

import java.util.HashSet;
import java.util.Set;

import commoninterfaces.State;
import commoninterfaces.Transition;
import comportamentale_fa.ComportamentaleFA;
import comportamentale_fa.ComportamentaleState;
import comportamentale_fa.Event;
import comportamentale_fa.Link;

public class Context {
	
	private InOutStream io;
	
	private Set<ComportamentaleFA> savedCFAs;
	private Set<Link> savedLinks;
	private Set<State> savedStates;
	private Set<Event> savedEvents;
	private Set<Transition<State>> savedTransitions;
	
	private ComportamentaleFA newCFA;
	
	
	public Context(InOutStream io) {
		this.io = io;
		this.savedCFAs = new HashSet<>();
		this.savedLinks = new HashSet<>();
		this.savedStates = new HashSet<>();
		this.savedEvents = new HashSet<>();
		this.savedTransitions = new HashSet<>();
	}
	
	public InOutStream getIOStream() {
		return io;
	}
	
	public boolean saveState(State s) {
		return savedStates.add(s);
	}
	
	public boolean saveEvent(Event e) {
		return savedEvents.add(e);
	}
	
	public boolean saveLink(Link l) {
		return savedLinks.add(l);
	}
	
	public void createNewCFA(String id) {
		newCFA = new ComportamentaleFA(id);
	}
	
	public boolean setInitialStateOnNewCFA(ComportamentaleState s) {
		if(newCFA != null) 
			return newCFA.setInitial(s);
		return false;
		
	}	
	
	public Transition<State> getSavedTransitionFromId(String id) {
		for(Transition<State> t: savedTransitions) {
			if(t.id().equals(id))
				return t;
		}
		return null;
	}
	
	public boolean hasSavedTransition(String id) {
		return getSavedTransitionFromId(id) != null;
	}
	
	public boolean saveCFA() {
		boolean saved = savedCFAs.add(newCFA);
		if(saved)
			newCFA = null;
		return saved;		
	}

	public int savedCFAsSize() {
		return savedCFAs.size();
	}
	
	public ComportamentaleFA getSavedCFAfromId(String id) {
		for(ComportamentaleFA cfa: savedCFAs) {
			if(cfa.id().equals(id))
				return cfa;
		}
		return null;
	}
	
	public boolean hasSavedCFA(String id) {
		return getSavedCFAfromId(id) != null;
	}
	
	public Link getSavedLinkFromId(String id) {
		for(Link link: savedLinks) {
			if(link.id().equals(id))
				return link;
		}
		return null;
	}
	
	public boolean hasSavedLink(String id) {
		return getSavedLinkFromId(id) != null;
	}
	
	public State getStateFromId(String id) {
		for(State s: savedStates) {
			if(s.id().equals(id))
				return s;
		}
		return null;
	}
	
	public String savedCFAsList() {
		StringBuilder sb = new StringBuilder("Lista di CFA salvati:\n");
		for(ComportamentaleFA cfa: savedCFAs) {
			sb.append("* ").append(cfa).append("\n");
		}
		return sb.toString();
	}
	
	public String savedEventsList() {
		StringBuilder sb = new StringBuilder("Lista di Event salvati:\n");
		for(Event e: savedEvents) {
			sb.append("* ").append(e).append("\n");
		}
		return sb.toString();
	}
	
	public String savedStatesList() {
		StringBuilder sb = new StringBuilder("Lista di Stati salvati:\n");
		for(State s: savedStates) {
			sb.append("* ").append(s).append("\n");
		}
		return sb.toString();
	}

	public void resetUnsavedWork() {
		newCFA = null;	
	}

	public String savedTransitonsList() {
		StringBuilder sb = new StringBuilder("Lista di Transizioni salvate:\n");
		for(Transition<State> t: savedTransitions) {
			sb.append("* ").append(t).append("\n");
		}
		return sb.toString();
	}

	public boolean addStateToNewCFA(ComportamentaleState s) {
		if(newCFA == null)
			return false;
		return newCFA.insert(s);
	}
	
}
