package finite_state_machine;

import fsm_interfaces.State;

public class FiniteState extends State{
	private static final long serialVersionUID = 1L;
	
	public FiniteState(String id) {
		super(id);
	}
	
	@Override
	public String toString() {
		return id()+((super.isAccepting())?"!":"");
	}
}
