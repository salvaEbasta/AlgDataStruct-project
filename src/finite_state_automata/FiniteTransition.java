package finite_state_automata;

import commoninterfaces.Transition;

public class FiniteTransition extends Transition{
	
	private static final String toStringFormat = "%s: %s--%s->%s";
	private String regex;
	
	public FiniteTransition(String id, FiniteState source, FiniteState dest, String regex) {
		super(id, source, dest);
		this.regex = regex;
	}
	
	public String regex() {
		return regex;
	}
	
	public boolean setRegex(String newRegex) {
		this.regex = newRegex;
		return true;
	}
	
	public boolean isAuto() {
		return source().equals(destination());
	}
	
	public boolean isParallelTo(FiniteTransition t) {
		return source().equals(t.source()) && 
				destination().equals(t.destination());
	}
	
	@Override
	public String toString() {
		return String.format(toStringFormat, id(), source(), regex, destination());
	}
}
