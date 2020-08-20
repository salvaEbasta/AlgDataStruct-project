package comportamentale_fa;

import java.io.Serializable;

import commoninterfaces.State;

public class ComportamentaleState extends State implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComportamentaleState(String id) {
		super(id);
	}

	@Override
	public String toString() {
		return id();
	}
}
