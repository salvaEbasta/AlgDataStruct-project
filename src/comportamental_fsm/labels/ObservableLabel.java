package comportamental_fsm.labels;

import java.io.Serializable;

public class ObservableLabel extends Label implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObservableLabel(String label) {
		super("Ω", label);
	}
	
	public ObservableLabel() {
		super("Ω");
	}

}
