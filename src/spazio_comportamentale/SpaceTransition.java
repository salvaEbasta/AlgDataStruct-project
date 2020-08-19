package spazio_comportamentale;

import commoninterfaces.Transition;
import comportamentale_fa.ComportamentaleTransition;
import comportamentale_fa.labels.Regex;

public class SpaceTransition<S extends SpaceState> extends Transition<S> {
	
	private ComportamentaleTransition transition;

	
	public SpaceTransition(String id, S source, S destination) {
		super(id, source, destination);	
	}
	
	public SpaceTransition(S source, S destination, ComportamentaleTransition transition) {
		super(transition.id(), source, destination);
		this.transition = transition;
		setRegex(new Regex(transition.regex()));
	}
	
	@Override
	public boolean equals(Object obj) {
		SpaceTransition<S> other = (SpaceTransition<S>) obj;
		return transition.equals(other.transition) && source().equals(other.source())
				&& sink().equals(other.sink());
	}
	
	@Override
	public String toString() {
		return String.format("%s: da {%s} verso {%s} %s", id(), source(), sink(), transition.labels());
	}
}
