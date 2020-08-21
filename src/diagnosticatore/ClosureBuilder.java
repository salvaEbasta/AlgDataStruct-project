package diagnosticatore;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import fsm_algorithms.RegexBuilder;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpaceTransition;

public class ClosureBuilder {
	public static SilentClosure build(SpaceAutomaComportamentale space, SpaceState state){
		SilentClosure silentClosure = new SilentClosure(state.id()+"_silentClosure");
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
				closure.setDecorable(current.source());
			}
		}
	}
	
	public static SilentClosure decorate(SilentClosure closure) {
		Map<SpaceState, String> decorations = null;
		decorations.entrySet().forEach(e->closure.decorate(e.getKey(), e.getValue()));
		return closure;
	}
	
	@Deprecated
	public static ClosureSpace buildSpace(SpaceAutomaComportamentale space) {
		ClosureSpace cSpace = new ClosureSpace();
		Iterator<SpaceTransition<SpaceState>> iter = space.transitions().iterator();
		while(iter.hasNext()) {
			SpaceTransition<SpaceState> origin = iter.next();
			if(origin.isSilent())
				continue;
			else if(cSpace.contains(origin.sink().id()))
				continue;
			else {
				SilentClosure sink = build(space, origin.sink());
				decorate(sink);
				cSpace.add(sink);
				SilentClosure source = null;
				if(!cSpace.contains(origin.source().id())) {
					source = build(space, origin.sink());
					decorate(source);
					cSpace.add(source);
				}else {
					source = cSpace.getStateWith(origin.source().id());
				}
				ClosureTransition t = new ClosureTransition(origin.id(), source, sink);
				t.setRelevantLabel(origin.relevantLabelContent() + source.getDecorationOf(origin.source()));
				cSpace.insert(t);
			}
		}
		return null;
	}
}
