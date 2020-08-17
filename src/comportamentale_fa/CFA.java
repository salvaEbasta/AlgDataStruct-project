package comportamentale_fa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CFA implements ComportamentaleFA{

	private String id;
	private HashMap<State, Interconnections> structure;
	private State initial;
	private State current;
	
	public CFA(String id) {
		this.id = id;
		structure = new HashMap<State, Interconnections>();
		initial = null;
		current = null;
	}
	
	public String id() {
		return id;
	}
	@Override
	public Set<Transition> transitions() {
		HashSet<Transition> tmp = new HashSet<Transition>();
		structure.values().forEach(conn->{
			tmp.addAll(conn.to());
			tmp.addAll(conn.from());
		});
		return tmp;
	}
	@Override
	public Set<State> states() {
		return new HashSet<State>(structure.keySet());
	}
	
	@Override
	public State initialState() {
		return initial;
	}
	
	@Override
	public State currentState() {
		return current;
	}
	
	@Override
	public Set<Transition> to(State s) {
		if (structure.containsKey(s))
			return structure.get(s).to();
		else
			return new HashSet<Transition>();
	}
	
	@Override
	public boolean transitionTo(Transition t) {
		if (structure.containsKey(t.sink()) && current.equals(t.source())) {
			current = t.sink();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean setCurrent(State s) {
		if (structure.containsKey(s)) {
			current = s;
			return true;
		}
		return false;
	}
	
	@Override
	public Set<Transition> from(State s) {
		if (structure.containsKey(s))
			return structure.get(s).from();
		else
			return new HashSet<Transition>();
	}
	
	@Override
	public boolean add(Transition t) {
		structure.get(t.source()).from().add(t);
		structure.get(t.sink()).to().add(t);
		return true;
	}
	
	@Override
	public boolean insert(State s) {
		if(!structure.containsKey(s)) {
			structure.put(s, new Interconnections());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean setInitial(State s) {
		initial = s;
		return true;
	}
	
	@Override
	public boolean remove(Transition t) {
		structure.get(t.source()).from().remove(t);
		structure.get(t.sink()).to().remove(t);
		return true;
	}
	
	@Override
	public boolean remove(State s) {
		if((initial!=null && initial.equals(s))||(current!=null && current.equals(s)))
			return false;
		structure.get(s).from().forEach(t->structure.get(t.sink()).to().remove(t));
		structure.get(s).to().forEach(t->structure.get(t.source()).from().remove(t));
		structure.remove(s);
		return true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(structure.toString());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object otherCFA) {
		CFA cfa = (CFA) otherCFA;
		return this.id.equals(cfa.id);
	}

}
