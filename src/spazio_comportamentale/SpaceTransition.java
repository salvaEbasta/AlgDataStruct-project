package spazio_comportamentale;

import commoninterfaces.Transition;
import comportamentale_fa.ComportamentaleTransition;

public class SpaceTransition<S extends SpaceState> extends Transition<S> {
	
	private ComportamentaleTransition transition;

	public SpaceTransition(S source, S destination, ComportamentaleTransition transition) {
		super(transition.id(), source, destination);
		this.transition = transition;
		setRegex(transition.regex());
	}

	public String labels() {
		return transition.labels();
	}

}
