package spazio_comportamentale;

import java.util.Set;

import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalTransition;

public class SpazioComportamentale {
	
	private CFSMnetwork net;
	private SpaceAutomaComportamentale spazioComp;
	
	public SpazioComportamentale(CFSMnetwork net) {
		this.net = net;
		spazioComp = new SpaceAutomaComportamentale("Spazio Comportamentale");		
	}
	
	public SpaceAutomaComportamentale generaSpazioComportamentale() {
		if(spazioComp.states().isEmpty()) {
			SpaceState initial = new SpaceState(net.getInitialStates(), net.getActiveEvents());
			spazioComp.insert(initial);
			spazioComp.setInitial(initial);
			buildSpace(initial, net.enabledTransitions()); 
			net.restoreInitial();
		}
		return spazioComp;
	}
	
	private void buildSpace(SpaceState state, Set<ComportamentalTransition> enabledTransitions) {
		if(enabledTransitions.size()>1) {
			for(ComportamentalTransition transition: enabledTransitions) {
				net.restoreState(state);
				scattoTransizione(state, transition); 
			}		
		} else if (enabledTransitions.size()==1)
			scattoTransizione(state, enabledTransitions.iterator().next());
		else 
			spazioComp.insert(state);
	}
	
	private void scattoTransizione(SpaceState source, ComportamentalTransition transition) {
		net.transitionTo(transition);	
		if(spazioComp.states().size() == 20)
			System.out.println();
		SpaceState destination = new SpaceState(net.getActualStates(), net.getActiveEvents());
		if(!spazioComp.insert(destination)) {
			SpaceState toSearch = destination;
			destination = spazioComp.states().stream().filter(s -> s.equals(toSearch)).iterator().next();
		}
		SpaceTransition<SpaceState> spaceTransition = new SpaceTransition<SpaceState>(source, destination, transition);
		boolean added = false;
		if(added = spazioComp.add(spaceTransition)) {
			//System.out.println(added);
			buildSpace(destination, net.enabledTransitions());	
		}
	}
}