package finite_state_automata;

public interface Transition {
	String id();
	State source();
	State sink();
	String regex();
	boolean setRegex(String newRegex);
	boolean isAuto();
	boolean isParallelTo(Transition t);
}
