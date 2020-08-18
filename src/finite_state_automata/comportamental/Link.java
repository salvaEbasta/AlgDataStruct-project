package finite_state_automata.comportamental;

public class Link {

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
	
	public ComportamentalFSM getDestination() {
		return destination;
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
	
	@Override
	public boolean equals(Object otherLink) {
		Link link = (Link) otherLink;
		return this.id.equals(link.id);
	}
	
}
