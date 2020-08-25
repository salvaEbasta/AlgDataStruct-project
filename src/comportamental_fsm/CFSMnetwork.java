package comportamental_fsm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.ldap.StartTlsRequest;

import comportamental_fsm.labels.ObservableLabel;
import spazio_comportamentale.SpaceState;

import java.util.Map.Entry;

public class CFSMnetwork implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<ComportamentalFSM> net;
	private ArrayList<Link> links;
	
	public CFSMnetwork(ArrayList<Link> links) {
		this.net = new ArrayList<ComportamentalFSM>();
		this.links = links;
		for(Link link: links) {
			link.setEmptyEvent();
			if(!net.contains(link.getSource()))
				net.add(link.getSource());
			if(!net.contains(link.getDestination()))
				net.add(link.getDestination());
		}
		for(ComportamentalFSM cfa: net) {
			cfa.setCurrent(cfa.initialState()); //imposta l'actual state allo stato iniziale (magari facciamo metodo setActualToInitial? )
		}
	}
	
	
	
	public ArrayList<ComportamentalFSM> net(){
	return net;
	}
	
	public ArrayList<Link> links(){
	return links;
	}
	
	public HashMap<String, ComportamentalState> getInitialStates(){
		HashMap<String, ComportamentalState> tmp = new HashMap<String, ComportamentalState>();
		net.forEach(cfa -> tmp.put(cfa.id(),cfa.initialState()()));
		return tmp;
	}
	
	public HashMap<String, ComportamentalState> getActualStates(){
		HashMap<String, ComportamentalState> tmp = new HashMap<String, ComportamentalState>();
		net.forEach(cfa -> tmp.put(cfa.id(),cfa.currentState()));
		return tmp;
	}
	
	public  HashMap<Link, Event> getActiveEvents() {
		HashMap<Link, Event> tmp = new HashMap<Link, Event>();
		links.forEach(l -> tmp.put(l,l.getEvent()));
		return tmp;
	}
	
	public void restoreState(SpaceState stats) {
		for(ComportamentalFSM cfa: net) {
			cfa.setCurrent(stats.getStates().get(cfa.id()))
		}
		for(int i=0; i<stats.getStates().size();i++) {
			net.get(i).setCurrent(stats.getStates().get(i));
		}
		for(int i=0; i<stats.getEvents().size();i++) {
			links.get(i).setEvent(stats.getEvents().get(i));
		}
	}
	
	public void restoreInitial() {
		for(ComportamentalFSM cfa: net) {
			cfa.setCurrent(cfa.initialState());
		}
		for(Link link: links) {
			link.setEmptyEvent();
		}
	}
	
	public void transitionTo(ComportamentalTransition transition) {
		for (ComportamentalFSM cfa : net) {
	        if (transition.source().equals(cfa.currentState())) {
	        	cfa.transitionTo(transition);
	            break;
	        }
	    }	
		if(!transition.isInputEventEmpty()) {			
			Link link = links.get(links.indexOf(transition.getInputLink()));
			link.setEmptyEvent();			
		}
		if(!transition.isOutputEventsEmpty()) {
			HashMap<Event, Link> outputEvents = transition.getOutputEvents();
			for(Link link: links) {
				if(outputEvents.containsValue(link)) {
					for (Entry<Event, Link> entry : outputEvents.entrySet()) {
				        if (link.equals(entry.getValue())) {
				            link.setEvent(entry.getKey());
				            break;
				        }
				    }
				}			
			}
		}
	}
	
	public ArrayList<ObservableLabel> getObservabelLabels(){
		ArrayList<ObservableLabel> obsList = new ArrayList<ObservableLabel>();
		net.forEach(cfa -> cfa.transitions().forEach(t -> {
			if(t.hasObservableLabel() && !obsList.contains(t.observableLabel()))
				obsList.add(t.observableLabel());
		}));
		return obsList;
	}
	
	public Set<ComportamentalTransition> enabledTransitions() {
		Set<ComportamentalTransition> enabledTransitions = new HashSet<ComportamentalTransition>();
		for(ComportamentalFSM cfa: net) {
			Set<ComportamentalTransition> transitions = cfa.from(cfa.currentState());
			for(ComportamentalTransition transition: transitions) {
				boolean enabled = true;
				if(!transition.isInputEventEmpty()) {
					Link link = links.get(links.indexOf(transition.getInputLink()));
					Event transitionEvent = transition.getInputEvent();
					if(!transitionEvent.equals(link.getEvent()))
						enabled = false;
				}
				if(!transition.isOutputEventsEmpty()) {
					for(Link outlink: transition.getOutputEvents().values()) {
						Link link = links.get(links.indexOf(outlink));
						if(link.hasEvent()) {
							enabled = false;
							break;
						}
					}					
				}
				if(enabled)
					enabledTransitions.add(transition);
			}
		}
		return enabledTransitions;
	}
	
	public String toString() {
		return "CFA:\n" + net.toString() + "\n\nLinks:\n" + links.toString(); 
	}

}