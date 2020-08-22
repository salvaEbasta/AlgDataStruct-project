package diagnosticatore;

import java.util.LinkedList;

import fsm_algorithms.RegexBuilder;
import spazio_comportamentale.BuilderSpaceComportamentale;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpaceTransition;

public class ClosureBuilder {
	public static SilentClosure build(SpaceAutomaComportamentale space, SpaceState state){
		SilentClosure silentClosure = new SilentClosure(state.id());
		if(space.hasState(state) && (space.initialState().equals(state) || space.hasEnteringObsTransitions(state)))
			composeSilentClosure(space, state, silentClosure);
		return silentClosure;
	}
	
	private static void composeSilentClosure(SpaceAutomaComportamentale space, SpaceState state, SilentClosure closure) {
		closure.insert(state);
		closure.setInitial(state);
		LinkedList<SpaceTransition<SpaceState>> queue = new LinkedList<SpaceTransition<SpaceState>>(space.from(state));
		while(queue.size() > 0) {
			SpaceTransition<SpaceState> current = queue.pop();
			if(current.isSilent()) {
				closure.insert(current.sink());
				closure.add(current);
				queue.addAll(space.from(current.sink()));
			}else {
				closure.setExiting(current.source());
			}
		}
	}
	
	public static SilentClosure decorate(SilentClosure closure) {
		for(SpaceState s : closure.decorableStates())
			closure.decorate(s, RegexBuilder.relevanceRegex(closure, new BuilderSpaceComportamentale(), s));
		return closure;
	}
	
	public static ClosureSpace buildSpace(SpaceAutomaComportamentale space) {
		ClosureSpace cs = new ClosureSpace(space.id());
		SilentClosure initial = build(space, space.initialState());
		decorate(initial);
		LinkedList<SilentClosure> queue = new LinkedList<SilentClosure>();
		queue.add(initial);
		composeClosureSpace(space, queue, cs);
		return cs;
	}
	
	private static void composeClosureSpace(
			SpaceAutomaComportamentale space,
			LinkedList<SilentClosure> queue , ClosureSpace cSpace) {
		if(queue.size() > 0) {
			SilentClosure closure = queue.pop();
			cSpace.insert(closure);
			for(SpaceState s : closure.exitStates()) {
				for(SpaceTransition<SpaceState> t : space.from(s)) {
					if(!t.isSilent()) {
						handleTransition(t, space, closure, queue, cSpace);
					}
				}
			}
			composeClosureSpace(space, queue, cSpace);
		}
	}
	
	private static void handleTransition(
			SpaceTransition<SpaceState> t, SpaceAutomaComportamentale space,
			SilentClosure closure, LinkedList<SilentClosure> queue , ClosureSpace cSpace) {
		
		SilentClosure sink = null;
		if(!cSpace.hasState(t.sink().id())) {
			sink = build(space, t.sink());
			decorate(sink);
			cSpace.insert(sink);
			queue.add(sink);
		}else {
			sink = cSpace.getState(t.sink().id());
		}
		ClosureTransition newT = new ClosureTransition(t.id(), closure, sink);
		newT.setObservableLabel(t.observableLabel());
		newT.setRelevantLabel(closure.getDecorationOf(t.source())+t.relevantLabelContent());
		cSpace.add(newT);
	}
}
