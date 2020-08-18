package commoninterfaces;

public interface Builder<S extends State, T extends Transition<S>> {
	S newState(String id);
	T newTransition(String id, S source, S destination);
}
