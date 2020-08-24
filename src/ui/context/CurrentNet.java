package ui.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.labels.ObservableLabel;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpazioComportamentale;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpazioComportamentaleObs;

public class CurrentNet implements Serializable{
	
	//RENDERE TUTTO SERIALIZABLE -> VA SALVATA RETE CON RISULTATI
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CFSMnetwork net;
	private SpaceAutomaComportamentale sac;
	private HashMap<ObservableLabel[], SpaceAutomaObsLin> listaOsservazioni;
	
	public CurrentNet(CFSMnetwork net) {
		this.net = net;
		listaOsservazioni = new HashMap<ObservableLabel[], SpaceAutomaObsLin>();
	}
	
	public void setSpaceAutomaComportamentale(SpaceAutomaComportamentale sac) {
		this.sac = sac;
	}
	
	public void addObservation(ObservableLabel[] obs, SpaceAutomaObsLin saol) {
		listaOsservazioni.put(obs, saol);
	}
	
	public boolean hasGeneratedSpace() {
		return sac != null;
	}	
	
	public SpaceAutomaComportamentale getGeneratedSpace() {
		return sac;
	}	
	
	public CFSMnetwork getNet() {
		return net;
	}
	
	public boolean hasGeneratedSpaceObs(ObservableLabel[] labels) {
		return listaOsservazioni.containsKey(labels);
	}
	
	public SpaceAutomaObsLin getGeneratedSpaceObs(ObservableLabel[] labels) {
		if(listaOsservazioni.containsKey(labels))
			return listaOsservazioni.get(labels);
		else 
			return null;
	}
	
	public String generatedSpacesDescription(){
		StringBuilder sb = new StringBuilder("Spazi generati per la rete:\n");
		if(sac != null)
			sb.append(String.format("0 - %s\n", sac.id()));
		int index = 1;
		for(SpaceAutomaObsLin spaceOL: listaOsservazioni.values())
			sb.append(String.format("%d - %s\n", index++, spaceOL.id()));
		return sb.toString();
	}
	
	public void reset() {
		net = null;
		sac = null;
		listaOsservazioni = new HashMap<ObservableLabel[], SpaceAutomaObsLin>();
	}
	
	public boolean hasObservableLabel(String label) {
		return net.getObservabelLabels().contains(new ObservableLabel(label));
	}

	public String obsLabel() {
		StringBuilder sb = new StringBuilder("Lista di etichette di Osservabilità nella rete:\n");
		for(ObservableLabel obs : net.getObservabelLabels())
			sb.append(String.format("* %s\n", obs.getLabel()));
		return sb.toString();
	}
	
	public String observationDescription() {
		StringBuilder sb = new StringBuilder("Osservazioni effettuate sulla rete:\n\n");
		for(Entry<ObservableLabel[], SpaceAutomaObsLin> entry: listaOsservazioni.entrySet())
			sb.append(entry.getValue()).append("\n\n");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return net.toString();		
	}
	

}
