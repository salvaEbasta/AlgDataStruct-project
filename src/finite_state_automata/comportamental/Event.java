package finite_state_automata.comportamental;

import utility.Constants;

public class Event {
	
	private String id;
	
	public Event(String id) {
		this.id = id;
	}
	
	public Event() {
		this.id = Constants.EPSILON;
	}
	
	public String id() {return id;}
	
	public boolean isEmpty() {
		return id.equals(Constants.EPSILON);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !Event.class.isAssignableFrom(obj.getClass()))
			return false;
		final Event ev = (Event) obj;
		return this.id.equals(ev.id);
	}
		
}
