package finite_state_automata.comportamental;

public class State {

	
	private String id;
	
	public State(String id) {
		this.id = id;
	}
	
	public String id() {return id;}
	
	@Override
	public boolean equals(Object otherState) {
		State other = (State) otherState;
		return this.id.equals(other.id);
	}
}
