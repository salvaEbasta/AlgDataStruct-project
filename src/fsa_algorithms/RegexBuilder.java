package fsa_algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import commoninterfaces.Automa;
import commoninterfaces.State;
import commoninterfaces.Transition;


public class RegexBuilder {
	public static String compute(Automa<S,T,I> N) {
		Logger log = loggerSetup();
		log.info(RegexBuilder.class.getSimpleName()+"::"+RegexBuilder.class.getMethods()[0].getName()+"...");
		log.fine("initial: "+N.toString());
		
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
		
		log.info("Post initialization: "+N.toString());
		
		//Initialization of markings
		HashMap<String, LinkedList<Transition>> markings = new HashMap<String, LinkedList<Transition>>();
		
		//To differentiate the id of the new transitions
		int counter = 0;
		
		//Buffer to compose the regex of the new transition
		StringBuilder regexBuilder = new StringBuilder();
		
		//main procedure
		while(N.states().size() > 2 || markings.values().stream().anyMatch(l->l.size()>1)) {
			regexBuilder.setLength(0);
			
			//Find a path of a single transition to and from a state of the sequence
			LinkedList<Transition> transitions = TransitionFinder.oneWayPath(N);
			StringBuilder mark = new StringBuilder();
			if(transitions.size() > 0) {
				log.info("Found one way path: "+transitions);
				Transition last = transitions.pollLast();
				N.remove(last);
				
				State source = transitions.getFirst().source();
				State sink = last.sink();
				regexBuilder.append("(");
				transitions.forEach(t->{
					N.remove(t);
					regexBuilder.append(t.regex());
				});
				Transition tmp = new Transition(source.id()+"-"+sink.id()+"_"+counter,
						source,
						sink,
						"");
				if(!last.sink().equals(nq) && !last.source().isAccepting()) {
					regexBuilder.append(last.regex()+")");
				}else {
					regexBuilder.append(")");
					if(!markings.containsKey(last.source().id()))
						markings.put(last.source().id(), new LinkedList<Transition>());
					markings.get(last.source().id()).add(tmp);
				}
				tmp.setRegex(regexBuilder.toString());
				N.add(tmp);
				log.info("New transition: "+tmp.toString());
			//Find transitions that are parallels
			}else if(findParallelTransitions(N, transitions)){
				log.info("Found parallels: "+transitions);
				Transition head = transitions.pop();
				N.remove(head);
				regexBuilder.append("("+head.regex());
				transitions.forEach(t->{
					N.remove(t);
					regexBuilder.append("|"+t.regex());
				});
				regexBuilder.append(")");
				String id = head.source().id()+"-"+head.sink().id()+"_"+counter;
				Transition union = new Transition(id,
						head.source(),
						head.sink(),
						regexBuilder.toString());
				N.add(union);
				log.info("New transition: "+union.toString());
			//Find transitions that have the same mark and are parallels
			}else if(findSameMarkParallelTransitions(markings, transitions, mark)) {
				log.info("Found same mark, parallels: "+transitions);
				Transition head = transitions.pop();
				N.remove(head);
				markings.get(mark.toString()).remove(head);
				
				regexBuilder.append("("+head.regex());
				transitions.forEach(t->{
					N.remove(t);
					markings.get(mark.toString()).remove(t);
					regexBuilder.append("|"+t.regex());
				});
				regexBuilder.append(")");
				Transition union = new Transition(head.source().id()+"-"+head.sink().id()+"_"+counter,
						head.source(),
						head.sink(),
						regexBuilder.toString());
				N.add(union);
				markings.get(mark.toString()).add(union);
				log.info("New transition: "+union.toString());
			}else {
				log.info("Default procedure");
				LinkedList<State> states = new LinkedList<State>(N.states());
				State n_tmp = states.pop();
				//n!=n0 && n!=nq
				while(n_tmp.equals(n0) || n_tmp.equals(nq)) 
					n_tmp = states.pop();
				State n = n_tmp;
				log.info("Selected state: "+n);
				N.to(n)
					.stream()
					.filter(r1->!r1.isAuto())
					.forEach(r1->{
						N.from(n)
							.stream()
							.filter(r2->!r2.isAuto())
							.forEach(r2->{
								String id = "t"+r1.id()+"- t"+r2.id();
								Transition tmp = new Transition(id,
										r1.source(),
										r2.sink(),
										"");
								if(r2.sink().equals(nq) && n.isAccepting()) {
									if(N.hasAuto(n)) {
										regexBuilder.append("(");
										regexBuilder.append(r1.regex());
										regexBuilder.append("("+N.getAuto(n).regex()+")*");
										regexBuilder.append(")");
									}else {
										regexBuilder.append(r1.regex());
									}
									if(!markings.containsKey(n.id()))
										markings.put(n.id(), new LinkedList<Transition>());
									markings.get(n.id()).add(tmp);
								}else if(N.hasAuto(n)) {
									regexBuilder.append("(");
									regexBuilder.append(r1.regex());
									regexBuilder.append("("+N.getAuto(n).regex()+")*");
									regexBuilder.append(r2.regex());
									regexBuilder.append(")");
								}else {
									regexBuilder.append("(");
									regexBuilder.append(r1.regex());
									regexBuilder.append(r2.regex());
									regexBuilder.append(")");
								}
								tmp.setRegex(regexBuilder.toString());
								N.add(tmp);
								log.info("New transition: "+tmp.toString());
							});
					});
				N.remove(n);
			}
			counter++;
			log.info("After procedure: "+N.toString());
		}
		
		//output building
		StringBuilder finalRegex = new StringBuilder("");
		N.transitions().forEach(t->finalRegex.append(t.regex()+"|"));
		finalRegex.deleteCharAt(finalRegex.length()-1);
		return finalRegex.toString();
	}
	
	private static Logger loggerSetup() {
		Logger log = Logger.getLogger(RegexBuilder.class.getSimpleName());
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		log.setLevel(Level.INFO);
		//log.addHandler(new StreamHandler(System.out, new SimpleFormatter()));
		return log;
	}
	
	private static boolean findSameMarkParallelTransitions(HashMap<String, LinkedList<Transition>> marks,
			LinkedList<Transition> transitions, StringBuilder key) {
		transitions.clear();
		key.setLength(0);
		Iterator<String> iter = marks.keySet().iterator();
		while(iter.hasNext()) {
			key.setLength(0);
			
			if(transitions.size() > 1)
				break;
			key.append(iter.next());
			LinkedList<Transition> marked = new LinkedList<Transition>(marks.get(key.toString()));
			while(marked.size() > 1) {
				Transition t = marked.pop();
				marked.forEach(t1->{if(t1.isParallelTo(t)) transitions.add(t1);});
				transitions.add(t);
				
				if(transitions.size() > 1)
					break;
				else
					transitions.clear();
			}
		}
		return transitions.size() > 0;
	}
	
	private static boolean findParallelTransitions(Automa N, LinkedList<Transition> transitions) {
		transitions.clear();
		transitions.addAll(TransitionFinder.parallelTransitions(N));
		return transitions.size() > 0;
	}
}
