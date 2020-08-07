package finite_state_automata;

import java.util.Set;

public interface FiniteStateMachine {
	public String id();
	public Set<Transition> transitions();
	public Set<State> states();
	public Set<State> acceptingStates();
	public State initialState();
	public State actualState();
	public Set<Transition> to(State s);
	public Set<Transition> from(State s);
	public boolean add(Transition t);
	public boolean insert(State s);
	public boolean setInitial(State s);
	public boolean remove(Transition t);
	public boolean remove(State s);
	public boolean hasAuto(State s);
	public Transition getAuto(State s);
}
