package comportamentale_fa;

import commoninterfaces.State;

public class ComportamentaleState extends State{
		
	public ComportamentaleState(String id) {
		super(id);
	}

	@Override
	public String toString() {
		return id();
	}
}
