package comportamental_fsm;

import commoninterfaces.State;

public class ComportamentalState extends State{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComportamentalState(String id) {
		super(id);
	}

	@Override
	public String toString() {
		return id();
	}
	
	@Override
	public boolean isAccepting() {
		return false;
	}
}
