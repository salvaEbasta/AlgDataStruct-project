package finite_state_automata;

import commoninterfaces.State;

public class FiniteState extends State{

	private boolean diAccettazione;
	
	public FiniteState(String id) {
		super(id);
		this.diAccettazione = false;
	}
	
	public FiniteState(String id, boolean diAccettazione) {
		super(id);
		this.diAccettazione = diAccettazione;
	}
	
	public boolean isAccepting() {
		return this.diAccettazione;
	}
	
	public void setAccepting(boolean accettazione) {
		this.diAccettazione = accettazione;
	}	
	
	@Override
	public String toString() {
		return id()+((diAccettazione)?"||":"");
	}
}
