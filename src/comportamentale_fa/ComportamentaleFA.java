package comportamentale_fa;

import java.util.Set;

public interface ComportamentaleFA {
	public String id();
	public Set<ComportamentaleTransition> transitions();
	public Set<ComportamentaleState> states();
	public ComportamentaleState initialState();
	public ComportamentaleState currentState();
	public Set<ComportamentaleTransition> to(ComportamentaleState s);
	public Set<ComportamentaleTransition> from(ComportamentaleState s);
	public boolean add(ComportamentaleTransition t);
	public boolean insert(ComportamentaleState s);
	public boolean setInitial(ComportamentaleState s);
	public boolean remove(ComportamentaleTransition t);
	public boolean remove(ComportamentaleState s);
	public boolean transitionTo(ComportamentaleTransition t);
	public boolean setCurrent(ComportamentaleState s);
}
