package finite_state_machine;

import commoninterfaces.State;

public class FiniteState extends State{

	private boolean accepting;
	
	public FiniteState(String id) {
		super(id);
		this.accepting = false;
	}
	
	public FiniteState(String id, boolean diAccettazione) {
		super(id);
		this.accepting = diAccettazione;
	}
	
	public boolean isAccepting() {
		return this.accepting;
	}
	
	public void setAccepting(boolean accettazione) {
		this.accepting = accettazione;
	}	
	
	@Override
	public String toString() {
		return id()+((accepting)?"!":"");
	}
}
