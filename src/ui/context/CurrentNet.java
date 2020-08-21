package ui.context;

import java.io.Serializable;
import java.util.ArrayList;

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
	private ArrayList<SpaceAutomaObsLin> listSaol;
	private ArrayList<ObservableLabel[]> listaOsservazioni;
	
	public CurrentNet(CFSMnetwork net) {
		this.net = net;
		listaOsservazioni = new ArrayList<ObservableLabel[]>();
	}
	
	public SpaceAutomaComportamentale generateSpace() {
		if(sac == null)
			sac = new SpazioComportamentale(net).generaSpazioComportamentale();
		return sac;
	}
	
	public SpaceAutomaObsLin generateSpaceObs(ObservableLabel[] labels) {
		SpaceAutomaObsLin saol = null;
		if(listaOsservazioni.contains(labels)) {
			//get spazio c o l corrispondente a labels
		} else {
			saol = new SpazioComportamentaleObs(net).generaSpazioOsservazione(labels);
			listSaol.add(saol);
		}
		return saol;
	}
	
	public void reset() {
		net = null;
		sac = null;
		listaOsservazioni = new ArrayList<ObservableLabel[]>();
	}

	@Override
	public String toString() {
		return net.toString();		
	}
	

}
