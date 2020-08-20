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
	
	public void setSource(ComportamentaleFA source) {
		this.source = source;
	}
	
	public ComportamentaleFA getDestination() {
		return destination;
	}
	
	public void setDestination(ComportamentaleFA destination) {
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
