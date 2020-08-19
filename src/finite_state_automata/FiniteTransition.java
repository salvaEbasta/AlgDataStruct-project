package finite_state_automata;

import commoninterfaces.Transition;
import comportamentale_fa.labels.Regex;

public class FiniteTransition extends Transition<FiniteState>{
	
	private static final String toStringFormat = "%s: %s--%s->%s";
	private Regex regex;
	
	public FiniteTransition(String id, FiniteState source, FiniteState dest, Regex regex) {
		super(id, source, dest);
		this.regex = regex;
	}
	
	public String regex() {
		return regex.getLabel();
	}
	
	public boolean setRegex(Regex newRegex) {
		this.regex = newRegex;
		return true;
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
		return String.format(toStringFormat, id(), source(), regex, sink());
	}
}
