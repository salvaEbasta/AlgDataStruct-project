package comportamental_fsm;

import java.util.HashSet;
import java.util.Set;

import commoninterfaces.Automa;

public class ComportamentalFSM extends Automa<ComportamentalState, ComportamentalTransition>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComportamentalFSM(String id) {
		super(id);
	}
	
	public ComportamentalFSM(ComportamentalFSM cfa) {
		super(cfa);
	}
	
	@Override
	public Set<ComportamentalState> acceptingStates() {
		return new HashSet<ComportamentalState>();
	}
	
}
