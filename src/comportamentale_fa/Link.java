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
	}
	
	public boolean hasEvent(){
		return event != null;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}	

	public void setEmptyEvent() {
		this.event = null;
	}
		
	public String eventString() {
		return event == null? "ε" : event.id();
	}
	
	public String id() {return id;}
	
	
	
}
