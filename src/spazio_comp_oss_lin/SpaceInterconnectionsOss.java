package spazio_comp_oss_lin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import comportamentale_fa.ComportamentaleTransition;

import java.util.TreeSet;

public class SpaceInterconnectionsOss implements Iterable<SpaceStateTransitionsOss>{
	
	private TreeSet<SpaceStateTransitionsOss> states;
	
	public SpaceInterconnectionsOss() {
		states = new TreeSet<SpaceStateTransitionsOss>((SpaceStateTransitionsOss state1, SpaceStateTransitionsOss state2) -> {
			return new Integer(state1.getSource().id()).compareTo(new Integer(state2.getSource().id()));
		});		
	}
	
	public boolean containsKey(SpaceStateOss key) { //faccio questo e non uso il metodo originale containsKey perchè quest'ultimo controlla prima gli hashcode e poi gli equals
		for(SpaceStateTransitionsOss stateTransitions: states) {
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
	
	public boolean add(SpaceStateOss state) {
		if(!containsKey(state)) {
			return states.add(new SpaceStateTransitionsOss(state));
		}
		return false;
	}
	
	public Iterator<SpaceStateTransitionsOss> iterator(){
		return states.iterator();
	}
	
	public boolean potatura() {
		int prevSize = size();
		TreeSet<SpaceStateTransitionsOss> setCopy = new TreeSet<SpaceStateTransitionsOss>(states);
		for(SpaceStateTransitionsOss stateTransition: setCopy) {
			checkPotatura(stateTransition.getSource());
		}
		ridenominazione();
		return size() != prevSize;
	}
	
	private void checkPotatura(SpaceStateOss state) {
		if(!state.isFinalState() && !get(state).hasOutputTransitions()) {
			Set<SpaceStateOss> inputStates = getInputStates(state);
			removeState(state);		
			for(SpaceStateOss inputState : inputStates) 
				checkPotatura(inputState);		
		}
	}
	
	private void removeState(SpaceStateOss toRemove) {
		states.remove(get(toRemove));
		for(SpaceStateTransitionsOss state: states) {
			if(state.hasOutputState(toRemove))
				state.removeOutputState(toRemove);			
		}
	}
	
	public void ridenominazione() {
		int i=0;
		for(SpaceStateTransitionsOss spaceTransitions: states)
			spaceTransitions.getSource().setId(Integer.toString(i++));
	}
	
	private Set<SpaceStateOss> getInputStates(SpaceStateOss to){
		Set<SpaceStateOss> inputStates = new HashSet<SpaceStateOss>();
		for(SpaceStateTransitionsOss stateTransitions: states) {
			if(stateTransitions.hasOutputState(to))
				inputStates.add(stateTransitions.getSource());
		}
		return inputStates;
	}
	
	public HashMap<ComportamentaleTransition, SpaceStateOss> getInputTransitions(SpaceStateOss to){
		HashMap<ComportamentaleTransition, SpaceStateOss> inputTransitions = new HashMap<ComportamentaleTransition, SpaceStateOss>();
		for(SpaceStateTransitionsOss stateTransitions: states) {
			if(stateTransitions.hasOutputState(to))
				inputTransitions.put(stateTransitions.getInputTransition(to), stateTransitions.getSource());
		}
		return inputTransitions;
	}
	
	public boolean addOutputTransition(SpaceStateOss source, ComportamentaleTransition transition, SpaceStateOss outState) {
		if(containsKey(source))
			return get(source).addOutputTransition(transition, outState);
		return false;		
	}
	
	private SpaceStateTransitionsOss get(SpaceStateOss s) {
		for(SpaceStateTransitionsOss stateTransitions: states) {
			if(stateTransitions.getSource().equals(s))
				return stateTransitions;
		}
		return null;
	}
	
	public SpaceStateOss getState(SpaceStateOss s) {
		for(SpaceStateTransitionsOss stateTransitions: states) {
			if(stateTransitions.getSource().equals(s))
				return stateTransitions.getSource();
		}
		return null;
	}
	
	public HashMap<ComportamentaleTransition, SpaceStateOss> getOutputTransitions(SpaceStateOss state) {
		if(containsKey(state))
			return get(state).getOutputTransitions();
		return null;
	}

}
