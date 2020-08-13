package comportamentale_fa;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class SpaceTree {
	
	private LinkedHashMap<SpaceState, Interconnections> states;
	
	public SpaceTree() {
		states = new LinkedHashMap<SpaceState, Interconnections>();		
	}
	
	public boolean containsKey(SpaceState key) { //faccio questo e non uso il metodo originale containsKey perchè quest'ultimo controlla prima gli hashcode e poi gli equals
		for(SpaceState state: states.keySet()) {
			if(state.equals(key))
				return true;
		}
		return false;
	}
	
	public boolean isEmpty() {
		return states.isEmpty();
	}
	
	public int size() {
		return states.size();
	}
	
	public Set<SpaceState> statesSet() {
		return states.keySet();
	}
	
	public boolean put(SpaceState state) {
		if(!containsKey(state)) {
			states.put(state, new Interconnections());
			return true;
		}
		return false;
	}
	
	public boolean potatura() {
		int prevSize = size();
		Set<SpaceState> toRemove = new HashSet<SpaceState>();
		for(SpaceState state: states.keySet()) {
			checkPotatura(state, toRemove);
		}
		for(SpaceState state: toRemove) //per evitare ConcurretnModificationException
			states.remove(state);
		return size() != prevSize;
	}
	
	private void checkPotatura(SpaceState state, Set<SpaceState> toRemove) {	
		if(!state.isFinalState() && states.get(state).from().isEmpty())
			toRemove.add(state);
		// tornare di uno stato indietro e controllare il precedente
	}
	
	public boolean addInputTransition(SpaceState state, Transition transition) {
		if(containsKey(state))
			return states.get(state).newIn(transition);
		return false;		
	}
	
	public boolean addOutputTransitions(SpaceState state, Set<Transition> transitions) {
		if(containsKey(state))
			return states.get(state).addAllOut(transitions);
		return false;		
	}

	public Set<Transition> getInputTransitions(SpaceState state) {
		if(containsKey(state))
			return states.get(state).to();
		return null;
	}
	
	public Set<Transition> getOutputTransitions(SpaceState state) {
		if(containsKey(state))
			return states.get(state).from();
		return null;
	}

}
