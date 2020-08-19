package spazio_comportamentale.oss_lineare;

import java.util.HashSet;
import java.util.Set;

import comportamentale_fa.ComportamentaleFANet;
import comportamentale_fa.ComportamentaleTransition;
import comportamentale_fa.labels.ObservableLabel;
import spazio_comportamentale.SpaceTransition;

public class SpazioComportamentaleObs {
	
	private ComportamentaleFANet net;
	private SpaceAutomaObsLin spazioCompOL;
	
	public SpazioComportamentaleObs(ComportamentaleFANet net) {
		this.net = net;
		spazioCompOL = new SpaceAutomaObsLin("Space Automa con Osservazione Lineare");		
	}
	
	public SpaceAutomaObsLin generaSpazioOsservazione(ObservableLabel[] observation) {
		if(spazioCompOL.states().isEmpty()) {
			int index = 0;
			SpaceStateObs initial = new SpaceStateObs(net.getInitialStates(), net.getActiveEvents(), index, observation.length);
			spazioCompOL.insert(initial);
			spazioCompOL.setInitial(initial);
			buildSpace(initial,  enabledTransitions(observation, index), observation, index);
			net.restoreState(initial);
		}
		return spazioCompOL;
	}
	
	private void buildSpace(SpaceStateObs state, Set<ComportamentaleTransition> enabledTransitions, ObservableLabel[] observation, int index) {
		if(enabledTransitions.size()>1) {
			for(ComportamentaleTransition transition: enabledTransitions) {
				net.restoreState(state);
				scattoTransizione(state, transition, observation, index); 
			}		
		} else if (enabledTransitions.size()==1)
			scattoTransizione(state, enabledTransitions.iterator().next(), observation, index);
		else 
			spazioCompOL.insert(state);
	}
	
	private void scattoTransizione(SpaceStateObs source, ComportamentaleTransition transition, ObservableLabel[] observation, int index) {
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
	
	private Set<ComportamentaleTransition> enabledTransitions(ObservableLabel[] observation, int index) {
		Set<ComportamentaleTransition> enabledTransitions = new HashSet<ComportamentaleTransition>();	
		for(ComportamentaleTransition transition: net.enabledTransitions()) {
			if(transition.observableLabel().isEmpty())
				enabledTransitions.add(transition);
			else if(index<observation.length && transition.observableLabel().equals(observation[index])) {
				enabledTransitions.add(transition);
			}		
		}
		return enabledTransitions;
	}


}
