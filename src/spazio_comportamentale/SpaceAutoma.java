package spazio_comportamentale;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import fsm_interfaces.Automa;
import fsm_interfaces.Interconnections;

public class SpaceAutoma<S extends SpaceState> extends Automa<S, SpaceTransition<S>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpaceAutoma(String id) {
		super(id);
	}
	
	public SpaceAutoma(SpaceAutoma<S> sa) {
		super(sa);
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
		Set<S> setCopy = new LinkedHashSet<S>(states());
		for(S state: setCopy) {
			checkPotatura(state);
		}
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
	 * Controlla se uno stato ha una transizione entrante osservabile
	 * @param s
	 * @return
	 */
	public boolean hasEnteringObsTransitions(S s) {
		if(hasState(s))
			return super.structure.get(s).hasObservableEntering();
		else
			return false;
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
	
	@Override
	public Object clone() {
		SpaceAutoma<S> deepCopy = (SpaceAutoma<S>) super.clone();
		return deepCopy;
	}

}
