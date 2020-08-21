package ui.context;

import comportamental_fsm.ComportamentalFSM;
import comportamental_fsm.ComportamentalState;
import comportamental_fsm.ComportamentalTransition;

public class CurrentCFA {
	
	private ComportamentalFSM newCFA;
	
	public CurrentCFA(String id) {
		newCFA = new ComportamentalFSM(id);
	}
	
	public boolean setInitialStateOnNewCFA(ComportamentalState s) {
		return newCFA.setInitial(s);		
	}	
	
	protected ComportamentalFSM getCFA() {
		return newCFA;
	}
	
	public boolean isCorrectCFA() {
		return !newCFA.states().isEmpty() && !newCFA.transitions().isEmpty() && newCFA.initialState() != null;
	}

	public boolean addStateToNewCFA(ComportamentalState s) {
		return newCFA.insert(s);
	}

	public boolean addTransitionToNewCFA(ComportamentalTransition t) {
		return newCFA.add(t);
	}


}
