package fsa_algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import finite_state_automata.FiniteStateMachine;
import finite_state_automata.State;
import finite_state_automata.Transition;

public class RegexBuilder {
	
	
	/**
	 * @param N
	 * @return
	 */
	public static String compute(FiniteStateMachine N) {
		Logger log = Logger.getLogger(RegexBuilder.class.getSimpleName());
		log.setLevel(Level.FINEST);
		
		//Initialization of N
		State n0 = null;
		if(N.to(N.initialState()).size() > 0) {
			n0 = new State("n0");
			N.insert(n0);
			N.add(new Transition("n0-"+N.initialState().id(), n0, N.initialState(), ""));
			N.setInitial(n0);
		} else {
			n0 = N.initialState();
		}
		State nq = new State("nq");
		N.insert(nq);
		N.acceptingStates().forEach((s)->N.add(new Transition(s.id()+"-"+nq.id(), s, nq, "")));
		
		log.finest("After initialization: "+N.toString());
		
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
				LinkedList<Transition> parallels = TransitionFinder.parallelTransitions(N);
				if(parallels.size() > 0) {
					Transition head = parallels.pop();
					N.remove(head);
					StringBuilder sb = new StringBuilder();
					parallels.forEach(t->{
						N.remove(t);
						sb.append("|"+t.regex());
					});
					Transition union = new Transition(head.source().id()+"-"+head.sink().id()+"_"+N.states().size(),
							head.source(),
							head.sink(),
							sb.toString());
					N.add(union);
				} else {
					//parallel transitions with same mark
					String key = "";
					LinkedList<Transition> markedParallels = new LinkedList<Transition>();
					Iterator<String> iter = markings.keySet().iterator();
					while(iter.hasNext()) {
						key = iter.next();
						LinkedList<Transition> marked = new LinkedList<Transition>(markings.get(key));
						while(marked.size() > 1) {
							Transition t = marked.pop();
							marked.forEach(t1->{if(t1.isParallelTo(t)) markedParallels.add(t1);});
							if(markedParallels.size() > 1) {
								markedParallels.add(t);
								break;
							}else
								markedParallels.clear();
						}
						if(markedParallels.size() > 0)
							break;
					}
					if(markedParallels.size() > 0) {
						Transition head = markedParallels.pop();
						N.remove(head);
						markings.get(key).remove(head);
						StringBuilder sb = new StringBuilder();
						for(Transition t : markedParallels) {
							N.remove(t);
							markings.get(key).remove(t);
							sb.append("|"+t.regex());
						}
						Transition union = new Transition(head.source().id()+"-"+head.sink().id()+"_"+N.states().size(),
								head.source(),
								head.sink(),
								sb.toString());
						N.add(union);
						markings.get(key).add(union);
					} else {
						Iterator<State> stateIter = N.states().iterator();
						State n = stateIter.next();
						while(n.equals(n0) || n.equals(nq))
							n = stateIter.next();
						for(Transition r_1 : N.to(n)) {
							if(!r_1.isAuto()) {
								for(Transition r_2 : N.from(n)) {
									if(!r_2.isAuto()) {
										if(r_2.sink().equals(nq) && n.isAccepting()) {
											Transition link = new Transition(r_1.source().id()+"-"+r_2.sink().id(),
													r_1.source(),
													r_2.sink(),
													"");
											StringBuilder sb = new StringBuilder(r_1.regex());
											if(N.hasAuto(n)) {
												sb.append("(");
												sb.append(N.getAuto(n).regex());
												sb.append(")*");
											}
											link.setRegex(sb.toString());
											N.add(link);
											if(!markings.containsKey(n.id()))
												markings.put(n.id(), new LinkedList<Transition>());
											markings.get(n.id()).add(link);
										}else if(N.hasAuto(n)) {
											StringBuilder sb = new StringBuilder(r_1.regex());
											sb.append("(");
											sb.append(N.getAuto(n).regex());
											sb.append(")*");
											sb.append(r_2.regex());
											N.add(new Transition(r_1.source().id()+"-"+r_2.sink().id(),
													r_1.source(),
													r_2.sink(),
													sb.toString()));
										}else {
											N.add(new Transition(r_1.source().id()+"-"+r_2.sink().id(),
													r_1.source(),
													r_2.sink(),
													r_1.regex()+r_2.regex()));
										}
									}
								}
							}
						}
						N.remove(n);
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
}
