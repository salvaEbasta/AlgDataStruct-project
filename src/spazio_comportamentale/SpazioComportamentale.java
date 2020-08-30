package spazio_comportamentale;

import java.util.Set;

import algorithm_interfaces.Algorithm;
import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalTransition;

public class SpazioComportamentale extends Algorithm<SpaceAutomaComportamentale>{

	private CFSMnetwork net;
	private SpaceAutomaComportamentale spazioComp;

	public SpazioComportamentale(CFSMnetwork net) {
		this.net = net;
		spazioComp = new SpaceAutomaComportamentale("Spazio Comportamentale");		
	}

	@Override
	public SpaceAutomaComportamentale call() throws Exception {
		if(spazioComp.states().isEmpty()) {
			SpaceState initial = new SpaceState(net.getInitialStates(), net.getActiveEvents());
			spazioComp.insert(initial);
			spazioComp.setInitial(initial);
			buildSpace(initial, net.enabledTransitions()); 
			net.restoreInitial();
			spazioComp.potatura();
			spazioComp.ridenominazione();
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
		SpaceState destination = new SpaceState(net.getCurrentStates(), net.getActiveEvents());
		if(!spazioComp.insert(destination)) {
			SpaceState toSearch = destination;
			destination = spazioComp.states().stream().filter(s -> s.equals(toSearch)).iterator().next();
		}
		SpaceTransition<SpaceState> spaceTransition = new SpaceTransition<SpaceState>(source, destination, transition);
		if(spazioComp.add(spaceTransition))
			buildSpace(destination, net.enabledTransitions());	
	}

	public SpaceAutomaComportamentale midResult() {	
		SpaceAutomaComportamentale midResult = new SpaceAutomaComportamentale(spazioComp);		
		spazioComp = new SpaceAutomaComportamentale("Spazio Comportamentale");		
		return midResult;
	}

}