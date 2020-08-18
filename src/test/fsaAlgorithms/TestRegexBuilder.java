package test.fsaAlgorithms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import finite_state_automata.FiniteStateMachine;
import finite_state_automata.State;
import finite_state_automata.Transition;
import finite_state_automata.base_implementation.BaseFSABuilder;
import fsa_algorithms.RegexBuilder;

class TestRegexBuilder {
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
	void test_benchmarkC1() {
		FiniteStateMachine C1 = build_benchmarkC1();
		String result = RegexBuilder.compute(C1);
		
		System.out.println(result);
		assertTrue(true);
	}

}
