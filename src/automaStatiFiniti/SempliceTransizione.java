package automaStatiFiniti;

import java.util.Objects;

public class SempliceTransizione implements Transizione{
	private static final String toStringFormat = "%s: %s--%s->%s";
	private String id;
	private Stato sorgente;
	private Stato destinazione;
	private String regex;
	
	public SempliceTransizione(String id, Stato sorg, Stato dest, String regex) {
		this.id = id;
		this.sorgente = sorg;
		this.destinazione = dest;
		this.regex = regex;
	}
	
	public String id() {return id;}
	public Stato getSorgente() {return sorgente;}
	public Stato getDestinazione() {return destinazione;}
	public String getRegex() {return regex;}
	
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	public boolean equals(Object obj) {
		if(obj==null || !SempliceTransizione.class.isAssignableFrom(obj.getClass()))
			return false;
		final SempliceTransizione tmp = (SempliceTransizione) obj;
		return this.id.equalsIgnoreCase(tmp.id);
	}
	
	public String toString() {
		return String.format(toStringFormat, id, sorgente, regex, destinazione);
	}
}
