package test.fsaAlgorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import finite_state_automata.FiniteStateMachine;
import finite_state_automata.base_implementation.BaseFSABuilder;
import finite_state_automata.State;
import finite_state_automata.Transition;
import fsa_algorithms.TransitionFinder;

public class TestTransitionFinder {
	private static FiniteStateMachine build_benchmarkC1() {
		FiniteStateMachine C1 = BaseFSABuilder.newFSA("C1");
		State _10 = BaseFSABuilder.newState("10");
		State _11 = BaseFSABuilder.newState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		Transition t1a = BaseFSABuilder.newTransition("t1a", _10, _11, "a");
		Transition t1b = BaseFSABuilder.newTransition("t1b", _11, _10, "b");
		Transition t1c = BaseFSABuilder.newTransition("t1c", _10, _11, "c");
		C1.add(t1a);
		C1.add(t1b);
		C1.add(t1c);
		return C1;
	}
	
	@Test
	void noOneWayPath() {
		FiniteStateMachine c1 = BaseFSABuilder.newFSA("empty");
		List<Transition> result = TransitionFinder.oneWayPath(c1);
		
		assertTrue(result.size() == 0);
	}
	
	@Test
	void test_oneWayPath() {
		FiniteStateMachine c1 = build_benchmarkC1();
		List<Transition> result = TransitionFinder.oneWayPath(c1);
		
		assertTrue(result.size() == 0);
	}
	
	@Test
	void test_oneWayPath_singleCycle() {
		FiniteStateMachine C1 = BaseFSABuilder.newFSA("C1");
		State n0 = BaseFSABuilder.newState("n0");
		State _10 = BaseFSABuilder.newState("10");
		State _11 = BaseFSABuilder.newState("11");
		State nq = BaseFSABuilder.newState("nq");
		_11.setAccepting(true);
		nq.setAccepting(true);
		C1.insert(n0);
		C1.insert(nq);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(n0);
		Transition t1a = BaseFSABuilder.newTransition("t1a", _10, _11, "a");
		Transition t1b = BaseFSABuilder.newTransition("t1b", _11, _10, "b");
		Transition eps_0 = BaseFSABuilder.newTransition("eps_0", n0, _10, "");
		Transition eps_1 = BaseFSABuilder.newTransition("eps_1", _11, nq, "");
		C1.add(t1a);
		C1.add(t1b);
		C1.add(eps_0);
		C1.add(eps_1);
		assertTrue(C1.to(_10).size() == 2);
		System.out.println(C1);
		
		List<Transition> result = TransitionFinder.oneWayPath(C1);
		System.out.println(result);
		assertTrue(result.size() == 0);
	}
	
	@Test
	void noParallels() {
		FiniteStateMachine c1 = build_benchmarkC1();
		Transition t1c = c1.transitions().stream().filter(t->t.id().equals("t1c")).findAny().get();
		c1.remove(t1c);
		List<Transition> result = TransitionFinder.parallelTransitions(c1);
		assertTrue(result.size() == 0);
	}
	
	@Test
	void test_findParallels() {
		FiniteStateMachine c1 = build_benchmarkC1();
		List<Transition> result = TransitionFinder.parallelTransitions(c1);
		
		assertTrue(result.size() == 2);
	}
}
