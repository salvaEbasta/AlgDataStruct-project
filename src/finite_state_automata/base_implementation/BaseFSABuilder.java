package finite_state_automata.base_implementation;

import finite_state_automata.FiniteStateMachine;
import finite_state_automata.State;
import finite_state_automata.Transition;

public class BaseFSABuilder {
	public static State newState(String id) {
		return new SimpleState(id);
	}
	
	public static Transition newTransition(String id,
			State source,
			State sink,
			String regex) {
		return new SimpleTransition(id, source, sink, regex);
	}
	
	public static FiniteStateMachine newFSA(String id) {
		return new LinkedTransitionsFSA(id);
	}
}
