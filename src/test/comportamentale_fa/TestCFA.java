package test.comportamentale_fa;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import comportamentale_fa.CFA;
import comportamentale_fa.ComportamentaleFA;
import comportamentale_fa.Event;
import comportamentale_fa.Link;
import comportamentale_fa.State;
import comportamentale_fa.Transition;
import comportamentale_fa.labels.OsservableLabel;
import comportamentale_fa.labels.RelevantLabel;

class TestCFA {

	@Test
	void test() {
		ComportamentaleFA c2 = new CFA("C2");
		State s20 = new State("20");
		State s21 = new State("21");
		c2.insert(s20);
		c2.insert(s21);
		c2.setInitial(s20);
		
		ComportamentaleFA c3 = new CFA("C3");
		State s30 = new State("30");
		State s31 = new State("31");
		c3.insert(s30);
		c3.insert(s31);
		c3.setInitial(s30);
		
		Event e2 = new Event("e2");
		Event e3 = new Event("e3");
		Link l2 = new Link("L2", c3, c2);
		Link l3 = new Link("L3", c2, c3);
		HashMap<Event, Link> out = new HashMap();
		out.put(e3, l3);
		Transition t2a = new Transition("t2a", s20, s21, e2, l2, out, new OsservableLabel("o2"), new RelevantLabel(""));
		
		
		System.out.println(t2a.toString());
	}

}
