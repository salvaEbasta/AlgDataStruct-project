package fsa_algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import finite_state_automata.FiniteStateMachine;
import finite_state_automata.FiniteState;
import finite_state_automata.FiniteTransition;

public class TransitionFinder {
	
	/**
	 * Trova una sequenza di transizioni di dimensione >=2 dove ogni stato intermedio 
	 * non ha altre transizioni entranti e uscenti al di fuori di quelle che compongono la sequenza
	 * @param N
	 * @return
	 */
	public static List<FiniteTransition> oneWayPath(FiniteStateMachine N){
		LinkedList<FiniteTransition> sequence = new LinkedList<FiniteTransition>();
		for(FiniteState s : N.states()) {
			buildSequence(N,s,sequence);
			if(sequence.size() > 1)
				break;
			else
				sequence = new LinkedList<FiniteTransition>();
		}
		return sequence;
	}
	
	private static void buildSequence(FiniteStateMachine N, FiniteState s, LinkedList<FiniteTransition> sequence) {
		if(N.to(s).size() == 1 && N.from(s).size() == 1) {
			FiniteTransition in = N.to(s).iterator().next();
			if(!in.isAuto()) {
				sequence.addFirst(in);
				buildUpstream(N, in.source(), sequence);
			}
			FiniteTransition out = N.from(s).iterator().next();
			if(!out.isAuto()) {
				sequence.addLast(out);
				buildDownstream(N, out.sink(), sequence);
			}
		}
		else if(N.from(s).size() == 1) {
			FiniteTransition out = N.from(s).iterator().next();
			if(!out.isAuto()) {
				sequence.addLast(out);
				buildDownstream(N, out.sink(), sequence);
			}
		}
		else if(N.to(s).size() == 1) {
			FiniteTransition in = N.to(s).iterator().next();
			if(!in.isAuto()) {
				sequence.addFirst(in);
				buildUpstream(N, in.source(), sequence);
			}
		}
	}
	
	private static void buildUpstream(FiniteStateMachine N, FiniteState source, LinkedList<FiniteTransition> sequence) {
		if(N.to(source).size() == 1) {
			FiniteTransition in = N.to(source).iterator().next();
			if(!in.isAuto()) {
				sequence.addFirst(in);
				buildUpstream(N, in.source(), sequence);
			}
		}
	}
	
	private static void buildDownstream(FiniteStateMachine N, FiniteState sink, LinkedList<FiniteTransition> sequence) {
		if(N.from(sink).size() == 1) {
			FiniteTransition out = N.from(sink).iterator().next();
			if(!out.isAuto()) {
				sequence.addLast(out);
				buildDownstream(N, out.sink(), sequence);
			}
		}
	}
	
	public static LinkedList<FiniteTransition> parallelTransitions(FiniteStateMachine N){
		LinkedList<FiniteTransition> parallels = new LinkedList<FiniteTransition>();
		for(FiniteState s : N.states()) {
			if(findParallels(N.to(s), parallels).size() > 1)
				break;
			else if(findParallels(N.from(s), parallels).size() > 1)
				break;
		}
		return parallels;
	}
	
	private static LinkedList<FiniteTransition> findParallels(Set<FiniteTransition> transitions, LinkedList<FiniteTransition> parallels){
		parallels.clear();
		LinkedList<FiniteTransition> queue = new LinkedList<FiniteTransition>(transitions);
		while(queue.size() > 1) {
			FiniteTransition tmp = queue.pop();
			queue.forEach(t->{
				if(t.isParallelTo(tmp))
					parallels.add(t);
			});
			if(parallels.size() > 0) {
				parallels.add(tmp);
				break;
			}else
				parallels.clear();
		}
		return parallels;
	}
}
