package commoninterfaces;

import java.util.HashMap;
import java.util.Set;

public interface AutomaInterface<T extends Transition, I extends Interconnections<T>>{
	public String id();
	public Set<T> transitions();
	public Set<State> states();
	public State initialState();
	public State currentState();
	public Set<T> to(State s);
	public Set<T> from(State s);
	public HashMap<State, I> structure();
	public boolean add(T t);
	public boolean insert(State s);
	public boolean setInitial(State s);
	public boolean remove(T t);
	public boolean remove(State s);
	public boolean transitionTo(T t);
	public boolean setCurrent(State s);
}
