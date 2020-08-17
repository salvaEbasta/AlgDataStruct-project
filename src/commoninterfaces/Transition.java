package commoninterfaces;

public abstract class Transition {
	
	private String id;
	private State source;
	private State destination;
	
	public Transition(String id, State source, State destination) {
		this.id = id;
		this.source = source;
		this.destination = destination;
	}
	
	public String id() {
		return id;
	}
	
	public State source() {
		return source;
	}
	
	public State destination() {
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
