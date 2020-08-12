package comportamentale_fa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map.Entry;

public class ComportamentaleFANet {
	
	private ArrayList<ComportamentaleFA> net;
	private ArrayList<Link> links;
	private SpaceStatusList status;
	
	public ComportamentaleFANet(ArrayList<Link> links) {
		this.net = new ArrayList<ComportamentaleFA>();
		this.links = links;
		for(Link link: links) {
			link.setEmptyEvent();
			if(!net.contains(link.getSource()))
				net.add(link.getSource());
			if(!net.contains(link.getDestination()))
				net.add(link.getDestination());
		}
		for(ComportamentaleFA cfa: net) {
			cfa.transitionTo(cfa.initialState()); //imposta l'actual state allo stato iniziale
		}
		Collections.reverse(net); // temporaneo, serve solo per far uscire C2 prima di C3
		status = new SpaceStatusList();
		status.add(net.stream().map(cfa -> cfa.initialState()).collect(Collectors.toCollection(ArrayList::new)),
				links.stream().map(link -> link.getEvent()).collect(Collectors.toCollection(ArrayList::new))); 
	}
	
	public String spazioComportamentale() {
		buildSpazio(enabledTransitions());
		return status.toString();
	}
	
	private void buildSpazio(Set<Transition> enabledTransitions) {
		status.addOutputTransitions(enabledTransitions);
		if(enabledTransitions.size()>1) {
			SpaceStatus stats = status.getCurrentStatus();
			for(Transition transition: enabledTransitions) {
				scatto(stats, transition, true); 
			}		
		} else if (enabledTransitions.size()==1)
			scatto(null, enabledTransitions.iterator().next(), false);
	}
	
	private void scatto(SpaceStatus stats, Transition transition, boolean doppio) {
		if(doppio)
			restoreStatus(stats);
		transitionTo(transition);	
		boolean added = status.add(net.stream().map(cfa -> cfa.actualState()).collect(Collectors .toCollection(ArrayList::new)),
				links.stream().map(link -> link.getEvent()).collect(Collectors .toCollection(ArrayList::new)), transition); 
		if(added)
			buildSpazio(enabledTransitions());	
	}
	
	private void restoreStatus(SpaceStatus stats) {
		for(int i=0; i<stats.getStates().size();i++) {
			net.get(i).transitionTo(stats.getStates().get(i));
		}
		for(int i=0; i<stats.getEvents().size();i++) {
			links.get(i).setEvent(stats.getEvents().get(i));
		}
	}
	
	public void transitionTo(Transition transition) {
		for (ComportamentaleFA cfa : net) {
	        if (transition.source().equals(cfa.actualState())) {
	        	cfa.transitionTo(transition.sink());
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
	
	public Set<Transition> enabledTransitions() {
		Set<Transition> enabledTransitions = new HashSet<Transition>();
		for(ComportamentaleFA cfa: net) {
			Set<Transition> transitions = cfa.from(cfa.actualState());
			for(Transition transition: transitions) {
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

}
