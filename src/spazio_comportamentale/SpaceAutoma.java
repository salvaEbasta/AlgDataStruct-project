package spazio_comportamentale;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import commoninterfaces.Automa;
import commoninterfaces.Interconnections;

public class SpaceAutoma<S extends SpaceState> extends Automa<S, SpaceTransition<S>> {

	public SpaceAutoma(String id) {
		super(id);
	}

	@Override
	public Set<S> acceptingStates() {
		Set<S> acceptingStates = new HashSet<S>();
		for(S state: states()) {
			if(state.isFinal())
				acceptingStates.add(state);
		}
		return acceptingStates;
	}
	
	/**
	 * Fa SOLO la potatura dell'albero
	 * @return
	 */
	public boolean potatura() {
		int prevSize = states().size();
		Set<S> setCopy = new HashSet<S>(states());
		for(S state: setCopy) {
			checkPotatura(state);
		}
		ridenominazione();
		return states().size() != prevSize;
	}
	
	private void checkPotatura(S state) {
		if(!state.isFinal() && from(state).isEmpty() && states().contains(state)) {
			Set<SpaceTransition<S>> inputTransitions = to(state);
			remove(state);		
			for(SpaceTransition<S> inputT : inputTransitions) 
				checkPotatura(inputT.source());		
		}
	}
	
	/**
	 * Svolge la ridenominazione
	 */
	public HashMap<Integer, S> ridenominazione() {
		HashMap<Integer, S> ridenominazione = new HashMap<Integer, S>();
		int i=0;
		for(S state: states()) {
			ridenominazione.put(i, state);
			Interconnections<S, SpaceTransition<S>> interconnections = structure.get(state);	
			structure.remove(state);
			state.setId(Integer.toString(i++));			
			structure.put(state, interconnections);			
		}
		return ridenominazione;
	}
	

}
