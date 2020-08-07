package finite_state_automata;

public class State {
	private String id;
	private boolean diAccettazione;
	
	public State(String id) {
		this.id = id;
		this.diAccettazione = false;
	}
	
	public String id() {return id;}
	
	public boolean isAccepting() {
		return this.diAccettazione;
	}
	
	public void setAccepting(boolean accettazione) {
		this.diAccettazione = accettazione;
	}
	
	public String toString() {
		return id+((diAccettazione)?"||":"");
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		final State tmp = (State) obj;
		return this.id.equalsIgnoreCase(tmp.id);
	}
}
