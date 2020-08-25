package fsm_interfaces;

import java.util.Set;

public interface FiniteStateMachine<S extends StateInterface, T extends Transition<S>> extends Cloneable{
	public String id();
	public Set<T> transitions();
	public Set<S> states();
	public S initialState();
	public S currentState();
	public Set<T> to(S s);
	public Set<T> from(S s);
	public boolean add(T t);
	public boolean insert(S s);
	public boolean setInitial(S s);
	public boolean remove(T t);
	public boolean remove(S s);
	public boolean transitionTo(T t);
	public boolean setCurrent(S s);
	public Set<S> acceptingStates();
	public boolean hasAuto(S s);
	public T getAuto(S s);
	public boolean hasState(S s);
	public Object clone();
}
