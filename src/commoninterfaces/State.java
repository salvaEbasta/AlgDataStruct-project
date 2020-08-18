package commoninterfaces;

public abstract class State {
	
	protected String id;
	public boolean accepting;
	
	public State(String id) {
		this.id = id;
		this.accepting = false;
	}
	
	public String id() {
		return id;
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	public boolean isAccepting() {
		return accepting;
	}
	
	public void setAccepting(boolean accepting) {
		this.accepting = accepting;
	}
	
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		final State tmp = (State) obj;
		return this.id.equalsIgnoreCase(tmp.id);
	}

}
