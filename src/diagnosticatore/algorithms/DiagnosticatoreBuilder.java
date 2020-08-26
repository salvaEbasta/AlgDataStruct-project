package diagnosticatore.algorithms;

import java.util.LinkedList;

import algorithm_interfaces.Algorithm;
import diagnosticatore.ClosureSpace;
import diagnosticatore.SilentClosure;
import fsm_interfaces.Transition;
import spazio_comportamentale.SpaceTransition;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpaceStateObs;

public class DiagnosticatoreBuilder extends Algorithm<ClosureSpace>{
	private SpaceAutomaObsLin space;
	private ClosureSpace cSpace;
	
	public DiagnosticatoreBuilder(SpaceAutomaObsLin space) {
		this.space = space;
	}
	
	@Override
	public ClosureSpace call() throws Exception {
		log.info(this.getClass().getSimpleName()+"::call("+space.id()+")...");
		
		cSpace = new ClosureSpace(space.id());
		SilentClosure initial = new SilentClosureBuilder(space, space.initialState()).call();
		initial = new ClosureDecorator(initial).call();
		
		LinkedList<SilentClosure> queue = new LinkedList<SilentClosure>();
		queue.add(initial);
		composeClosureSpace(queue);
		cSpace.setInitial(initial);
		return cSpace;
	}

	@Override
	public ClosureSpace midResult() {
		return cSpace;
	}
	
	private void composeClosureSpace(LinkedList<SilentClosure> queue) throws Exception{
		if(queue.size() > 0) {
			SilentClosure closure = queue.pop();
			cSpace.insert(closure);
			for(SpaceStateObs s : closure.exitStates()) {
				for(SpaceTransition<SpaceStateObs> t : space.from(s)) {
					if(!t.isSilent()) {
						handleTransition(t, closure, queue);
					}
				}
			}
			composeClosureSpace(queue);
		}
	}
	
	private void handleTransition(SpaceTransition<SpaceStateObs> t, SilentClosure closure, LinkedList<SilentClosure> queue) throws Exception{
		SilentClosure sink = null;
		if(!cSpace.hasState(t.sink().id())) {
			sink = new SilentClosureBuilder(space, t.sink()).call();
			sink = new ClosureDecorator(sink).call();
			cSpace.insert(sink);
			queue.add(sink);
		}else {
			sink = cSpace.getState(t.sink().id());
		}
		Transition<SilentClosure> newT = new Transition<SilentClosure>(t.id(), closure, sink);
		newT.setObservableLabel(t.observableLabel());
		newT.setRelevantLabel(closure.decorationOf(t.source())+t.relevantLabelContent());
		cSpace.add(newT);
	}
}
