package commoninterfaces;

import java.io.Serializable;

import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.RelevantLabel;

public class Transition<S extends StateInterface> implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private S source;
	private S sink;
	private RelevantLabel rel;
	private ObservableLabel obs;
	
	public Transition(String id, S source, S sink) {
		this.id = id;
		this.source = source;
		this.sink = sink;
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
		return source.equals(sink);
	}
	
	public boolean isParallelTo(Transition<S> t) {
		return this.source.equals(t.source) && this.sink.equals(t.sink);
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
	
	public boolean isSilent() {
		return obs.isEmpty();
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
		return sink;
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		final Transition<State> tmp = (Transition<State>) obj;
		return id.equalsIgnoreCase(tmp.id()) 
				&& source.equals(tmp.source)
				&& sink.equals(tmp.sink);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id.concat(":["));
		sb.append(source.id()+"->"+sink.id());
		sb.append(", "+obs.toString()+", "+rel.toString()+"]");
		return sb.toString();
	}
}
