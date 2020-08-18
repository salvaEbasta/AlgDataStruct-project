package commoninterfaces;

public abstract class State {
	
	protected String id;
	
	public State(String id) {
		this.id = id;
	}
	
	public String id() {
		return id;
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		final State tmp = (State) obj;
		return this.id.equalsIgnoreCase(tmp.id);
	}

}
