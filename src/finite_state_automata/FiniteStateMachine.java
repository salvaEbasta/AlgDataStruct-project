package finite_state_automata;

import java.util.Set;

public interface FiniteStateMachine {
	public String id();
	public Set<FiniteTransition> transitions();
	public Set<FiniteState> states();
	public Set<FiniteState> acceptingStates();
	public FiniteState initialState();
	public FiniteState actualState();
	public Set<FiniteTransition> to(FiniteState s);
	public Set<FiniteTransition> from(FiniteState s);
	public boolean add(FiniteTransition t);
	public boolean insert(FiniteState s);
	public boolean setInitial(FiniteState s);
	public boolean remove(FiniteTransition t);
	public boolean remove(FiniteState s);
	public boolean hasAuto(FiniteState s);
	public FiniteTransition getAuto(FiniteState s);
}
