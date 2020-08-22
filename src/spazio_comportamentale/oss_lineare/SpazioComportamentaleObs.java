package spazio_comportamentale.oss_lineare;

import java.util.HashSet;
import java.util.Set;

import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalTransition;
import comportamental_fsm.labels.ObservableLabel;
import spazio_comportamentale.SpaceTransition;

public class SpazioComportamentaleObs {
	
	private CFSMnetwork net;
	private SpaceAutomaObsLin spazioCompOL;
	
	public SpazioComportamentaleObs(CFSMnetwork net) {
		this.net = net;		
	}
	
	public SpaceAutomaObsLin generaSpazioOsservazione(ObservableLabel[] observation) {
		if(spazioCompOL == null) {
			int index = 0;
			spazioCompOL = new SpaceAutomaObsLin("Space Automa con Osservazione Lineare ".concat(observation.toString()));		
			SpaceStateObs initial = new SpaceStateObs(net.getInitialStates(), net.getActiveEvents(), index, observation.length);
			spazioCompOL.insert(initial);
			spazioCompOL.setInitial(initial);
			buildSpace(initial,  enabledTransitions(observation, index), observation, index);
			net.restoreState(initial);
		}
		return spazioCompOL;
	}
	
	private void buildSpace(SpaceStateObs state, Set<ComportamentalTransition> enabledTransitions, ObservableLabel[] observation, int index) {
		if(enabledTransitions.size()>1) {
			for(ComportamentalTransition transition: enabledTransitions) {
				net.restoreState(state);
				scattoTransizione(state, transition, observation, index); 
			}		
		} else if (enabledTransitions.size()==1)
			scattoTransizione(state, enabledTransitions.iterator().next(), observation, index);
		else 
			spazioCompOL.insert(state);
	}
	
	private void scattoTransizione(SpaceStateObs source, ComportamentalTransition transition, ObservableLabel[] observation, int index) {
		net.transitionTo(transition);
		if(index<observation.length && transition.observableLabel().equals(observation[index]))
			index++;
		SpaceStateObs destination = new SpaceStateObs(net.getActualStates(), net.getActiveEvents(), index, observation.length);
		if(!spazioCompOL.insert(destination)) {
			SpaceStateObs toSearch = destination;
			destination = spazioCompOL.states().stream().filter(s -> s.equals(toSearch)).iterator().next();
		}
		SpaceTransition<SpaceStateObs> spaceTransition = new SpaceTransition<SpaceStateObs>(source, destination, transition);
		if(spazioCompOL.add(spaceTransition))
			buildSpace(destination, enabledTransitions(observation, index), observation, index);	
	}
	
	private Set<ComportamentalTransition> enabledTransitions(ObservableLabel[] observation, int index) {
		Set<ComportamentalTransition> enabledTransitions = new HashSet<ComportamentalTransition>();	
		for(ComportamentalTransition transition: net.enabledTransitions()) {
			if(transition.observableLabel().isEmpty())
				enabledTransitions.add(transition);
			else if(index<observation.length && transition.observableLabel().equals(observation[index])) {
				enabledTransitions.add(transition);
			}		
		}
		return enabledTransitions;
	}
}
