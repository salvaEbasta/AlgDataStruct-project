package automaStatiFiniti;

public class Stato {
	private String id;
	private boolean diAccettazione;
	
	public Stato(String id) {
		this.id = id;
		this.diAccettazione = false;
	}
	
	public boolean accettazione() {
		return this.diAccettazione;
	}
	
	public void setAccettazione(boolean accettazione) {
		this.diAccettazione = accettazione;
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		final Stato tmp = (Stato) obj;
		return this.id.equalsIgnoreCase(tmp.id);
	}
}
