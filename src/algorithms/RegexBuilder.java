package algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import finiteStateAutomata.FiniteStateMachine;
import finiteStateAutomata.State;
import finiteStateAutomata.Transition;

public class RegexBuilder {
	/**
	 * @param asf
	 * @return
	 */
	public static String compute(FiniteStateMachine N) {
		//Initialization of N
		State n0 = null;
		if(N.to(N.initialState()).size() > 0) {
			n0 = new State("n0");
			N.insert(n0);
			N.add(new Transition("n0-b0", n0, N.initialState(), ""));
			N.setInitial(n0);
		} else {
			n0 = N.initialState();
		}
		State nq = new State("nq");
		N.insert(nq);
		N.acceptingStates().forEach((s)->N.add(new Transition(s.id()+"-"+nq.id(), s, nq, "")));
		
		HashMap<String, LinkedList<Transition>> markings = new HashMap<String, LinkedList<Transition>>();
		
		//main loop
		while(N.states().size() > 2 || markings.values().stream().anyMatch(l->l.size()>0)) {
			//sequence of transitions
			List<Transition> sequence = TransitionFinder.oneWayPath(N);
			if(sequence.size() > 0) {
				Transition last = sequence.get(sequence.size()-1);
				StringBuilder sb = new StringBuilder();
				sequence.forEach(t->{
					N.remove(t);
					sb.append(t.regex());
				});
				State source = sequence.get(0).source();
				State sink = last.sink();
				Transition tmp = new Transition(source.id()+"-"+sink.id()+"_"+N.states().size(),
						source,
						sink,
						sb.toString());
				N.add(tmp);
				if(last.sink().equals(nq) || last.source().isAccepting()) {
					markings.put(last.source().id(), new LinkedList<Transition>());
					markings.get(last.source().id()).add(tmp);
				}
			} else {
				//parallel transitions
				List<Transition> parallelT = findParallelTransitions(N);
				if(parallelT.size() > 0) {
				} else {
					//parallel transitions with same mark
					List<Transition> parallelSameMark = parellelSameMark(N, markings);
					if(parallelSameMark.size() > 0) {
						
					} else {
						
					}
				}
				
			} 
		}
		
		//output building
		StringBuilder sb = new StringBuilder("");
		N.transitions().forEach(t->sb.append(t.regex()+"|"));
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	private static List<Transition> findParallelTransitions(FiniteStateMachine N){
		return null;
	}
	
	private static List<Transition> parellelSameMark(FiniteStateMachine N, HashMap<String, LinkedList<Transition>> markings){
		return null;
	}
}
