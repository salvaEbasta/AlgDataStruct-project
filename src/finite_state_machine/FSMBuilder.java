package finite_state_machine;

import fsm_interfaces.ComponentBuilder;

public class FSMBuilder implements ComponentBuilder<FiniteState, FiniteTransition>{

	@Override
	public FiniteState newState(String id) {
		return new FiniteState(id);
	}

	@Override
	public FiniteTransition newTransition(String id, FiniteState source, FiniteState destination) {
		return new FiniteTransition(id, source, destination);
	}

}
