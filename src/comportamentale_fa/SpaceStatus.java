package comportamentale_fa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SpaceStatus {
	
	private String id;
	private ArrayList<State> actualStates;
	private ArrayList<Event> linkEvents;
	private Set<Transition> in;
	private Set<Transition> out;
	
	
	public SpaceStatus(String id, ArrayList<State> actualStates, ArrayList<Event> linkEvents) {
		this.id = id;
		this.actualStates = actualStates;
		this.linkEvents = linkEvents;
		in = new HashSet<Transition>();
		out = new HashSet<Transition>();
	}
	
	public boolean isFinalState() {
		for(Event event: linkEvents) {
			if(event != null)
				return false;
		}
		return true;
	}
	
	public boolean addInputTransition(Transition t) {
		return in.add(t);
	}
	
	public boolean addOutputTransition(Set<Transition> t) {
		return out.addAll(t);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(String.format("Stato %s => %s",id, actualStates.get(0).id()));
		for(int i=1; i<actualStates.size(); i++) {
			sb.append(" ").append(actualStates.get(i).id());
		}
		sb.append(" |");
		for(Event event: linkEvents) {
			sb.append(" ").append(event == null? "ε" : event.id());
		}
		sb.append(isFinalState()? "\t[Stato Finale]": "");
		if(!in.isEmpty()) {
			sb.append("\n\t- Input Transitions: ");
			for(Transition transition: in) {
				sb.append(transition.id().concat(" "));
			}
		}
		if(!out.isEmpty()) {
			sb.append("\n\t- Output Transitions: ");
			for(Transition transition: out) {
				sb.append(transition.id().concat(" "));
			}
		}
		return sb.toString();
	}
	
	public boolean equals(Object otherStatus) {
		SpaceStatus other = (SpaceStatus) otherStatus;
		ArrayList<State> copyActualStates = new ArrayList<State>(actualStates);
		ArrayList<Event> copyLinkEvents = new ArrayList<Event>(linkEvents);
		copyActualStates.removeAll(other.actualStates);
		copyLinkEvents.removeAll(other.linkEvents);
		return copyActualStates.isEmpty() && copyLinkEvents.isEmpty();
	}
}
