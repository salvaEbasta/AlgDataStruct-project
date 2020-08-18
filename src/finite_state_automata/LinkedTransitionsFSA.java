package finite_state_automata;

import java.util.HashSet;
import java.util.Set;

import commoninterfaces.Automa;

public class LinkedTransitionsFSA extends Automa<FiniteState, FiniteTransition>{
	
	
	public LinkedTransitionsFSA(String id) {
		super(id);
	}

	public boolean hasAuto(FiniteState s) {
		return super.structure.get(s).hasAuto();
	}
	
	public FiniteTransition getAuto(FiniteState s) {
		return super.structure.get(s).getAuto().iterator().next();
	}

	@Override
	public Set<FiniteState> acceptingStates() {
		Set<FiniteState> acceptingStates = new HashSet<FiniteState>();
		for(FiniteState state: states()) {
			if(state.isAccepting())
				acceptingStates.add(state);
		}
		return acceptingStates;
	}

}
