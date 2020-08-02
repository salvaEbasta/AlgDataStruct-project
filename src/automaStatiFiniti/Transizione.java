package automaStatiFiniti;

import java.util.Objects;

public class Transizione{
	private static final String toStringFormat = "%s: %s--%s->%s";
	private String id;
	private Stato sorgente;
	private Stato destinazione;
	private String regex;
	
	public Transizione(String id, Stato sorg, Stato dest, String regex) {
		this.id = id;
		this.sorgente = sorg;
		this.destinazione = dest;
		this.regex = regex;
	}
	
	public String id() {return id;}
	public Stato getDa() {return sorgente;}
	public Stato getA() {return destinazione;}
	public String getRegex() {return regex;}
	public boolean setRegex(String newRegex) {
		this.regex = newRegex;
		return true;
	}
	
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	public boolean equals(Object obj) {
		if(obj==null || !Transizione.class.isAssignableFrom(obj.getClass()))
			return false;
		final Transizione tmp = (Transizione) obj;
		return this.id.equalsIgnoreCase(tmp.id);
	}
	
	public String toString() {
		return String.format(toStringFormat, id, sorgente, regex, destinazione);
	}
}
