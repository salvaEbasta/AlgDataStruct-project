package spazio_comportamentale.oss_lineare;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import algorithm_interfaces.MidResult;
import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalTransition;
import comportamental_fsm.labels.ObservationsList;
import spazio_comportamentale.SpaceTransition;

public class SpazioComportamentaleObs implements Callable<SpaceAutomaObsLin>, MidResult<SpaceAutomaObsLin>{
	
	private CFSMnetwork net;
	private ObservationsList observation;
	private int index = 0;
	private SpaceAutomaObsLin spazioCompOL;
	
	
	public SpazioComportamentaleObs(CFSMnetwork net, ObservationsList observation) {
		this.net = net;		
		this.observation = observation;
	}
	
	@Override
	public SpaceAutomaObsLin call() {
		if(spazioCompOL == null) {
			spazioCompOL = new SpaceAutomaObsLin("Space Automa con Osservazione Lineare " + observation);		
			SpaceStateObs initial = new SpaceStateObs(net.getInitialStates(), net.getActiveEvents(), index, observation.size());
			spazioCompOL.insert(initial);
			spazioCompOL.setInitial(initial);
			buildSpace(initial, enabledTransitions());
			net.restoreState(initial);
			spazioCompOL.potatura();
			spazioCompOL.ridenominazione();
		}
		return spazioCompOL;
	}
	
	private void buildSpace(SpaceStateObs state, Set<ComportamentalTransition> enabledTransitions) {
//		try {
//			Thread.sleep(1500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if(enabledTransitions.size()>1) {
			for(ComportamentalTransition transition: enabledTransitions) {
				net.restoreState(state);
				scattoTransizione(state, transition); 
			}		
		} else if (enabledTransitions.size()==1)
			scattoTransizione(state, enabledTransitions.iterator().next());
		else 
			spazioCompOL.insert(state);
	}
	
	private void scattoTransizione(SpaceStateObs source, ComportamentalTransition transition) {
		net.transitionTo(transition);
		if(index<observation.size() && transition.observableLabel().equals(observation.get(index)))
			index++;
		SpaceStateObs destination = new SpaceStateObs(net.getActualStates(), net.getActiveEvents(), index, observation.size());
		if(!spazioCompOL.insert(destination)) {
			SpaceStateObs toSearch = destination;
			destination = spazioCompOL.states().stream().filter(s -> s.equals(toSearch)).iterator().next();
		}
		SpaceTransition<SpaceStateObs> spaceTransition = new SpaceTransition<SpaceStateObs>(source, destination, transition);
		if(spazioCompOL.add(spaceTransition))
			buildSpace(destination, enabledTransitions());	
	}
	
	private Set<ComportamentalTransition> enabledTransitions() {
		Set<ComportamentalTransition> enabledTransitions = new HashSet<ComportamentalTransition>();	
		for(ComportamentalTransition transition: net.enabledTransitions()) {
			if(transition.observableLabel().isEmpty())
				enabledTransitions.add(transition);
			else if(index<observation.size() && transition.observableLabel().equals(observation.get(index))) {
				enabledTransitions.add(transition);
			}		
		}
		return enabledTransitions;
	}
	
	public SpaceAutomaObsLin midResult() {
		if(spazioCompOL == null)
			return new SpaceAutomaObsLin("Space Automa con Osservazione Lineare ".concat(observation.toString()));		
		return spazioCompOL;
	}
}
