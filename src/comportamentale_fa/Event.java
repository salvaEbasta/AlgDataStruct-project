package comportamentale_fa;

public class Event {
	
	private String id;
	
	public Event(String id) {
		this.id = id;
	}
	
	public String id() {return id;}
	
	@Override
	public boolean equals(Object otherEvent) {
		Event ev = (Event) otherEvent;
		if(ev == null)
			return false;
		return this.id.equals(ev.id);
	}
		
}
