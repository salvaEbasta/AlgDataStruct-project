package finite_state_machine;

import commoninterfaces.Transition;

public class FiniteTransition extends Transition<FiniteState>{
	
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
