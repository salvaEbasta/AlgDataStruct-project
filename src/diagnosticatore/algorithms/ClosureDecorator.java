package diagnosticatore.algorithms;

import algorithm_interfaces.Algorithm;
import diagnosticatore.SilentClosure;
import fsm_algorithms.RelevanceRegexBuilder;
import spazio_comportamentale.BuilderSpaceComportamentale;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpaceTransition;

public class ClosureDecorator extends Algorithm<SilentClosure>{
	private SilentClosure closure;
	
	public ClosureDecorator(SilentClosure closure) {
		this.closure = closure;
	}

	@Override
	public SilentClosure call() throws Exception {
		log.info(this.getClass().getSimpleName()+"::decorate("+closure.id()+")...");
		
		for(SpaceState s : closure.decorableStates()) {
			String decoration = new RelevanceRegexBuilder<SpaceState, SpaceTransition<SpaceState>>(
										(SilentClosure)closure.clone(), 
										new BuilderSpaceComportamentale(), 
										s).call();
			closure.decorate(s, decoration);
		}
		return closure;
	}

	@Override
	public SilentClosure midResult() {
		return closure;
	}
}
