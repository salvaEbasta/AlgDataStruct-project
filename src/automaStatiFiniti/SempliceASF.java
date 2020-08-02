package automaStatiFiniti;

import java.util.HashMap;
import java.util.HashSet;

public class SempliceASF implements AutomaStatiFiniti{
	private String id;
	private Stato iniziale;
	private HashMap<Stato, HashSet<Transizione>> struttura;
	private Stato attuale;
	
	public SempliceASF(String id) {
		this.id = id;
		this.iniziale = null;
		this.struttura = new HashMap<Stato, HashSet<Transizione>>();
		this.attuale = null;
	}
	
	public String id() {return id;}
	
	public void setIniziale(Stato s) {
		if(struttura.containsKey(s))
			this.iniziale = s;
	}
	
	public void inserisci(Stato s) {
		if(!struttura.containsKey(s))
			this.struttura.put(s, new HashSet<Transizione>());
	}
	
	public boolean connetti(Transizione t) {
		if(struttura.containsKey(t.getDa()) && struttura.containsKey(t.getDa()))
			return struttura.get(t.getDa()).add(t);
		return false;
	}
	
	public Stato statoAttuale() {
		return this.attuale;
	}
	
	public boolean start() {
		this.attuale = this.iniziale;
		return true;
	}
	
	public String esegui(Transizione t) {
		String regex = "";
		if(attuale!=null && struttura.get(attuale).contains(t)) {
			regex = t.getRegex();
			attuale = t.getA();
		}
		return regex;
	}
	
	public HashSet<Transizione> transizioniAbilitate(){
		return struttura.get(attuale);
	}
}
