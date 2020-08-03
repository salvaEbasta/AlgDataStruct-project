package finiteStateAutomata;

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
		this.structure = new HashMap<State, Interconnections>();
		this.initial = null;
		this.actual = null;
	}
	
	public String id() {return id;}
	
	@Override
	public Set<Transition> transitions() {
		HashSet<Transition> tmp = new HashSet<Transition>();
		structure.forEach((k, v)->{
				tmp.addAll(v.to());
				tmp.addAll(v.from());
			});
		return tmp;
	}

	@Override
	public Set<State> states() {
		return structure.keySet();
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
	public Set<Transition> to(State s){
		return structure.get(s).to();
	}
	
	@Override
	public Set<Transition> from(State s){
		return structure.get(s).from();
	}
}
