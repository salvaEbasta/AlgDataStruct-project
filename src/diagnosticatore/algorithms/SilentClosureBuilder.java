package diagnosticatore.algorithms;

import java.util.LinkedList;

import algorithm_interfaces.Algorithm;
import diagnosticatore.SilentClosure;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpaceTransition;

public class SilentClosureBuilder extends Algorithm<SilentClosure>{
	private SpaceAutomaComportamentale space;
	private SpaceState state;
	private SilentClosure closure;
	
	public SilentClosureBuilder(SpaceAutomaComportamentale space, SpaceState state) {
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
		LinkedList<SpaceTransition<SpaceState>> queue = new LinkedList<SpaceTransition<SpaceState>>(space.from(state));
		while(queue.size() > 0) {
			SpaceTransition<SpaceState> current = queue.pop();
			
			log.info("Current transition: "+current);
			
			if(current.isSilent() && !closure.transitions().contains(current)) {
				closure.insert(current.sink());
				closure.add(current);
				queue.addAll(space.from(current.sink()));
			}else if(!closure.transitions().contains(current)){
				log.info("Set exiting: "+current.source().id());
				
				closure.setExiting(current.source());
			}
		}
	}
}
