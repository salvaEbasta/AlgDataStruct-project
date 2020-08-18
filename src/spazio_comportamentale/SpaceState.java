package spazio_comportamentale;

import java.util.ArrayList;

import commoninterfaces.State;
import comportamentale_fa.Event;
import comportamentale_fa.ComportamentaleState;

public class SpaceState extends State{
	
	private ArrayList<ComportamentaleState> actualStates;
	private ArrayList<Event> linkEvents;	
	
	public SpaceState(String id, ArrayList<ComportamentaleState> actualStates, ArrayList<Event> linkEvents) {
		super(id);
		this.actualStates = actualStates;
		this.linkEvents = new ArrayList<Event>();
		for(Event event: linkEvents) {
			this.linkEvents.add(new Event(event.id()));
		}
	}
	
	public void setId(String id) {
		super.id = id;
	}
	
	public boolean isFinalState() {
		for(Event event: linkEvents) {
			if(!event.isEmpty())
				return false;
		}
		return true;
	}
	
	public ArrayList<ComportamentaleState> getStates() {
		return actualStates;
	}
	
	public ArrayList<Event> getEvents() {
		return linkEvents;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(String.format("Stato %s => %s",id, actualStates.get(0).id()));
		for(int i=1; i<actualStates.size(); i++) {
			sb.append(" ").append(actualStates.get(i).id());
		}
		sb.append(" |");
		for(Event event: linkEvents) {
			sb.append(" ").append(event.id());
		}
		sb.append(isFinalState()? "\t[Stato Finale]": "");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object otherStatus) {
		SpaceState other = (SpaceState) otherStatus;
		for(int i=0; i < actualStates.size(); i++){
			if(!actualStates.get(i).equals(other.actualStates.get(i)))
				return false;
		}
		for(int i=0; i < linkEvents.size(); i++){
			if(!linkEvents.get(i).equals(other.linkEvents.get(i)))
				return false;
		}
		return true;
	}
}
