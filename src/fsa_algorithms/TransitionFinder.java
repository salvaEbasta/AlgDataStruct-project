package fsa_algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import finite_state_automata.FiniteStateMachine;
import finite_state_automata.State;
import finite_state_automata.Transition;

public class TransitionFinder {
	
	/**
	 * Trova una sequenza di transizioni di dimensione >=2 dove ogni stato intermedio 
	 * non ha altre transizioni entranti e uscenti al di fuori di quelle che compongono la sequenza
	 * @param N
	 * @return
	 */
	public static List<Transition> oneWayPath(FiniteStateMachine N){
		LinkedList<Transition> sequence = new LinkedList<Transition>();
		for(State s : N.states()) {
			buildSequence(N,s,sequence);
			if(sequence.size() > 1)
				break;
			else
				sequence = new LinkedList<Transition>();
		}
		return sequence;
	}
	
	private static void buildSequence(FiniteStateMachine N, State s, LinkedList<Transition> sequence) {
		if(N.to(s).size() == 1 && N.from(s).size() == 1) {
			Transition in = N.to(s).iterator().next();
			if(!in.isAuto()) {
				sequence.addFirst(in);
				buildUpstream(N, in.source(), sequence);
			}
			Transition out = N.from(s).iterator().next();
			if(!out.isAuto()) {
				sequence.addLast(out);
				buildDownstream(N, out.sink(), sequence);
			}
		}
		else if(N.from(s).size() == 1) {
			Transition out = N.from(s).iterator().next();
			if(!out.isAuto()) {
				sequence.addLast(out);
				buildDownstream(N, out.sink(), sequence);
			}
		}
		else if(N.to(s).size() == 1) {
			Transition in = N.to(s).iterator().next();
			if(!in.isAuto()) {
				sequence.addFirst(in);
				buildUpstream(N, in.source(), sequence);
			}
		}
	}
	
	private static void buildUpstream(FiniteStateMachine N, State source, LinkedList<Transition> sequence) {
		if(N.to(source).size() == 1) {
			Transition in = N.to(source).iterator().next();
			if(!in.isAuto()) {
				sequence.addFirst(in);
				buildUpstream(N, in.source(), sequence);
			}
		}
	}
	
	private static void buildDownstream(FiniteStateMachine N, State sink, LinkedList<Transition> sequence) {
		if(N.from(sink).size() == 1) {
			Transition out = N.from(sink).iterator().next();
			if(!out.isAuto()) {
				sequence.addLast(out);
				buildDownstream(N, out.sink(), sequence);
			}
		}
	}
	
	public static LinkedList<Transition> parallelTransitions(FiniteStateMachine N){
		LinkedList<Transition> parallels = new LinkedList<Transition>();
		for(State s : N.states()) {
			if(findParallels(N.to(s), parallels).size() > 1)
				break;
			else if(findParallels(N.from(s), parallels).size() > 1)
				break;
		}
		return parallels;
	}
	
	private static LinkedList<Transition> findParallels(Set<Transition> transitions, LinkedList<Transition> parallels){
		parallels.clear();
		LinkedList<Transition> queue = new LinkedList<Transition>(transitions);
		while(queue.size() > 1) {
			Transition tmp = queue.pop();
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
