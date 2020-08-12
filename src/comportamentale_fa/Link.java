package comportamentale_fa;

public class Link {

	private String id;
	private ComportamentaleFA source;
	private ComportamentaleFA destination;
	private Event event;
	
	public Link(String id, ComportamentaleFA source, ComportamentaleFA destination) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.event = new Event();
	}
	
	public ComportamentaleFA getSource() {
		return source;
	}
	
	public ComportamentaleFA getDestination() {
		return destination;
	}
	
	public boolean hasEvent(){
		return !event.isEmpty();
	}
	
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event.setId(event.id());
	}	

	public void setEmptyEvent() {
		this.event.setEmpty();
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
