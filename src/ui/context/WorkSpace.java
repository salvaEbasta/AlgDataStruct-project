package ui.context;

import java.util.HashSet;
import java.util.Set;

import commoninterfaces.State;
import commoninterfaces.Transition;
import comportamental_fsm.ComportamentalFSM;
import comportamental_fsm.Event;
import comportamental_fsm.Link;

public class WorkSpace {
	
	private Set<ComportamentalFSM> savedCFAs;
	private Set<Link> savedLinks;
	private Set<State> savedStates;
	private Set<Event> savedEvents;
	private Set<Transition<State>> savedTransitions;
	
	public WorkSpace() {
		this.savedCFAs = new HashSet<>();
		this.savedLinks = new HashSet<>();
		this.savedStates = new HashSet<>();
		this.savedEvents = new HashSet<>();
		this.savedTransitions = new HashSet<>();
	}
	
	public boolean saveState(State s) {
		return savedStates.add(s);
	}
	
	public State getSavedStateFromId(String id) {
		for(State s: savedStates) {
			if(s.id().equals(id))
				return s;
		}
		return null;
	}	

	public boolean hasSavedState(String id) {
		return getSavedStateFromId(id) != null;
	}
	
	public String savedStatesList() {
		StringBuilder sb = new StringBuilder("Lista di Stati salvati:\n");
		for(State s: savedStates) {
			sb.append("* ").append(s).append("\n");
		}
		return sb.toString();
	}
	
	public int savedStatesSize() {
		return savedStates.size();
	}
	
	public boolean saveEvent(Event e) {
		return savedEvents.add(e);
	}	

	public Event getSavedEventFromId(String id) {
		for(Event e: savedEvents) {
			if(e.id().equals(id))
				return e;
		}
		return null;
	}	

	public boolean hasSavedEvent(String id) {
		return getSavedEventFromId(id) != null;
	}
	
	public String savedEventsList() {
		StringBuilder sb = new StringBuilder("Lista di Event salvati:\n");
		for(Event e: savedEvents) {
			sb.append("* ").append(e).append("\n");
		}
		return sb.toString();
	}

	public boolean saveLink(Link l) {
		return savedLinks.add(l);
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
	

	public Set<Link> getSavedLinks() {
		return savedLinks;
	}

	public String savedLinksList() {
		StringBuilder sb = new StringBuilder("Lista di Link salvati:\n");
		for(Link l: savedLinks) {
			sb.append("* ").append(l).append("\n");
		}
		return sb.toString();
	}
	
	public int savedLinksSize() {
		return savedLinks.size();
	}
	
	public boolean saveTransition(Transition<State> t) {
		return savedTransitions.add(t);
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
	
	public String savedTransitionsList() {
		StringBuilder sb = new StringBuilder("Lista di Transizioni salvate:\n");
		for(Transition<State> t: savedTransitions) {
			sb.append("* ").append(t).append("\n");
		}
		return sb.toString();
	}
	
	public boolean saveCFA(ComportamentalFSM cfa) {
		return savedCFAs.add(cfa);
	}
	
	public ComportamentalFSM getSavedCFAfromId(String id) {
		for(ComportamentalFSM cfa: savedCFAs) {
			if(cfa.id().equals(id))
				return cfa;
		}
		return null;
	}
	
	public boolean hasSavedCFA(String id) {
		return getSavedCFAfromId(id) != null;
	}	
	
	public String savedCFAsList() {
		StringBuilder sb = new StringBuilder("Lista di CFA salvati:\n");
		for(ComportamentalFSM cfa: savedCFAs) {
			sb.append("* ").append("Automa ").append(cfa.id()).append("\n");
		}
		return sb.toString();
	}
	
	public int savedCFAsSize() {
		return savedCFAs.size();
	}
	

	public boolean linkCFAs(String id, ComportamentalFSM source, ComportamentalFSM destination) {
		if(hasSavedLink(id)) {
			Link l = getSavedLinkFromId(id);
			l.setSource(source);
			l.setDestination(destination);
			return true;
		}
		return false;
	}	
	
	public void reset() {
		this.savedCFAs = new HashSet<>();
		this.savedLinks = new HashSet<>();
		this.savedStates = new HashSet<>();
		this.savedEvents = new HashSet<>();
		this.savedTransitions = new HashSet<>();
	}
	

}
