package comportamentale_fa;

public class Event {
	
	private String id;
	
	public Event(String id) {
		this.id = id;
	}
	
	public Event() {
		this.id = "ε";
	}
	
	public String id() {return id;}
	
	public boolean isEmpty() {
		return id.equals("ε");
	}
	
	public void setEmpty() {
		this.id = "ε";
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object otherEvent) {
		Event ev = (Event) otherEvent;
		return this.id.equals(ev.id);
	}
		
}
