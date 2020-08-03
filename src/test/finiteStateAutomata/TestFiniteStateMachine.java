package test.finiteStateAutomata;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import finiteStateAutomata.FiniteStateMachine;
import finiteStateAutomata.LinkedTransitionsFSA;
import finiteStateAutomata.State;
import finiteStateAutomata.Transition;

class TestFiniteStateMachine {

	@Test
	void building_benchmarkC1() {
		FiniteStateMachine C1 = new LinkedTransitionsFSA("C1");
		assertTrue(C1.id()=="C1");
		
		State _10 = new State("10");
		_10.setAccepting(true);
		State _11 = new State("11");
		_11.setAccepting(true);
		C1.inserisci(_11);
		C1.inserisci(_10);
		C1.setIniziale(_10);
		
		Transition t1a = new Transition("t1a", _10, _11, "a");
		Transition t1b = new Transition("t1b", _11, _10, "b");
		Transition t1c = new Transition("t1c", _10, _11, "c");
		C1.connetti(t1a);
		C1.connetti(t1b);
		C1.connetti(t1c);
		
		//_10;do-t1a:_11;do-t1b:_10;do-t1c:_11
		C1.start();
		assertTrue(C1.statoAttuale().equals(_10));
		assertTrue(C1.transizioniAbilitate().contains(t1a));
		assertTrue(C1.transizioniAbilitate().contains(t1c));
		assertTrue(C1.esegui(t1a)=="a");
		assertTrue(C1.statoAttuale().equals(_11));
		assertTrue(C1.transizioniAbilitate().contains(t1b));
		assertTrue(C1.esegui(t1b)=="b");
		assertTrue(C1.statoAttuale().equals(_10));
		assertTrue(C1.transizioniAbilitate().contains(t1c));
		assertTrue(C1.esegui(t1c)=="c");
		assertTrue(C1.statoAttuale().equals(_11));
	}

}
