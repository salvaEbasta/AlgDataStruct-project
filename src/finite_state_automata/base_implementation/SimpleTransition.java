package finite_state_automata.base_implementation;

import java.util.Objects;

import finite_state_automata.State;
import finite_state_automata.Transition;

public class SimpleTransition implements Transition{
	private static final String toStringFormat = "%s: %s--%s->%s";
	private String id;
	private State sorgente;
	private State destinazione;
	private String regex;
	
	public SimpleTransition(String id, State sorg, State dest, String regex) {
		this.id = id;
		this.sorgente = sorg;
		this.destinazione = dest;
		this.regex = regex;
	}
	
	public String id() {return id;}
	public State source() {return sorgente;}
	public State sink() {return destinazione;}
	public String regex() {return regex;}
	public boolean setRegex(String newRegex) {
		this.regex = newRegex;
		return true;
	}
	
	public boolean isAuto() {
		return sorgente.equals(destinazione);
	}
	
	public boolean isParallelTo(Transition t) {
		return sorgente.equals(t.sink()) && 
				destinazione.equals(t.source());
	}
	
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	public boolean equals(Object obj) {
		if(obj==null || !Transition.class.isAssignableFrom(obj.getClass()))
			return false;
		final Transition tmp = (Transition) obj;
		return this.id.equalsIgnoreCase(tmp.id());
	}
	
	public String toString() {
		return String.format(toStringFormat, id, sorgente, regex, destinazione);
	}
}
