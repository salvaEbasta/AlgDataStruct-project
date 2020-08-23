package diagnosticatore;

import java.util.Iterator;
import java.util.Set;

import commoninterfaces.Automa;
import commoninterfaces.Transition;

public class ClosureSpace extends Automa<SilentClosure, Transition<SilentClosure>>{
	private static final long serialVersionUID = 1L;

	public ClosureSpace(String id) {
		super(id);
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("ClosureSpace: %s\n", id()));
		sb.append(String.format("[Numero Stati: %d - Numero Transizioni: %d]\n", states().size(), transitions().size()));
		for(SilentClosure state: states()) {
			sb.append(state.id());
			Set<Transition<SilentClosure>>  in = to(state);
			Set<Transition<SilentClosure>> out = from(state);
			if(!in.isEmpty()) {
				sb.append("\n\t- Input Transitions:");
				for(Transition<SilentClosure> inTransition: in) {
					sb.append(String.format("\n\t\t* %s", inTransition));
				}
			}
			if(!out.isEmpty()) {
				sb.append("\n\t- Output Transitions:");
				for(Transition<SilentClosure> outTransition: out) {
					sb.append(String.format("\n\t\t* %s", outTransition));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
