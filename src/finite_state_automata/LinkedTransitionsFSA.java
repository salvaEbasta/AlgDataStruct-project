package finite_state_automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LinkedTransitionsFSA implements FiniteStateMachine{
	private String id;
	private HashMap<State, Interconnections> structure;
	private State initial;
	private State actual;
	
	public LinkedTransitionsFSA(String id) {
		this.id = id;
		structure = new HashMap<State, Interconnections>();
		initial = null;
		actual = null;
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
	public Set<State> acceptingStates() {
		HashSet<State> tmp = new HashSet<State>(structure.keySet());
		tmp.removeIf(s->!s.isAccepting());
		return tmp;
	}
	@Override
	public State initialState() {
		return initial;
	}
	@Override
	public State actualState() {
		return actual;
	}
	@Override
	public Set<Transition> to(State s) {
		if (structure.containsKey(s))
			return structure.get(s).to();
		else
			return new HashSet<Transition>();
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
		structure.get(t.source()).newOut(t);
		structure.get(t.sink()).newIn(t);
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
		if((initial!=null && initial.equals(s))||(actual!=null && actual.equals(s)))
			return false;
		structure.get(s).from().forEach(t->structure.get(t.sink()).to().remove(t));
		structure.get(s).to().forEach(t->structure.get(t.source()).from().remove(t));
		structure.remove(s);
		return true;
	}
	@Override
	public boolean hasAuto(State s) {
		return structure.get(s).hasAuto();
	}
	@Override
	public Transition getAuto(State s) {
		return structure.get(s).getAuto().iterator().next();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(structure.toString());
		return sb.toString();
	}
}
