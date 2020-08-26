package diagnosticatore.algorithms;

import java.util.LinkedList;

import algorithm_interfaces.Algorithm;
import diagnosticatore.SilentClosure;
import spazio_comportamentale.SpaceTransition;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpaceStateObs;

public class SilentClosureBuilder extends Algorithm<SilentClosure>{
	private SpaceAutomaObsLin space;
	private SpaceStateObs state;
	private SilentClosure closure;
	
	public SilentClosureBuilder(SpaceAutomaObsLin space, SpaceStateObs state) {
		this.space = space;
		this.state = state;
	}
	
	@Override
	public SilentClosure call() throws Exception {
		closure = new SilentClosure(state.id());
		if(space.hasState(state) && (space.initialState().equals(state) || space.hasEnteringObsTransitions(state)))
			composeSilentClosure();
		return closure;
	}

	@Override
	public SilentClosure midResult() {
		return closure;
	}
	
	private void composeSilentClosure() {
		log.info(this.getClass().getSimpleName()+"::buildClosure("+state.id()+")");
		
		closure.insert(state);
		closure.setInitial(state);
		LinkedList<SpaceTransition<SpaceStateObs>> queue = new LinkedList<SpaceTransition<SpaceStateObs>>(space.from(state));
		while(queue.size() > 0) {
			SpaceTransition<SpaceStateObs> current = queue.pop();
			
			log.info("Current transition: "+current);
			
			if(current.isSilent()) {
				closure.insert(current.sink());
				closure.add(current);
				queue.addAll(space.from(current.sink()));
			}else {
				log.info("Set exiting: "+current.source().id());
				
				closure.setExiting(current.source());
			}
		}
	}
}
