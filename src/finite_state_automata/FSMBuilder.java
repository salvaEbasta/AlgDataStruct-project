package finite_state_automata;

import commoninterfaces.Builder;

public class FSMBuilder implements Builder<FiniteState, FiniteTransition>{

	@Override
	public FiniteState newState(String id) {
		return new FiniteState(id);
	}

	@Override
	public FiniteTransition newTransition(String id, FiniteState source, FiniteState destination) {
		return new FiniteTransition(id, source, destination);
	}

}
