package commoninterfaces;

import java.util.HashMap;
import java.util.Set;

public interface AutomaInterface<S extends State, T extends Transition, I extends Interconnections<T>>{
	public String id();
	public Set<T> transitions();
	public Set<S> states();
	public State initialState();
	public State currentState();
	public Set<T> to(S s);
	public Set<T> from(S s);
	public HashMap<S, I> structure();
	public boolean add(T t);
	public boolean insert(S s);
	public boolean setInitial(S s);
	public boolean remove(T t);
	public boolean remove(S s);
	public boolean transitionTo(T t);
	public boolean setCurrent(S s);
}
