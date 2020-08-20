package comportamental_fsm;

import java.io.Serializable;

public class Link implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private ComportamentalFSM source;
	private ComportamentalFSM destination;
	private Event event;
	
	public Link(String id, ComportamentalFSM source, ComportamentalFSM destination) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.event = new Event();
	}
	
	public ComportamentalFSM getSource() {
		return source;
	}
	
	public void setSource(ComportamentalFSM source) {
		this.source = source;
	}
	
	public ComportamentalFSM getDestination() {
		return destination;
	}
	
	public void setDestination(ComportamentalFSM destination) {
		this.destination = destination;
	}
	
	public boolean hasEvent(){
		return !event.isEmpty();
	}
	
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}	

	public void setEmptyEvent() {
		this.event = new Event();
	}
		
	public String eventString() {
		return event.id();
	}
	
	public String id() {return id;}
	
	public String toString() {
		return String.format("Link %s: %s => %s", id, source == null? "vuoto" : "CFA ".concat(source.id()),
				destination == null? "vuoto" : "CFA ".concat(destination.id()));
	}
	
	@Override
	public boolean equals(Object otherLink) {
		Link link = (Link) otherLink;
		return this.id.equals(link.id);
	}
	
}
