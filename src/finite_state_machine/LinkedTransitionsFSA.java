package finite_state_machine;

import java.util.HashSet;
import java.util.Set;

import commoninterfaces.Automa;
import commoninterfaces.Interconnections;

public class LinkedTransitionsFSA extends Automa<FiniteState, FiniteTransition>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	@Override
	public Object clone() {
		LinkedTransitionsFSA deepCopy = new LinkedTransitionsFSA(super.id());
		super.structure.forEach((key,value)->
			deepCopy.structure.put(key, (Interconnections<FiniteState, FiniteTransition>)value.clone()));
		deepCopy.setInitial(this.initialState());
		deepCopy.setCurrent(this.currentState());
		return deepCopy;
	}
}
