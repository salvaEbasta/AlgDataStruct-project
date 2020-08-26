package ui.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.ObservationsList;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;

public class CurrentNet implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CFSMnetwork net;
	private SpaceAutomaComportamentale sac;
	private HashMap<ObservationsList, SpaceAutomaObsLin> listaOsservazioni;
	
	public CurrentNet(CFSMnetwork net) {
		this.net = net;
		listaOsservazioni = new HashMap<ObservationsList, SpaceAutomaObsLin>();
	}
	
	public void setSpaceAutomaComportamentale(SpaceAutomaComportamentale sac) {
		this.sac = sac;
	}
	
	public void addObservation(ObservationsList obs, SpaceAutomaObsLin saol) {
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
	
	public boolean hasGeneratedSpaceObs(ObservationsList labels) {
		return listaOsservazioni.containsKey(labels);
	}
	
	public SpaceAutomaObsLin getGeneratedSpaceObs(ObservationsList labels) {
		if(listaOsservazioni.containsKey(labels))
			return listaOsservazioni.get(labels);
		else 
			return null;
	}
	
	public Entry<ObservationsList, SpaceAutomaObsLin> getSpaceObsByIndex(int index){
		if(index > listaOsservazioni.size())
			return null;
		int i=0;
		for(Entry<ObservationsList, SpaceAutomaObsLin> entry: listaOsservazioni.entrySet()) {
			if(i == index)
				return entry;
		}
		return null;
	}
	
	public int computedObsertvationsLenght() {
		return listaOsservazioni.size();
	}
	
	public String generatedObsSpacesDescription(){
		StringBuilder sb = new StringBuilder("Spazi generati per la rete:\n");
		int index = 0;
		for(SpaceAutomaObsLin spaceOL: listaOsservazioni.values())
			sb.append(String.format("%d - %s\n", index++, spaceOL.id()));
		return sb.toString();
	}
	
	public void reset() {
		net = null;
		sac = null;
		listaOsservazioni = new HashMap<ObservationsList, SpaceAutomaObsLin>();
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
		for(Entry<ObservationsList, SpaceAutomaObsLin> entry: listaOsservazioni.entrySet())
			sb.append(entry.getValue()).append("\n\n");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return net.toString();		
	}
	

}
