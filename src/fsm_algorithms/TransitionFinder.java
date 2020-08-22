package fsm_algorithms;

import java.util.LinkedList;

import commoninterfaces.FiniteStateMachine;
import commoninterfaces.StateInterface;
import commoninterfaces.Transition;


public class TransitionFinder {
	
	/**
	 * Trova una sequenza di transizioni di dimensione >=2 dove ogni stato intermedio 
	 * non ha altre transizioni entranti e uscenti al di fuori di quelle che compongono la sequenza
	 * @param N
	 * @return
	 */
	public static <S extends StateInterface, T extends Transition<S>> LinkedList<T> oneWayPath(FiniteStateMachine<S, T> N){
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
	
	private static <S extends StateInterface, T extends Transition<S>> void buildSequence(FiniteStateMachine<S, T> N, S s, LinkedList<T> sequence) {
		buildUpstream(N, s, sequence);
		buildDownstream(N, s, sequence);
	}
	
	private static <S extends StateInterface, T extends Transition<S>> void buildUpstream(FiniteStateMachine<S, T> N, S source, LinkedList<T> sequence) {
		//System.out.println("Upstream of "+source+": "+N.to(source));
		if(N.to(source).size() == 1 && N.from(source).size() == 1) {
			T inT = N.to(source).iterator().next();
			if(!inT.isAuto()) {
				sequence.addFirst(inT);
				buildUpstream(N, inT.source(), sequence);
			}
		}
	}
	
	private static <S extends StateInterface, T extends Transition<S>> void buildDownstream(FiniteStateMachine<S, T> N, S sink, LinkedList<T> sequence) {
		//System.out.println("Downstream of "+sink+": "+N.from(sink));
		if(N.from(sink).size() == 1 && N.to(sink).size() == 1) {
			T outT = N.from(sink).iterator().next();
			if(!outT.isAuto()) {
				sequence.addLast(outT);
				buildDownstream(N, outT.sink(), sequence);
			}
		}
	}
	
	public static <S extends StateInterface, T extends Transition<S>> LinkedList<T> parallelTransitions(FiniteStateMachine<S, T> N){
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
