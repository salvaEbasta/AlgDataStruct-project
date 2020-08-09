package comportamentale_fa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class SpaceStatus {
	
	private String id;
	private HashMap<ComportamentaleFA, State> actualStates;
	private ArrayList<Link> linksStatus;
	
	public SpaceStatus(String id, HashMap<ComportamentaleFA, State> actualStates, ArrayList<Link> links) {
		this.id = id;
		this.actualStates = actualStates;
		linksStatus = links;
	}
	
	public void update(Transition transition) {
		for (Entry<ComportamentaleFA, State> entry : actualStates.entrySet()) {
	        if (transition.source().equals(entry.getValue())) {
	        	actualStates.replace(entry.getKey(), transition.sink());
	            break;
	        }
	    }	
		if(!transition.isOutputEventsEmpty()) {
			HashMap<Event, Link> outputEvents = transition.getOutputEvents();
			for(Link link: linksStatus) {
				if(outputEvents.containsValue(link)) {
					for (Entry<Event, Link> entry : outputEvents.entrySet()) {
				        if (link.equals(entry.getValue())) {
				            link.setEvent(entry.getKey());
				            break;
				        }
				    }
				} else {
					link.setEmptyEvent();
				}
			
			}
		} else {
			linksStatus.forEach(link -> link.setEmptyEvent());
		}
	}


	@Override
	public String toString() {
		State[] states = new State[actualStates.size()];
		states = actualStates.values().toArray(states);
		StringBuilder sb = new StringBuilder(states[0].id());
		for(int i=1; i<actualStates.size(); i++) {
			sb.append(" ").append(states[i].id());
		}
		sb.append(" |");
		for(Link link: linksStatus) {
			sb.append(" ").append(link.eventString());
		}
		return sb.toString();
	}
}
