package spazio_comportamentale;

import java.util.HashMap;

import comportamental_fsm.ComportamentalState;
import comportamental_fsm.Event;
import comportamental_fsm.Link;
import fsm_interfaces.State;

public class SpaceState extends State{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, ComportamentalState> actualStates;
	private HashMap<Link, Event> linkEvents;	
	
	
	public SpaceState(String id) {
		super(id);
		this.actualStates = new HashMap<String, ComportamentalState>();
		this.linkEvents = new HashMap<Link, Event>();
	}
	
	public SpaceState(HashMap<String, ComportamentalState> actualStates, HashMap<Link, Event> linkEvents) {
		super("");
		this.actualStates = actualStates;
		this.linkEvents = new HashMap<Link, Event>();
		linkEvents.forEach((link, event)->this.linkEvents.put(link, (Event)event.clone()));
		setId(content());
	}
	
	public void setId(String id) {
		super.id = id;
	}
	
	public boolean isFinal() {
		if(linkEvents.isEmpty())
			return false;
		for(Event event: linkEvents.values()) {
			if(!event.isEmpty())
				return false;
		}
		return true;
	}
	
	public HashMap<String, ComportamentalState> getStates() {
		return actualStates;
	}
	
	public boolean hasState(ComportamentalState s) {
		return this.actualStates.keySet().contains(s.id());
	}
	
	public HashMap<Link, Event> getEvents() {
		return linkEvents;
	}
	
	public boolean hasEvent(Event e) {
		return linkEvents.values().contains(e);
	}
	
	private String content() {
		StringBuilder sb = new StringBuilder();
		sb.append(actualStates.toString()).append(" ");
		sb.append(linkEvents.toString()).append(" ");
		sb.append(isFinal()? "f": "");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		if(actualStates.isEmpty())
			return id();
		StringBuilder sb = new StringBuilder(String.format("Stato %s => [",id));
		actualStates.forEach((automa_id, state)->sb.append(String.format(" %s(%s)", automa_id, state.toString())));
		sb.append(" |");
		linkEvents.forEach((l, e)->sb.append(String.format(" %s(%s)", l.id(), e)));
		sb.append(isFinal()? "] [Stato Finale]": "]");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object otherStatus) {
		if(otherStatus==null || !SpaceState.class.isAssignableFrom(otherStatus.getClass()))
			return false;
		final SpaceState other = (SpaceState) otherStatus;		
		
		return actualStates.equals(other.actualStates) 
				&& linkEvents.equals(other.linkEvents);
	}
}