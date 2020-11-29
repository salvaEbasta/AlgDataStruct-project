package spazio_comportamentale.oss_lineare;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import algorithm_interfaces.Algorithm;
import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalTransition;
import comportamental_fsm.labels.ObservationsList;
import spazio_comportamentale.SpaceTransition;

public class SpazioComportamentaleObs extends Algorithm<SpaceAutomaObsLin>{
	
	private CFSMnetwork net;
	private ObservationsList observation;
	private SpaceAutomaObsLin spazioCompOL;
	
	private int visited;
	
	
	public SpazioComportamentaleObs(CFSMnetwork net, ObservationsList observation) {
		this.net = net;		
		this.observation = observation;
		spazioCompOL = new SpaceAutomaObsLin("Space Automa con Osservazione Lineare " + observation);
		visited = 0;
	}
	
	private Set<ComportamentalTransition> enabledTransitions(){
		Set<ComportamentalTransition> enabledTransitions = new HashSet<ComportamentalTransition>();	
		for(ComportamentalTransition transition: net.enabledTransitions()) {
			if(transition.observableLabel().isEmpty())
				enabledTransitions.add(transition);
			else if(visited < observation.size() && transition.observableLabel().equals(observation.get(visited))) {
				enabledTransitions.add(transition);
			}
		}
		return enabledTransitions;
	}

	@Override
	public SpaceAutomaObsLin call() throws Exception {
		spazioCompOL = new SpaceAutomaObsLin("_"+observation.toString());
		
		SpaceStateObs initial = new SpaceStateObs(net.getInitialStates(), net.getActiveEvents(), 0, observation.size());
		spazioCompOL.insert(initial);
		
		explore(initial, enabledTransitions());
		
		if(visited < observation.size())
			return new SpaceAutomaObsLin("_"+observation.toString());
		
		spazioCompOL.setInitial(initial);
		net.restoreState(initial);
		spazioCompOL.potatura();
		spazioCompOL.ridenominazione();
		return spazioCompOL;
	}
	
	private void explore(SpaceStateObs state, Set<ComportamentalTransition> enabledTs){
		Iterator<ComportamentalTransition> iter = enabledTs.iterator();
		while(iter.hasNext()) {
			boolean keepGoing = false;
			ComportamentalTransition t = iter.next();
			net.restoreState(state);
			net.transitionTo(t);
			
			if(!t.isSilent())
				++visited;
			SpaceStateObs sink = new SpaceStateObs(net.getCurrentStates(), net.getActiveEvents(), visited, observation.size());
			if(!spazioCompOL.hasState(sink)) {
				spazioCompOL.insert(sink);
				keepGoing = true;
			} else {
				for(SpaceStateObs s : spazioCompOL.states())
					if(s.equals(sink)) {
						sink = s;
						break;
					}
			}
			spazioCompOL.add(new SpaceTransition<SpaceStateObs>(state, sink, t));
			if(keepGoing)
				explore(sink, enabledTransitions());
		}
	}
	
	public SpaceAutomaObsLin midResult() {
		SpaceAutomaObsLin midResult = new SpaceAutomaObsLin(spazioCompOL);
		spazioCompOL =  new SpaceAutomaObsLin("Space Automa con Osservazione Lineare ".concat(observation.toString()));		
		return midResult;
	}
	
}
