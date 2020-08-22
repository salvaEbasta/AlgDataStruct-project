package diagnosticatore;

import commoninterfaces.Automa;
import commoninterfaces.Transition;
import spazio_comportamentale.SpaceAutoma;

public class ClosureSpace extends Automa<SilentClosure, Transition<SilentClosure>>{

	public ClosureSpace(String id) {
		super(id);
	}

}
