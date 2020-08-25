package diagnosticatore;

import fsm_interfaces.ComponentBuilder;
import fsm_interfaces.Transition;

public class ClosureComponentBuilder implements ComponentBuilder<SilentClosure, Transition<SilentClosure>>{

	@Override
	public SilentClosure newState(String id) {
		return new SilentClosure(id);
	}

	@Override
	public Transition<SilentClosure> newTransition(String id, SilentClosure source, SilentClosure destination) {
		return new Transition<SilentClosure>(id, source, destination);
	}

}
