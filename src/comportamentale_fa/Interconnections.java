package comportamentale_fa;

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
	
	public boolean addAllIn(Set<Transition> setIn) {
		int prevSize = in.size();
		for(Transition t: setIn) {
			if(!in.contains(t))
				in.add(t);
		}
		return in.size() != prevSize;
	}
	
	public boolean newOut(Transition t) {
		if(!out.contains(t))
			return out.add(t);
		return false;
	}
	
	public boolean addAllOut(Set<Transition> setOut) {
		int prevSize = out.size();
		for(Transition t: setOut) {
			if(!out.contains(t))
				out.add(t);
		}
		return out.size() != prevSize;
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(out.toString());
		sb.deleteCharAt(0);
		sb.insert(0, in.toString().replace("]", ", "));
		return sb.toString();
	}
}
