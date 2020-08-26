package diagnosticatore.algorithms;

import algorithm_interfaces.Algorithm;
import diagnosticatore.SilentClosure;
import fsm_algorithms.RelevanceRegexBuilder;
import spazio_comportamentale.SpaceTransition;
import spazio_comportamentale.oss_lineare.BuilderSpaceComportamentaleObsLin;
import spazio_comportamentale.oss_lineare.SpaceStateObs;

public class ClosureDecorator extends Algorithm<SilentClosure>{
	private SilentClosure closure;
	
	public ClosureDecorator(SilentClosure closure) {
		this.closure = closure;
	}

	@Override
	public SilentClosure call() throws Exception {
		log.info(this.getClass().getSimpleName()+"::decorate("+closure.id()+")...");
		
		for(SpaceStateObs s : closure.decorableStates()) {
			String decoration = new RelevanceRegexBuilder<SpaceStateObs, SpaceTransition<SpaceStateObs>>(
										closure, 
										new BuilderSpaceComportamentaleObsLin(), 
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
