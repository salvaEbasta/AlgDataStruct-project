package comportamentale_fa;

import java.util.Set;

public interface ComportamentaleFA {
	public String id();
	public Set<Transition> transitions();
	public Set<State> states();
	public State initialState();
	public State actualState();
	public Set<Transition> to(State s);
	public Set<Transition> from(State s);
	public boolean add(Transition t);
	public boolean insert(State s);
	public boolean setInitial(State s);
	public boolean remove(Transition t);
	public boolean remove(State s);
}
