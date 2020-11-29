package ui.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.ObservationsList;
import diagnosticatore.ClosureSpace;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;

public class CurrentNet implements Serializable{
	private static final long serialVersionUID = 1L;
	private CFSMnetwork net;
	private SpaceAutomaComportamentale sac;
	private ClosureSpace diagnosticatore;
	private double scTime;
	private double scSpace;
	private double diagnosticatoreTime;
	private double diagnosticatoreSpace;
	private HashMap<ObservationsList, Result> results;
	
	public CurrentNet(CFSMnetwork net) {
		this.net = net;
		sac = null;
		diagnosticatore = null;
		results = new HashMap<ObservationsList, Result>();
		diagnosticatoreSpace = 0;
		diagnosticatoreTime = 0;
	}
	
	public void setSpaceAutomaComportamentale(SpaceAutomaComportamentale sac) {
		this.sac = sac;
	}
	
	public void addObservation(ObservationsList obs, SpaceAutomaObsLin saol) {
		results.put(obs, new Result());
		results.get(obs).setObsSpace(saol);
	}
	
	public boolean hasComportamentalSpace() {
		return sac != null;
	}	
	
	public SpaceAutomaComportamentale getComportamentalSpace() {
		return sac;
	}	
	
	public CFSMnetwork getNet() {
		return net;
	}
	
	public boolean hasLinObsCompSpace(ObservationsList labels) {
		if(results.containsKey(labels))
			return results.get(labels).hasObsSpace();
		else
			return false;
	}
	
	public SpaceAutomaObsLin getLinObsCompSpace(ObservationsList labels) {
		if(results.containsKey(labels))
			return results.get(labels).obsSpace();
		else 
			return null;
	}
	
	public double spazioComportamentaleTime() {
		return scTime;
	}
	
	public double spazioComportamentaleSpace() {
		return scSpace;
	}
	
	public HashMap<ObservationsList, SpaceAutomaObsLin> obsSpaces(){
		HashMap<ObservationsList, SpaceAutomaObsLin> map = new HashMap<ObservationsList, SpaceAutomaObsLin>();
		results.forEach((linObs, r)->map.put(linObs, r.obsSpace()));
		return map;
	}
	
	public Set<ObservationsList> linObss(){
		return results.keySet();
	}
	
	public int observationsNumber() {
		return results.size();
	}
	
	public String linObsCompSpacesDescription(){
		StringBuilder sb = new StringBuilder("Spazi generati per la rete:\n");
		int index = 0;
		for(Result spaceOL: results.values())
			sb.append(String.format("%d - %s\n", index++, spaceOL.obsSpace().id()));
		return sb.toString();
	}
	
	public void reset() {
		net = null;
		sac = null;
		diagnosticatore = null;
		
		results = new HashMap<ObservationsList, Result>();
	}
	
	public boolean hasObservableLabel(String label) {
		return net.getObservabelLabels().contains(new ObservableLabel(label));
	}

	public String obsLabel() {
		StringBuilder sb = new StringBuilder("Lista di etichette di Osservabilità nella rete:\n");
		for(ObservableLabel obs : net.getObservabelLabels())
			sb.append(String.format("* %s\n", obs.content()));
		return sb.toString();
	}	

	
	public String observationDescription() {
		StringBuilder sb = new StringBuilder("Osservazioni effettuate sulla rete:\n");
		results.forEach((o, r)->sb.append("* "+o.toString()+": "+r.toString()+"\n"));
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return net.toString();		
	}
	
	public boolean hasDiagnosticatore() {
		return diagnosticatore !=null;
	}
	
	public ClosureSpace getDiagnosticatore() {
		return diagnosticatore;
	}
	
	public void setDiagnosticatore(ClosureSpace decorated) {
		diagnosticatore = decorated;
	}
	
	public void addObsSpaceResult(ObservationsList obs, String diagnosis, double time, double space) {
		if(results.containsKey(obs)) {
			results.get(obs).setObsSpaceDiagnosis(diagnosis);
			results.get(obs).setObsSpaceTime(time);
			results.get(obs).setObsSpaceSpace(space);
		}
	}
	
	public void addDiagnosticatoreResult(ObservationsList obs, String diagnosis, double time, double space) {
		if(results.containsKey(obs)) {
			results.get(obs).setDiagnosticatoreDiagnosis(diagnosis);
			results.get(obs).setDiagnosticatoreTime(time);
			results.get(obs).setDiagnosticatoreSpace(space);
		}
	}
	
	public boolean addLinObs(ObservationsList obs) {
		if(!results.containsKey(obs)) {
			results.put(obs, new Result());
			return true;
		}
		return false;
	}
	
	public Set<ObservationsList> observations(){
		return results.keySet();
	}
	
	public boolean hasDiagnosticatorePerformance() {
		return diagnosticatoreSpace > 0 && diagnosticatoreTime > 0;
	}
	
	public void setSpazioComportamentalePerformance(double time, double space) {
		scSpace = space;
		scTime = time;
	}
	
	public void setDiagnosticatorePerformance(double time, double space) {
		diagnosticatoreSpace = space;
		diagnosticatoreTime = time;
	}
	
	public double diagnosticatoreTime() {return diagnosticatoreTime;}
	
	public double diagnosticatoreSpace() {return diagnosticatoreSpace;}
	
	public void setObsSpaceGenerationPerformance(ObservationsList obs, double time, double space) {
		if(results.containsKey(obs)) {
			results.get(obs).setObsSpaceGenerationSpace(space);
			results.get(obs).setObsSpaceGenerationTime(time);
		}
	}
	
	public double obsSpaceGenTime(ObservationsList obs) {
		if(results.containsKey(obs))
			return results.get(obs).obsSpaceGenTime();
		return -1;
	}
	
	public double obsSpaceGenSpace(ObservationsList obs) {
		if(results.containsKey(obs))
			return results.get(obs).obsSpaceGenSpace();
		return -1;
	}
}
