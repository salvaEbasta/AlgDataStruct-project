package finite_state_automata.comportamental;

import finite_state_automata.FiniteStateMachine;
import finite_state_automata.State;
import finite_state_automata.Transition;

public interface ComportamentalFSM extends FiniteStateMachine{
	public State activate(Transition t);
	public boolean setCurrent(State s);
}
