package diagnosticatore;

import commoninterfaces.Transition;

public class ClosureTransition extends Transition<SilentClosure>{
	private static final long serialVersionUID = 1L;

	public ClosureTransition(String id, SilentClosure source, SilentClosure sink) {
		super(id, source, sink);
	}

}
