package spazio_comportamentale;

import java.util.Set;

import comportamentale_fa.ComportamentaleFANet;
import comportamentale_fa.ComportamentaleTransition;

public class SpazioComportamentale {
	
	private ComportamentaleFANet net;
	private SpaceAutomaComportamentale spazioComp;
	
	public SpazioComportamentale(ComportamentaleFANet net) {
		this.net = net;
		spazioComp = new SpaceAutomaComportamentale("Spazio Comportamentale");		
	}
	
	public SpaceAutomaComportamentale generaSpazioComportamentale() {
		if(spazioComp.states().isEmpty()) {
			SpaceState initial = new SpaceState(Integer.toString(spazioComp.states().size()), net.getInitialStates(), net.getActiveEvents());
			spazioComp.insert(initial);
			spazioComp.setInitial(initial);
			buildSpace(initial, net.enabledTransitions());
			net.restoreInitial();
		}
		return spazioComp;
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
			spazioComp.insert(state);
	}
	
	private void scattoTransizione(SpaceState source, ComportamentaleTransition transition) {
		net.transitionTo(transition);	
		if(spazioComp.states().size() == 20)
			System.out.println();
		SpaceState destination = new SpaceState(Integer.toString(spazioComp.states().size()), net.getActualStates(), net.getActiveEvents());
		if(!spazioComp.insert(destination)) {
			SpaceState toSearch = destination;
			destination = spazioComp.states().stream().filter(s -> s.equals(toSearch)).iterator().next();
		}
		SpaceTransition<SpaceState> spaceTransition = new SpaceTransition<SpaceState>(source, destination, transition);
		boolean added = false;
		if(added = spazioComp.add(spaceTransition)) {
			System.out.println(added);
			buildSpace(destination, net.enabledTransitions());	
		}
	}

}
