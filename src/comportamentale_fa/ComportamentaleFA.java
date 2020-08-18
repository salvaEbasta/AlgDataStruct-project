package comportamentale_fa;

import java.util.HashSet;
import java.util.Set;

import commoninterfaces.Automa;

public class ComportamentaleFA extends Automa<ComportamentaleState, ComportamentaleTransition>{

	public ComportamentaleFA(String id) {
		super(id);
	}

	@Override
	public Set<ComportamentaleState> acceptingStates() {
		return new HashSet<ComportamentaleState>();
	}

	
}
