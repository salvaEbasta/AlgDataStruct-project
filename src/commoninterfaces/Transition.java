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
	
	public String regex() {
		return regex.getLabel();
	}
	
	public boolean isAuto() {
		return source.equals(destination);
	}
	
	public boolean isParallelTo(Transition<S> t) {
		return this.source.equals(t.source) && this.destination.equals(t.destination);
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
		final Transition<S> tmp = (Transition<S>) obj;
		return this.id().equalsIgnoreCase(tmp.id());
	}
	

}
