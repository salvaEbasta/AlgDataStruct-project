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
	
	public SpaceAutomaComportamentale generateSpace() {
		if(sac == null)
			sac = new SpazioComportamentale(net).generaSpazioComportamentale();
		return sac;
	}
	
	public SpaceAutomaObsLin generateSpaceObs(ObservableLabel[] labels) {
		SpaceAutomaObsLin saol = null;
		if(listaOsservazioni.containsKey(labels))
			saol = listaOsservazioni.get(labels);
		else {
			saol = new SpazioComportamentaleObs(net).generaSpazioOsservazione(labels);
			listaOsservazioni.put(labels, saol);
		}
		return saol;
	}
	
	public void reset() {
		net = null;
		sac = null;
		listaOsservazioni = new HashMap<ObservableLabel[], SpaceAutomaObsLin>();
	}

	public String observationDescription() {
		StringBuilder sb = new StringBuilder("Osservazioni effettuate sulla rete:\n");
		for(Entry<ObservableLabel[], SpaceAutomaObsLin> entry: listaOsservazioni.entrySet()) {
			sb.append("SPAZIO COMPORTAMENTALE GENERATO per osservazione ");
			sb.append(entry.getKey());
			sb.append(":\n*****************************************************");
			sb.append(entry.getValue()).append("\n\n");
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return net.toString();		
	}
	

}
