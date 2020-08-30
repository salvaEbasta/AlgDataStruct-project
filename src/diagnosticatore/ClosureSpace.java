package diagnosticatore;

import java.util.Iterator;
import java.util.Set;

import fsm_interfaces.Automa;
import fsm_interfaces.Transition;

public class ClosureSpace extends Automa<SilentClosure, Transition<SilentClosure>>{
	private static final long serialVersionUID = 1L;

	public ClosureSpace(String id) {
		super(id);
	}
	
	public ClosureSpace(ClosureSpace cSpace) {
		super(cSpace);
	}

	public boolean hasState(String id) {
		Iterator<SilentClosure> iter = structure.keySet().iterator();
		while(iter.hasNext())
			if(iter.next().id().equals(id))
				return true;
		return false;
	}
	
	public SilentClosure getState(String id) {
		Iterator<SilentClosure> iter = structure.keySet().iterator();
		while(iter.hasNext()) {
			SilentClosure tmp = iter.next();
			if(tmp.id().equals(id))
				return tmp;
		}
		return null;
	}

}
