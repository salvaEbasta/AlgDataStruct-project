package spazio_comportamentale;

import java.util.HashSet;
import java.util.Set;
import commoninterfaces.Automa;
import commoninterfaces.Interconnections;

public abstract class SpaceAutoma<S extends SpaceState> extends Automa<S, SpaceTransition<S>> {

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
	
	public void ridenominazione() {
		int i=0;
		for(S state: states()) {
			Interconnections<SpaceTransition<S>> interconnections = structure.get(state);	
			structure.remove(state);
			state.setId(Integer.toString(i++));			
			structure.put(state, interconnections);			
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(S state: states()) {
			sb.append(state.toString());
			Set<SpaceTransition<S>>  in = to(state);
			Set<SpaceTransition<S>> out = from(state);
			if(!in.isEmpty()) {
				sb.append("\n\t- Input Transitions:");
				for(SpaceTransition<S> inTransition: in) {
					sb.append(String.format("\n\t\t* %s", inTransition));
				}
			}
			if(!out.isEmpty()) {
				sb.append("\n\t- Output Transitions:");
				for(SpaceTransition<S> outTransition: out) {
					sb.append(String.format("\n\t\t* %s", outTransition));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
