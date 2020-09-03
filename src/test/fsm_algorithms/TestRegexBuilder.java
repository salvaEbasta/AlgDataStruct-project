package test.fsm_algorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import finite_state_machine.FSMBuilder;
import finite_state_machine.FiniteState;
import finite_state_machine.FiniteTransition;
import finite_state_machine.LinkedTransitionsFSA;
import fsm_algorithms.MultipleRelRegexBuilder;
import fsm_interfaces.FiniteStateMachine;
import utility.Constants;

class TestRegexBuilder {
	@Test
	void test_benchmarkC1() throws Exception {
		FiniteStateMachine<FiniteState, FiniteTransition> C1 = build_benchmarkC1();
		HashMap<FiniteState, String> result = new MultipleRelRegexBuilder<FiniteState, FiniteTransition>(C1, new FSMBuilder()).call();
		String output = toRegex(result);
		System.out.println(output);
		assertTrue(output.equalsIgnoreCase("(ε(((c|a)b))*(c|a))"));
	}
	
	@Test
	void test_pg50() throws Exception {
		FiniteStateMachine<FiniteState, FiniteTransition> pg50 = build_pg50();
		HashMap<FiniteState, String> result = new MultipleRelRegexBuilder<FiniteState, FiniteTransition>(pg50, 
				new FSMBuilder()).call();
		String output = toRegex(result);
		assertTrue(output.equalsIgnoreCase("(εεf)|(εεε)|(((εεf)r)f)|(((εεf)r)ε)"));
	}
	
	@Test
	void test_pg50_decorations_state4() throws Exception {
		FiniteStateMachine<FiniteState, FiniteTransition> pg50 = build_pg50();
		HashMap<FiniteState, String> result = new MultipleRelRegexBuilder<FiniteState, FiniteTransition>(pg50, 
				new FSMBuilder()).call();		
		//System.out.println(regex);
		String regex = result.get(new FiniteState("4"));
		regex = regex.replace("(", "");
		regex = regex.replace(")", "");
		regex = regex.replace(Constants.EPSILON, "");
		regex = regex.trim();
		assertTrue(regex.equals("f"));
	}
	
	@Test
	void test_pg50_decorations_state7() throws Exception {
		FiniteStateMachine<FiniteState, FiniteTransition> pg50 = build_pg50();
		HashMap<FiniteState, String> result = new MultipleRelRegexBuilder<FiniteState, FiniteTransition>(pg50, 
				new FSMBuilder()).call();
		String regex = result.get(new FiniteState("7"));
		regex = regex.replace("(", "");
		regex = regex.replace(")", "");
		regex = regex.replace(Constants.EPSILON, "");
		regex = regex.trim();
		assertTrue(regex.equals("frf"));
	}
	
	@Test
	void test_pg50_decorations_state8() throws Exception {
		FiniteStateMachine<FiniteState, FiniteTransition> pg50 = build_pg50();
		HashMap<FiniteState, String> result = new MultipleRelRegexBuilder<FiniteState, FiniteTransition>(pg50, 
				new FSMBuilder()).call();
		//System.out.println(regex);
		String regex = result.get(new FiniteState("8"));
		regex = regex.replace("(", "");
		regex = regex.replace(")", "");
		regex = regex.replace(Constants.EPSILON, "");
		regex = regex.trim();
		assertTrue(regex.equals("fr"));
	}
	
	@Test
	void test_pg50_decorations_state5() throws Exception {
		FiniteStateMachine<FiniteState, FiniteTransition> pg50 = build_pg50();
		HashMap<FiniteState, String> result = new MultipleRelRegexBuilder<FiniteState, FiniteTransition>(pg50, 
				new FSMBuilder()).call();
		//System.out.println(regex);
		String regex = result.get(new FiniteState("5"));
		regex = regex.replace("(", "");
		regex = regex.replace(")", "");
		regex = regex.replace(Constants.EPSILON, "");
		regex = regex.trim();
		assertTrue(regex.isEmpty());
	}

	private static FiniteStateMachine<FiniteState, FiniteTransition> build_benchmarkC1() {
		FiniteStateMachine<FiniteState, FiniteTransition> C1 = new LinkedTransitionsFSA("C1");
		FiniteState _10 = new FiniteState("10");
		FiniteState _11 = new FiniteState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		FiniteTransition t1a = new FiniteTransition("t1a", _10, _11);
		t1a.setRelevantLabel("a");
		FiniteTransition t1b = new FiniteTransition("t1b", _11, _10);
		t1b.setRelevantLabel("b");
		FiniteTransition t1c = new FiniteTransition("t1c", _10, _11);
		t1c.setRelevantLabel("c");
		C1.add(t1a);
		C1.add(t1b);
		C1.add(t1c);
		//System.out.print(C1.toString());
		return C1;
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
	
	private String toRegex(HashMap<FiniteState, String> result) {
		Iterator<String> iterator = result.values().iterator();
		String relDiagnosi = null;
		if(iterator.hasNext()) {
			StringBuilder sbDiagn = new StringBuilder(iterator.next());
			while(iterator.hasNext())
				sbDiagn.append("|").append(iterator.next());
			relDiagnosi = sbDiagn.toString();		
		} else 
			relDiagnosi = Constants.EPSILON;
		return relDiagnosi;
	}
}
