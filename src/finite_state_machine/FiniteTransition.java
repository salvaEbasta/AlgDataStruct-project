package finite_state_machine;

import fsm_interfaces.Transition;

public class FiniteTransition extends Transition<FiniteState>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String toStringFormat = "%s: %s--(o:%s|r:%s)-->%s";
	
	public FiniteTransition(String id, FiniteState source, FiniteState dest) {
		super(id, source, dest);
	}
	
	public boolean isAuto() {
		return source().equals(sink());
	}
	
	public boolean isParallelTo(FiniteTransition t) {
		return source().equals(t.source()) && 
				sink().equals(t.sink());
	}
	
	@Override
	public String toString() {
		return String.format(toStringFormat, id(), source(), super.observableLabelContent(), super.relevantLabelContent(), sink());
	}
}
