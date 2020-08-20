package spazio_comportamentale;

import commoninterfaces.Transition;
import comportamental_fsm.ComportamentalTransition;

public class SpaceTransition<S extends SpaceState> extends Transition<S> {
	
	private ComportamentalTransition transition;

	
	public SpaceTransition(String id, S source, S destination) {
		super(id, source, destination);
	}
	
	public SpaceTransition(S source, S destination, ComportamentalTransition transition) {
		super(transition.id(), source, destination);
		super.setObservableLabel(transition.observableLabelContent());
		super.setRelevantLabel(transition.relevantLabelContent());
		this.transition = transition;
	}
	
	@Override
	public boolean equals(Object obj) {
		SpaceTransition<S> other = (SpaceTransition<S>) obj;
		return transition.equals(other.transition) && source().equals(other.source())
				&& sink().equals(other.sink());
	}
	
	@Override
	public String toString() {
		if(transition == null)
			return String.format("%s: da {%s} verso {%s}", id(), source(), sink());
		return String.format("%s: da {%s} verso {%s} %s", id(), source(), sink(), transition.labels());
	}
}
