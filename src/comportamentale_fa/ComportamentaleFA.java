package comportamentale_fa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import commoninterfaces.Automa;

public class ComportamentaleFA extends Automa<ComportamentaleState, ComportamentaleTransition> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComportamentaleFA(String id) {
		super(id);
	}

	@Override
	public Set<ComportamentaleState> acceptingStates() {
		return new HashSet<ComportamentaleState>();
	}

	
}
