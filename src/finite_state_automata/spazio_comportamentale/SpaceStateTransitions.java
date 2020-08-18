package finite_state_automata.spazio_comportamentale;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import finite_state_automata.comportamental.Transition;

import java.util.Set;

public class SpaceStateTransitions {
	
	private SpaceState source;
	private HashMap<Transition, SpaceState> out;
	
	public SpaceStateTransitions(SpaceState source) {
		this.source = source;
		out = new HashMap<Transition, SpaceState>();
	}
	
	public SpaceState getSource() {
		return source;
	}
	
	public boolean hasOutputTransitions() {
		return !out.isEmpty();
	}
	
	public HashMap<Transition, SpaceState> getOutputTransitions(){
		return out;
	}
	
	public Transition getInputTransition(SpaceState to) {
		if(hasOutputState(to)) {
			for(Entry<Transition, SpaceState> entry: out.entrySet()) {
				if(entry.getValue().equals(to))
					return entry.getKey();
			}
		}
		return null;
	}
	
	public boolean addOutputTransition(Transition t, SpaceState s) {
		if(!out.containsKey(t)) {
			out.put(t, s);
			return true;
		}
		return false;
	}
	

	public boolean hasOutputState(SpaceState to) {
		return out.containsValue(to);
	}
	
	@Override
	public boolean equals(Object otherState) {
		SpaceStateTransitions other = (SpaceStateTransitions) otherState;
		return this.source.equals(other.source);
		
	}

	public boolean removeOutputState(SpaceState toRemove) {
		if(hasOutputState(toRemove)) {
			Set<Entry<Transition, SpaceState>> entries = new HashSet<Entry<Transition, SpaceState>>(out.entrySet()); //evitare ConcurrentModificationException
			for(Entry<Transition, SpaceState> entry: entries) {
				if(entry.getValue().equals(toRemove))
					out.remove(entry.getKey());
			}
		}
		return false;
	}
	

}
