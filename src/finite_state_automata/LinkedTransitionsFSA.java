package finite_state_automata;

import commoninterfaces.Automa;

public class LinkedTransitionsFSA extends Automa<FiniteTransition, FiniteInterconnections>{
	
	
	public LinkedTransitionsFSA(String id) {
		super(id);
	}

	public boolean hasAuto(FiniteState s) {
		return structure().get(s).hasAuto();
	}
	
	public FiniteTransition getAuto(FiniteState s) {
		return structure().get(s).getAuto().iterator().next();
	}

}
