package finite_state_automata.base_implementation;

import finite_state_automata.State;

public class SimpleState implements State{
	private String id;
	private boolean accepting;
	
	public SimpleState(String id) {
		this.id = id;
		this.accepting = false;
	}
	
	@Override
	public String id() {return id;}
	
	@Override
	public boolean isAccepting() {
		return this.accepting;
	}
	
	@Override
	public void setAccepting(boolean accepting) {
		this.accepting = accepting;
	}
	
	public String toString() {
		return id+((accepting)?"||":"");
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj==null || !State.class.isAssignableFrom(obj.getClass()))
			return false;
		final State tmp = (State) obj;
		return this.id.equalsIgnoreCase(tmp.id());
	}
}
