package fsa_algorithms;

import java.util.LinkedList;

import commoninterfaces.AutomaInterface;
import commoninterfaces.State;
import commoninterfaces.Transition;


public class TransitionFinder<S extends State, T extends Transition<S>> {
	
	/**
	 * Trova una sequenza di transizioni di dimensione >=2 dove ogni stato intermedio 
	 * non ha altre transizioni entranti e uscenti al di fuori di quelle che compongono la sequenza
	 * @param N
	 * @return
	 */
	public LinkedList<T> oneWayPath(AutomaInterface<S, T> N){
		LinkedList<T> sequence = new LinkedList<T>();
		for(S s : N.states()) {
			//System.out.println("State : "+s);
			buildSequence(N, s, sequence);
			//System.out.println("Result :"+sequence);
			if(sequence.size() >= 2)
				break;
			else
				sequence.clear();
		}
		return sequence;
	}
	
	private void buildSequence(AutomaInterface<S, T> N, S s, LinkedList<T> sequence) {
		buildUpstream(N, s, sequence);
		buildDownstream(N, s, sequence);
	}
	
	private void buildUpstream(AutomaInterface<S, T> N, S source, LinkedList<T> sequence) {
		//System.out.println("Upstream of "+source+": "+N.to(source));
		if(N.to(source).size() == 1 && N.from(source).size() == 1) {
			T inT = N.to(source).iterator().next();
			if(!inT.isAuto()) {
				sequence.addFirst(inT);
				buildUpstream(N, inT.source(), sequence);
			}
		}
	}
	
	private void buildDownstream(AutomaInterface<S, T> N, S sink, LinkedList<T> sequence) {
		//System.out.println("Downstream of "+sink+": "+N.from(sink));
		if(N.from(sink).size() == 1 && N.to(sink).size() == 1) {
			T outT = N.from(sink).iterator().next();
			if(!outT.isAuto()) {
				sequence.addLast(outT);
				buildDownstream(N, outT.sink(), sequence);
			}
		}
	}
	
	public LinkedList<T> parallelTransitions(AutomaInterface<S, T> N){
		LinkedList<T> parallels = new LinkedList<T>();
		LinkedList<T> transitions = new LinkedList<T>(N.transitions());
		while(transitions.size() > 1) {
			T tmp = transitions.pop();
			transitions.forEach(t->{
				if(t.isParallelTo(tmp))
					parallels.add(t);
			});
			if(parallels.size() > 0) {
				parallels.add(tmp);
				break;
			}else
				parallels.clear();
		}
		transitions.clear();
		return parallels;
	}
}
