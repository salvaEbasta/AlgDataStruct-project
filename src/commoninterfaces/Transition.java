package commoninterfaces;

import comportamentale_fa.labels.Label;

public abstract class Transition<S extends State> {
	
	private String id;
	private S source;
	private S destination;
	private Label regex;
	
	public Transition(String id, S source, S destination) {
		this.id = id;
		this.source = source;
		this.destination = destination;
	}
	
	public String id() {
		return id;
	}
	
	public S source() {
		return source;
	}	
	
	public Label regex() {
		return regex;
	}
	
	public void setRegex(Label regex) {
		this.regex = regex;
	}
	
	public S sink() {
		return destination;
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		final Transition tmp = (Transition) obj;
		return this.id().equalsIgnoreCase(tmp.id());
	}
	

}
