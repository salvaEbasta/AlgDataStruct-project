package fsa_algorithms;

import java.util.LinkedList;

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
	public static LinkedList<Transition> oneWayPath(FiniteStateMachine N){
		LinkedList<Transition> sequence = new LinkedList<Transition>();
		for(State s : N.states()) {
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
	
	private static void buildSequence(FiniteStateMachine N, State s, LinkedList<Transition> sequence) {
		buildUpstream(N, s, sequence);
		buildDownstream(N, s, sequence);
	}
	
	private static void buildUpstream(FiniteStateMachine N, State source, LinkedList<Transition> sequence) {
		//System.out.println("Upstream of "+source+": "+N.to(source));
		if(N.to(source).size() == 1 && N.from(source).size() == 1) {
			Transition inT = N.to(source).iterator().next();
			if(!inT.isAuto()) {
				sequence.addFirst(inT);
				buildUpstream(N, inT.source(), sequence);
			}
		}
	}
	
	private static void buildDownstream(FiniteStateMachine N, State sink, LinkedList<Transition> sequence) {
		//System.out.println("Downstream of "+sink+": "+N.from(sink));
		if(N.from(sink).size() == 1 && N.to(sink).size() == 1) {
			Transition outT = N.from(sink).iterator().next();
			if(!outT.isAuto()) {
				sequence.addLast(outT);
				buildDownstream(N, outT.sink(), sequence);
			}
		}
	}
	
	public static LinkedList<Transition> parallelTransitions(FiniteStateMachine N){
		LinkedList<Transition> parallels = new LinkedList<Transition>();
		LinkedList<Transition> transitions = new LinkedList<Transition>(N.transitions());
		while(transitions.size() > 1) {
			Transition tmp = transitions.pop();
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
