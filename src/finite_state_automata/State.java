package finite_state_automata;

public interface State {
	String id();
	boolean isAccepting();
	void setAccepting(boolean accepting);
}
