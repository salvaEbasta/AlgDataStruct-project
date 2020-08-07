package finite_state_automata;

import java.util.HashSet;
import java.util.Set;

public class Interconnections {
	private HashSet<Transition> in;
	private HashSet<Transition> out;
	
	public Interconnections() {
		in = new HashSet<Transition>();
		out = new HashSet<Transition>();
	}
	
	public boolean newIn(Transition t) {
		if(!in.contains(t))
			return in.add(t);
		return false;
	}
	
	public boolean newOut(Transition t) {
		if(!out.contains(t))
			return out.add(t);
		return false;
	}
	
	public boolean remove(Transition t) {
		return in.remove(t) || out.remove(t);
	} 
	
	public boolean contains(Transition t) {
		return in.contains(t) || out.contains(t);
	}
	
	public Set<Transition> to(){
		return in;
	}
	
	public Set<Transition> from(){
		return out;
	}
	
	public boolean hasAuto() {
		HashSet<Transition> tmp = new HashSet<Transition>(out);
		tmp.retainAll(in);
		return !tmp.isEmpty();
	}
	
	public Set<Transition> getAuto(){
		HashSet<Transition> tmp = new HashSet<Transition>(out);
		tmp.retainAll(in);
		return tmp;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(out.toString());
		sb.deleteCharAt(0);
		sb.insert(0, in.toString().replace("]", ", "));
		return sb.toString();
	}
}
