package comportamental_fsm;

import java.io.Serializable;
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
	
	@Override
	public Set<ComportamentalState> acceptingStates() {
		return new HashSet<ComportamentalState>();
	}
	
}
