package commoninterfaces;

import comportamentale_fa.labels.ObservableLabel;
import comportamentale_fa.labels.RelevantLabel;

public abstract class Transition<S extends State> {
	
	private String id;
	private S source;
	private S destination;
	private RelevantLabel rel;
	private ObservableLabel obs;
	
	public Transition(String id, S source, S destination) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.rel = new RelevantLabel();
		this.obs = new ObservableLabel();
	}
	
	public String id() {
		return id;
	}
	
	public S source() {
		return source;
	}	
	
	public String relevantLabelContent() {
		return rel.getLabel();
	}
	
	public RelevantLabel relevantLabel() {
		return rel;
	}
	
	public String observableLabelContent() {
		return obs.getLabel();
	}
	
	public ObservableLabel observableLabel() {
		return obs;
	}
	
	public boolean setObservableLabel(String newLabel) {
		obs.setLabel(newLabel);
		return true;
	}
	
	public boolean setObservableLabel(ObservableLabel obs) {
		this.obs = obs;
		return true;
	}
	
	public boolean isAuto() {
		return source.equals(destination);
	}
	
	public boolean isParallelTo(Transition<S> t) {
		return this.source.equals(t.source) && this.destination.equals(t.destination);
	}
	
	public boolean setRelevantLabel(String newLabel) {
		this.rel.setLabel(newLabel);
		return true;
	}
	
	public boolean setRelevantLabel(RelevantLabel rel) {
		this.rel = rel;
		return true;
	}
	
	public boolean hasObservableLabel() {
		return !obs.isEmpty();
	}
	
	public boolean hasRelevantLabel() {
		return !rel.isEmpty();
	}
	
	public boolean hasLabel() {
		return !obs.isEmpty() || !rel.isEmpty();
	}
	
	public String labels() {
		if(hasLabel())
			return String.format("[%s]", hasObservableLabel()? obs : rel);
		return "";
	}	
	
	public S sink() {
		return destination;
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		final Transition<S> tmp = (Transition<S>) obj;
		return this.id().equalsIgnoreCase(tmp.id());
	}
	

}
