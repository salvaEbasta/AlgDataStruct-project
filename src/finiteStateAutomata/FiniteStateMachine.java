package finiteStateAutomata;

import java.util.Set;

public interface FiniteStateMachine {
	public Set<Transition> transitions();
	public Set<State> states();
	public State initialState();
	public State actualState();
}
