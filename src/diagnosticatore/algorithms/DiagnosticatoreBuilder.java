package diagnosticatore.algorithms;

import java.util.HashMap;
import java.util.LinkedList;

import algorithm_interfaces.Algorithm;
import diagnosticatore.ClosureSpace;
import diagnosticatore.SilentClosure;
import fsm_interfaces.Transition;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpaceTransition;

public class DiagnosticatoreBuilder extends Algorithm<ClosureSpace>{
	private SpaceAutomaComportamentale space;
	private ClosureSpace cSpace;
	
	public DiagnosticatoreBuilder(SpaceAutomaComportamentale space) {
		this.space = space;
	}
	
	@Override
	public ClosureSpace call() throws Exception{
		log.info(this.getClass().getSimpleName()+"::call("+space.id()+")...");
		
		cSpace = new ClosureSpace(space.id());
		// colors: 1->gray; -1->black
		HashMap<String, Integer> colors = new HashMap<String, Integer>();
		
		SilentClosure initial = new SilentClosureBuilder(space, space.initialState()).call();
		initial = new ClosureDecorator(initial).call();
		cSpace.insert(initial);
		
		colors.put(initial.id(), 0);
		
		LinkedList<SilentClosure> queue = new LinkedList<SilentClosure>();
		queue.add(initial);
		
		while(queue.size() > 0) {
			SilentClosure u = queue.pop();
			
			for(SpaceState s: u.exitStates()) {
				for(SpaceTransition<SpaceState> t : space.from(s)) {
					if(t.isSilent()) 
						continue;
					
					SilentClosure sink = null;
					if(!colors.containsKey(t.sink().id())) {
						sink = new SilentClosureBuilder(space, t.sink()).call();
						sink = new ClosureDecorator(sink).call();
						cSpace.insert(sink);
						colors.put(sink.id(), 0);
						queue.addLast(sink);
					} else {
						sink = cSpace.getState(t.sink().id());
					}
					Transition<SilentClosure> newT = new Transition<SilentClosure>(t.id(), u, sink);
					newT.setObservableLabel(t.observableLabel());
					newT.setRelevantLabel(u.decorationOf(t.source())+t.relevantLabelContent());
					cSpace.add(newT);
				}
			}
			colors.put(u.id(), -1);
		}
		
		cSpace.setInitial(initial);
		return cSpace;
	}
	
	@Override
	public ClosureSpace midResult() {
		ClosureSpace midResult = new ClosureSpace(cSpace);
		cSpace = new ClosureSpace(space.id());
		return midResult;
	}
}
