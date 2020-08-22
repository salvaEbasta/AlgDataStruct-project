package commoninterfaces;

public interface ComponentBuilder<S extends StateInterface, T extends Transition<S>> {
	S newState(String id);
	T newTransition(String id, S source, S destination);
}
