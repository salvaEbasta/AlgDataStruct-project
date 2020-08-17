package spazio_comportamentale;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import comportamentale_fa.ComportamentaleTransition;

import java.util.TreeSet;

public class SpaceInterconnections implements Iterable<SpaceStateTransitions>{
	
	private TreeSet<SpaceStateTransitions> states;
	
	public SpaceInterconnections() {
		states = new TreeSet<SpaceStateTransitions>((SpaceStateTransitions state1, SpaceStateTransitions state2) -> {
			return new Integer(state1.getSource().id()).compareTo(new Integer(state2.getSource().id()));
		});		
	}
	
	public boolean containsKey(SpaceState key) { //faccio questo e non uso il metodo originale containsKey perchè quest'ultimo controlla prima gli hashcode e poi gli equals
		for(SpaceStateTransitions stateTransitions: states) {
			if(stateTransitions.getSource().equals(key))
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
	
//	public Set<SpaceState> statesSet() {
//		return states.keySet();
//	}
	
	public boolean add(SpaceState state) {
		if(!containsKey(state)) {
			return states.add(new SpaceStateTransitions(state));
		}
		return false;
	}
	
	public Iterator<SpaceStateTransitions> iterator(){
		return states.iterator();
	}
	
	public boolean potatura() {
		int prevSize = size();
		TreeSet<SpaceStateTransitions> setCopy = new TreeSet<SpaceStateTransitions>(states);
		for(SpaceStateTransitions stateTransition: setCopy) {
			checkPotatura(stateTransition.getSource());
		}
		return size() != prevSize;
	}
	
	private void checkPotatura(SpaceState state) {
		if(!state.isFinalState() && !get(state).hasOutputTransitions()) {
			Set<SpaceState> inputStates = getInputStates(state);
			removeState(state);		
			for(SpaceState inputState : inputStates) 
				checkPotatura(inputState);		
		}
	}
	
	private void removeState(SpaceState toRemove) {
		states.remove(get(toRemove));
		for(SpaceStateTransitions state: states) {
			if(state.hasOutputState(toRemove))
				state.removeOutputState(toRemove);			
		}
	}
	
	private Set<SpaceState> getInputStates(SpaceState to){
		Set<SpaceState> inputStates = new HashSet<SpaceState>();
		for(SpaceStateTransitions stateTransitions: states) {
			if(stateTransitions.hasOutputState(to))
				inputStates.add(stateTransitions.getSource());
		}
		return inputStates;
	}
	
	public HashMap<ComportamentaleTransition, SpaceState> getInputTransitions(SpaceState to){
		HashMap<ComportamentaleTransition, SpaceState> inputTransitions = new HashMap<ComportamentaleTransition, SpaceState>();
		for(SpaceStateTransitions stateTransitions: states) {
			if(stateTransitions.hasOutputState(to))
				inputTransitions.put(stateTransitions.getInputTransition(to), stateTransitions.getSource());
		}
		return inputTransitions;
	}
	
	public boolean addOutputTransition(SpaceState source, ComportamentaleTransition transition, SpaceState outState) {
		if(containsKey(source))
			return get(source).addOutputTransition(transition, outState);
		return false;		
	}
	
	private SpaceStateTransitions get(SpaceState s) {
		for(SpaceStateTransitions stateTransitions: states) {
			if(stateTransitions.getSource().equals(s))
				return stateTransitions;
		}
		return null;
	}
	
	public SpaceState getState(SpaceState s) {
		for(SpaceStateTransitions stateTransitions: states) {
			if(stateTransitions.getSource().equals(s))
				return stateTransitions.getSource();
		}
		return null;
	}
	
	public HashMap<ComportamentaleTransition, SpaceState> getOutputTransitions(SpaceState state) {
		if(containsKey(state))
			return get(state).getOutputTransitions();
		return null;
	}

}
