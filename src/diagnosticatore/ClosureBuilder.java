package diagnosticatore;

import java.util.LinkedList;

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
		closure.decorableStates().forEach(s->{
			String relevantRegex = RegexBuilder.regexForEachAccepting(N, builder);
			closure.decore(s, relevantRegex);
		});
		return closure;
	}
	
	@Deprecated
	public static ClosureSpace buildSpace() {
		return null;
	}
}
