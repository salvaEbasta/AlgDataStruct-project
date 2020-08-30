package spazio_comportamentale;

import comportamental_fsm.ComportamentalTransition;
import fsm_interfaces.Transition;

public class SpaceTransition<S extends SpaceState> extends Transition<S> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
			return String.format("%s:[%s->%s]", id(), source().id(), sink().id());
		return String.format("%s:[%s->%s, %s, %s]", id(), source().id(), sink().id(), transition.observableLabel(), transition.relevantLabel());
	}
}
