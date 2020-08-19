package spazio_comportamentale;

import java.util.Set;

import comportamentale_fa.ComportamentaleFANet;
import comportamentale_fa.ComportamentaleTransition;

public class SpazioComportamentale {
	
	private ComportamentaleFANet net;
	private SpaceAutomaComportamentale states;
	
	public SpazioComportamentale(ComportamentaleFANet net) {
		this.net = net;
		states = new SpaceAutomaComportamentale("Spazio Comportamentale");		
	}
	
	public SpaceAutomaComportamentale generaSpazio() {
		if(states.states().isEmpty()) {
			SpaceState initial = new SpaceState(Integer.toString(states.states().size()), net.getInitialStates(), net.getActiveEvents());
			states.insert(initial);
			states.setInitial(initial);
			buildSpace(initial, net.enabledTransitions());
			net.restoreState(initial);
		}
		return states;
	}
	
	private void buildSpace(SpaceState state, Set<ComportamentaleTransition> enabledTransitions) {
		if(enabledTransitions.size()>1) {
			for(ComportamentaleTransition transition: enabledTransitions) {
				net.restoreState(state);
				scattoTransizione(state, transition); 
			}		
		} else if (enabledTransitions.size()==1)
			scattoTransizione(state, enabledTransitions.iterator().next());
		else 
			states.insert(state);
	}
	
	private void scattoTransizione(SpaceState source, ComportamentaleTransition transition) {
		net.transitionTo(transition);	
		SpaceState next = new SpaceState(Integer.toString(states.states().size()), net.getActualStates(), net.getActiveEvents());
		if(states.insert(next)) {
			SpaceState toSearch = next;
			next = states.states().stream().filter(s -> s.equals(toSearch)).iterator().next();
		}
		SpaceTransition<SpaceState> spaceTransition = new SpaceTransition<SpaceState>(source, next, transition);
		if(states.add(spaceTransition))
			buildSpace(next, net.enabledTransitions());	
	}

}
