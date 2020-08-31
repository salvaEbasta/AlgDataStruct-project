package test.fsm_algorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import finite_state_machine.FSMBuilder;
import finite_state_machine.FiniteState;
import finite_state_machine.FiniteTransition;
import finite_state_machine.LinkedTransitionsFSA;
import fsm_algorithms.MultipleRelRegexBuilder;
import fsm_interfaces.FiniteStateMachine;
import utility.Constants;

class TestMultipleRegexBuilder {
	@Test
	void test_pg59() throws Exception{
		FiniteStateMachine<FiniteState, FiniteTransition> pg59 = build_pg59();
		HashMap<FiniteState, String> result = new MultipleRelRegexBuilder<FiniteState, FiniteTransition>(pg59, 
				new FSMBuilder()).call();
		
		assertTrue(result.size()==5);
		assertTrue(result.get(new FiniteState("3")).equals("f"));
		assertTrue(result.get(new FiniteState("6")).equals(Constants.EPSILON));
		assertTrue(result.get(new FiniteState("7")).equals("(εr)"));
		assertTrue(result.get(new FiniteState("5")).equals("((fr)f)"));
		assertTrue(result.get(new FiniteState("0")).equals("((fr)ε)"));
	}
	
	
	@Test
	void test_pg50() throws Exception{
		FiniteStateMachine<FiniteState, FiniteTransition> pg50 = build_pg50();
		HashMap<FiniteState, String> result = new MultipleRelRegexBuilder<FiniteState, FiniteTransition>(pg50, 
				new FSMBuilder()).call();
		//System.out.println(result);
		//simplifiedResult = "(f(r(f)?)?)?" = "eps|(f((r(f|eps))|eps))" = "ε|(f((r(f|ε))|ε))"
		
		assertTrue(result.size() == 4);
		assertTrue(result.get(new FiniteState("4")).equals("(εεf)"));
		assertTrue(result.get(new FiniteState("7")).equals("(((εεf)r)f)"));
		assertTrue(result.get(new FiniteState("8")).equals("(((εεf)r)ε)"));
		assertTrue(result.get(new FiniteState("5")).equals("(εεε)"));
	}
	
	private static FiniteStateMachine<FiniteState, FiniteTransition> build_pg59() {
		FiniteStateMachine<FiniteState, FiniteTransition> c = new LinkedTransitionsFSA("pg59");
		FiniteState _2 = new FiniteState("2");
		FiniteState _3 = new FiniteState("3");
		FiniteState _6 = new FiniteState("6");
		FiniteState _7 = new FiniteState("7");
		FiniteState _4 = new FiniteState("4");
		FiniteState _5 = new FiniteState("5");
		FiniteState _0 = new FiniteState("0");
		_3.setAccepting(true);
		_6.setAccepting(true);
		_7.setAccepting(true);
		_5.setAccepting(true);
		_0.setAccepting(true);
		c.insert(_0);
		c.insert(_2);
		c.insert(_3);
		c.insert(_6);
		c.insert(_7);
		c.insert(_4);
		c.insert(_5);
		c.setInitial(_2);

		FiniteTransition t3c_0 = new FiniteTransition("t3c_0", _2, _3);
		t3c_0.setRelevantLabel("f");
		c.add(t3c_0);
		FiniteTransition t3b_0 = new FiniteTransition("t3b_0", _2, _6);
		t3b_0.setRelevantLabel(Constants.EPSILON);
		c.add(t3b_0);
		FiniteTransition t2b_0 = new FiniteTransition("t2b_0", _6, _7);
		t2b_0.setRelevantLabel("r");
		c.add(t2b_0);
		FiniteTransition t2b_1 = new FiniteTransition("t2b_1", _3, _4);
		t2b_1.setRelevantLabel("r");
		c.add(t2b_1);
		FiniteTransition t3b_1 = new FiniteTransition("t3b_1", _4, _0);
		t3b_1.setRelevantLabel(Constants.EPSILON);
		c.add(t3b_1);
		FiniteTransition t3c_1 = new FiniteTransition("t3c_1", _4, _5);
		t3c_1.setRelevantLabel("f");
		c.add(t3c_1);
		return c;
	}
	
	private static FiniteStateMachine<FiniteState, FiniteTransition> build_pg50() {
		FiniteStateMachine<FiniteState, FiniteTransition> C1 = new LinkedTransitionsFSA("pg50");
		FiniteState _1 = new FiniteState("1");
		FiniteState _2 = new FiniteState("2");
		FiniteState _3 = new FiniteState("3");
		FiniteState _4 = new FiniteState("4");
		FiniteState _5 = new FiniteState("5");
		FiniteState _6 = new FiniteState("6");
		FiniteState _7 = new FiniteState("7");
		FiniteState _8 = new FiniteState("8");
		_4.setAccepting(true);
		_5.setAccepting(true);
		_7.setAccepting(true);
		_8.setAccepting(true);
		C1.insert(_1);
		C1.insert(_2);
		C1.insert(_3);
		C1.insert(_4);
		C1.insert(_5);
		C1.insert(_6);
		C1.insert(_7);
		C1.insert(_8);
		C1.setInitial(_1);
		
		FiniteTransition t3a = new FiniteTransition("t3a", _1, _2);
		t3a.setRelevantLabel(Constants.EPSILON);
		C1.add(t3a);
		FiniteTransition t2a = new FiniteTransition("t2a", _2, _3);
		t2a.setRelevantLabel(Constants.EPSILON);
		C1.add(t2a);
		FiniteTransition t3b_0 = new FiniteTransition("t3b_0", _3, _5);
		t3b_0.setRelevantLabel(Constants.EPSILON);
		C1.add(t3b_0);
		FiniteTransition t3c_0 = new FiniteTransition("t3c_0", _3, _4);
		t3c_0.setRelevantLabel("f");
		C1.add(t3c_0);
		FiniteTransition t2b = new FiniteTransition("t2b", _4, _6);
		t2b.setRelevantLabel("r");
		C1.add(t2b);
		FiniteTransition t3b_1 = new FiniteTransition("t3b_1", _6, _8);
		t3b_1.setRelevantLabel(Constants.EPSILON);
		C1.add(t3b_1);
		FiniteTransition t3c_1 = new FiniteTransition("t3c_1", _6, _7);
		t3c_1.setRelevantLabel("f");
		C1.add(t3c_1);
		
		//System.out.print(C1.toString());
		return C1;
	}
}
