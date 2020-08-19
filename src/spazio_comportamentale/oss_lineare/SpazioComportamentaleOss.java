package spazio_comportamentale.oss_lineare;

import java.util.HashSet;
import java.util.Set;

import comportamentale_fa.ComportamentaleFANet;
import comportamentale_fa.ComportamentaleTransition;
import comportamentale_fa.labels.ObservableLabel;
import spazio_comportamentale.SpaceTransition;

public class SpazioComportamentaleOss {
	
	private ComportamentaleFANet net;
	private SpaceAutomaObsLin spazioCompOL;
	
	public SpazioComportamentaleOss(ComportamentaleFANet net) {
		this.net = net;
		spazioCompOL = new SpaceAutomaObsLin("Space Automa con Osservazione Lineare");		
	}
	
	public SpaceAutomaObsLin generaSpazioOsservazione(ObservableLabel[] observation) {
		if(spazioCompOL.states().isEmpty()) {
			int index = 0;
			SpaceStateOss initial = new SpaceStateOss(Integer.toString(spazioCompOL.states().size()), net.getInitialStates(), net.getActiveEvents(), index, observation.length);
			spazioCompOL.insert(initial);
			spazioCompOL.setInitial(initial);
			buildSpace(initial,  enabledTransitions(observation, index), observation, index);
			net.restoreState(initial);
		}
		return spazioCompOL;
	}
	
	private void buildSpace(SpaceStateOss state, Set<ComportamentaleTransition> enabledTransitions, ObservableLabel[] observation, int index) {
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
	
	private void scattoTransizione(SpaceStateOss source, ComportamentaleTransition transition, ObservableLabel[] observation, int index) {
		net.transitionTo(transition);
		if(index<observation.length && transition.getObservableLabel().equals(observation[index]))
			index++;
		SpaceStateOss destination = new SpaceStateOss(Integer.toString(spazioCompOL.states().size()), net.getActualStates(), net.getActiveEvents(), index, observation.length);
		if(!spazioCompOL.insert(destination)) {
			SpaceStateOss toSearch = destination;
			destination = spazioCompOL.states().stream().filter(s -> s.equals(toSearch)).iterator().next();
		}
		SpaceTransition<SpaceStateOss> spaceTransition = new SpaceTransition<SpaceStateOss>(source, destination, transition);
		if(spazioCompOL.add(spaceTransition))
			buildSpace(destination, enabledTransitions(observation, index), observation, index);	
	}
	
	private Set<ComportamentaleTransition> enabledTransitions(ObservableLabel[] observation, int index) {
		Set<ComportamentaleTransition> enabledTransitions = new HashSet<ComportamentaleTransition>();	
		for(ComportamentaleTransition transition: net.enabledTransitions()) {
			if(transition.getObservableLabel().isEmpty())
				enabledTransitions.add(transition);
			else if(index<observation.length && transition.getObservableLabel().equals(observation[index])) {
				enabledTransitions.add(transition);
			}		
		}
		return enabledTransitions;
	}


}
