package fsm_interfaces;

import java.io.Serializable;

public class State implements StateInterface, Serializable {
	private static final long serialVersionUID = 1L;
	protected String id;
	private boolean accepting;
	
	public State(String id) {
		this.id = id;
		this.accepting = false;
	}
	
	@Override
	public String id() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean isAccepting() {
		return accepting;
	}
	
	public void setAccepting(boolean accepting) {
		this.accepting = accepting;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		final State tmp = (State) obj;
		return this.id.equals(tmp.id);
	}
}
