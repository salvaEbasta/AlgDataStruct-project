package comportamental_fsm;

import java.io.Serializable;

import utility.Constants;

public class Event implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public String toString() {
		return id;
	}
	
	@Override
	public boolean equals(Object otherEvent) {
		Event ev = (Event) otherEvent;
		return this.id.equals(ev.id);
	}
		
}