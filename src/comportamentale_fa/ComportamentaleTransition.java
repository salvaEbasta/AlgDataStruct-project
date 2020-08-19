package comportamentale_fa;

import java.util.HashMap;

import commoninterfaces.Transition;
import comportamentale_fa.labels.Label;
import comportamentale_fa.labels.ObservableLabel;
import comportamentale_fa.labels.RelevantLabel;

public class ComportamentaleTransition extends Transition<ComportamentaleState>{
	
	private Event in;
	private Link inputLink;
	private HashMap<Event, Link> out; //Ad ogni evento in uscita è associato un link diverso, andrebbe scelta un'altra struttura dati perchè con l'hashmap
									//è possibile inserire più volte lo stesso "valore" Link
	
	public ComportamentaleTransition(String id, ComportamentaleState source, ComportamentaleState destination, Event in, Link inputLink, HashMap<Event, Link> out, ObservableLabel omega, RelevantLabel f) {
		super(id, source, destination);
		super.setObservableLabel(omega);
		super.setRelevantLabel(f);
		this.in = in;
		this.inputLink = inputLink;
		this.out = out;
	}
	
	public ComportamentaleTransition(String id, ComportamentaleState source, ComportamentaleState destination, Event in, Link inputLink, ObservableLabel omega, RelevantLabel f) {
		super(id, source, destination);
		super.setObservableLabel(omega);
		super.setRelevantLabel(f);
		this.in = in;
		this.inputLink = inputLink;
		this.out = new HashMap<Event, Link>();
	}
	
	public ComportamentaleTransition(String id, ComportamentaleState source, ComportamentaleState destination, HashMap<Event, Link> out, ObservableLabel omega, RelevantLabel f) {
		super(id, source, destination);
		super.setObservableLabel(omega);
		super.setRelevantLabel(f);
		this.in = new Event();
		this.inputLink = null;
		this.out = out;
	}
	
	public Link getInputLink() {
		return inputLink;
	}
	
	public Event getInputEvent() {
		return in;
	}
	
	public boolean isInputEventEmpty() {
		return in.isEmpty();
	}
	
	public boolean isOutputEventsEmpty() {
		return out.isEmpty();
	}
	
	public HashMap<Event, Link> getOutputEvents() {
		return out;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(String.format("%s: ", id()));
		if(!isInputEventEmpty())
			sb.append(String.format("%s(%s)", in.id(), inputLink.id()));
		if(!isOutputEventsEmpty()) {
			Event[] keySet = new Event[out.size()];
			keySet = out.keySet().toArray(keySet);
			sb.append(String.format("/{%s(%s)", keySet[0].id(), out.get(keySet[0]).id()));
			for(int i=1; i<keySet.length; i++) {
				sb.append(String.format(",%s(%s)", keySet[i], out.get(keySet[i]).id()));
			}
			sb.append("}");			
		}
		if(hasLabel())
			sb.append(labels());
		return sb.toString();
	}

}
