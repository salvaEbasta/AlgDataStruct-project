package test.diagnosticatore;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import comportamental_fsm.ComportamentalFSM;
import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalState;
import comportamental_fsm.ComportamentalTransition;
import comportamental_fsm.Event;
import comportamental_fsm.Link;
import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.RelevantLabel;
import diagnosticatore.ClosureBuilder;
import diagnosticatore.SilentClosure;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpazioComportamentale;

class TestSilentClosure {
	private static CFSMnetwork initialize_pg26() {
		//AUTOMATA C2
		ComportamentalFSM c2 = new ComportamentalFSM("C2");
		ComportamentalState s20 = new ComportamentalState("20");
		ComportamentalState s21 = new ComportamentalState("21");
		c2.insert(s20);
		c2.insert(s21);
		c2.setInitial(s20);
		
		//AUTOMATA C3
		ComportamentalFSM c3 = new ComportamentalFSM("C3");
		ComportamentalState s30 = new ComportamentalState("30");
		ComportamentalState s31 = new ComportamentalState("31");
		c3.insert(s30);
		c3.insert(s31);
		c3.setInitial(s30);
		
		Event e2 = new Event("e2");
		Event e3 = new Event("e3");
		Link l2 = new Link("L2", c3, c2);
		Link l3 = new Link("L3", c2, c3);
		HashMap<Event, Link> out = new HashMap<Event, Link>();
		out.put(e3, l3);
		HashMap<Event, Link> out2 = new HashMap<Event, Link>();
		out2.put(e2, l2);
		
		ComportamentalTransition t2a = new ComportamentalTransition("t2a", s20, s21, e2, l2, out, new ObservableLabel("o2"), new RelevantLabel());
		ComportamentalTransition t2b = new ComportamentalTransition("t2b", s21, s20, out, new ObservableLabel(), new RelevantLabel("r"));
		c2.add(t2a);
		c2.add(t2b);
		
		ComportamentalTransition t3a = new ComportamentalTransition("t3a", s30, s31, out2, new ObservableLabel("o3"), new RelevantLabel());
		ComportamentalTransition t3b = new ComportamentalTransition("t3b", s31, s30, e3, l3, new ObservableLabel(), new RelevantLabel());
		ComportamentalTransition t3c = new ComportamentalTransition("t3c", s31, s31, e3, l3, new ObservableLabel(), new RelevantLabel("f"));
		c3.add(t3a);
		c3.add(t3b);
		c3.add(t3c);
		
		ArrayList<Link> listLink = new ArrayList<Link>();
		 listLink.add(l2); listLink.add(l3);
		
		return new CFSMnetwork(listLink);
	}
	
	private static SpaceAutomaComportamentale build_ComportamenalSpace_pg38(){
		CFSMnetwork pg26 = initialize_pg26();
		SpazioComportamentale sc = new SpazioComportamentale(pg26);
		SpaceAutomaComportamentale computedSpace = null;
		try {
			computedSpace = sc.call();
			computedSpace.potatura();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return computedSpace;
	}
	
	@Test
	void test_pg65_2() {
		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		HashMap<Integer, SpaceState> rename = pg38.ridenominazione();
		System.out.println(rename);
		Iterator<Entry<Integer, SpaceState>> iter = rename.entrySet().iterator();
		int key = -1;
		while(iter.hasNext()) {
			Entry<Integer, SpaceState> e = iter.next();
			if(e.getValue().hasEvent(new Event()) && e.getValue().hasEvent(new Event("e3")))
				if(e.getValue().hasState(new ComportamentalState("21")) && e.getValue().hasState(new ComportamentalState("31")))
					key = e.getKey();
		}
		//System.out.println("key: "+key);
		//System.out.println("State: "+ridenominazione.get(key));
		SilentClosure closure = ClosureBuilder.buildSilentClosure(pg38, rename.get(key));
		
		System.out.println(closure.toString());
		assertTrue(closure.states().size() == 7);
		assertTrue(closure.states().contains(rename.get(key)));
		assertTrue(closure.transitions().size() == 6);
		assertTrue(closure.acceptingStates().size() == 5);
		assertTrue(closure.exitStates().size() == 3);
		
		ClosureBuilder.decorate(closure);
		//System.out.println(closure.diagnosis());
		String diagnosis = closure.diagnosis();
		
		List<String> portions = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<diagnosis.length(); i++)
			if(diagnosis.charAt(i)=='|') {
				portions.add(sb.toString());
				sb.setLength(0);
			}else
				sb.append(diagnosis.charAt(i));
		portions.add(sb.toString());
		assertTrue(portions.size() == 4);
		assertTrue(portions.contains("fε"));
		assertTrue(portions.contains("frε"));
		assertTrue(portions.contains("frf"));
		assertTrue(portions.contains("εε"));
	}
	
	@Test
	void test_pg65_1() {
		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		HashMap<Integer, SpaceState> rename = pg38.ridenominazione();
		System.out.println(rename);
		Iterator<Entry<Integer, SpaceState>> iter = rename.entrySet().iterator();
		int key = -1;
		while(iter.hasNext()) {
			Entry<Integer, SpaceState> e = iter.next();
			if(e.getValue().hasEvent(new Event()) && e.getValue().hasEvent(new Event("e2")))
				if(e.getValue().hasState(new ComportamentalState("20")) && e.getValue().hasState(new ComportamentalState("31")))
					key = e.getKey();
		}
		//System.out.println("key: "+key);
		//System.out.println("State: "+ridenominazione.get(key));
		SilentClosure closure = ClosureBuilder.buildSilentClosure(pg38, rename.get(key));
		
		//System.out.println(closure.toString());
		assertTrue(closure.states().size() == 1);
		assertTrue(closure.states().contains(rename.get(key)));
		assertTrue(closure.transitions().size() == 0);
		assertTrue(closure.acceptingStates().size() == 1);
		assertTrue(closure.exitStates().size() == 1);

		ClosureBuilder.decorate(closure);
		//System.out.println(closure.diagnosis());
		assertTrue(closure.diagnosis().isEmpty());
	}
	
	@Test
	void test_pg65_10() {
		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		HashMap<Integer, SpaceState> rename = pg38.ridenominazione();
		System.out.println(rename);
		Iterator<Entry<Integer, SpaceState>> iter = rename.entrySet().iterator();
		int key = -1;
		while(iter.hasNext()) {
			Entry<Integer, SpaceState> e = iter.next();
			if(e.getValue().hasEvent(new Event()) && e.getValue().hasEvent(new Event("e3")))
				if(e.getValue().hasState(new ComportamentalState("21")) && e.getValue().hasState(new ComportamentalState("30")))
					key = e.getKey();
		}
		//System.out.println("key: "+key);
		//System.out.println("State: "+ridenominazione.get(key));
		SilentClosure closure = ClosureBuilder.buildSilentClosure(pg38, rename.get(key));
		
		//System.out.println(closure.toString());
		assertTrue(closure.states().size() == 1);
		assertTrue(closure.states().contains(rename.get(key)));
		assertTrue(closure.transitions().size() == 0);
		assertTrue(closure.acceptingStates().size() == 1);
		assertTrue(closure.exitStates().size() == 1);
		
		ClosureBuilder.decorate(closure);
		//System.out.println(closure.diagnosis());
		assertTrue(closure.diagnosis().isEmpty());
	}
	
	@Test
	void test_pg65_8() {
		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		HashMap<Integer, SpaceState> rename = pg38.ridenominazione();
		System.out.println(rename);
		Iterator<Entry<Integer, SpaceState>> iter = rename.entrySet().iterator();
		int key = -1;
		while(iter.hasNext()) {
			Entry<Integer, SpaceState> e = iter.next();
			if(e.getValue().hasEvent(new Event("e3")) && e.getValue().hasEvent(new Event("e2")))
				if(e.getValue().hasState(new ComportamentalState("20")) && e.getValue().hasState(new ComportamentalState("31")))
					key = e.getKey();
		}
		//System.out.println("key: "+key);
		//System.out.println("State: "+ridenominazione.get(key));
		SilentClosure closure = ClosureBuilder.buildSilentClosure(pg38, rename.get(key));
		
		System.out.println(closure.toString());
		assertTrue(closure.states().size() == 3);
		assertTrue(closure.states().contains(rename.get(key)));
		assertTrue(closure.transitions().size() == 2);
		assertTrue(closure.acceptingStates().size() == 2);
		assertTrue(closure.exitStates().size() == 2);

		ClosureBuilder.decorate(closure);
		//System.out.println(closure.diagnosis());
		assertTrue(closure.diagnosis().isEmpty());
	}
	
	@Test
	void test_pg65_11() {
		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		HashMap<Integer, SpaceState> rename = pg38.ridenominazione();
		System.out.println(rename);
		Iterator<Entry<Integer, SpaceState>> iter = rename.entrySet().iterator();
		int key = -1;
		while(iter.hasNext()) {
			Entry<Integer, SpaceState> e = iter.next();
			if(e.getValue().hasEvent(new Event("e3")) && e.getValue().hasEvent(new Event("e2")))
				if(e.getValue().hasState(new ComportamentalState("21")) && e.getValue().hasState(new ComportamentalState("31")))
					key = e.getKey();
		}
		//System.out.println("key: "+key);
		//System.out.println("State: "+ridenominazione.get(key));
		SilentClosure closure = ClosureBuilder.buildSilentClosure(pg38, rename.get(key));
		
		//System.out.println(closure.toString());
		assertTrue(closure.states().size() == 5);
		assertTrue(closure.states().contains(rename.get(key)));
		assertTrue(closure.transitions().size() == 4);
		assertTrue(closure.acceptingStates().size() == 2);
		assertTrue(closure.exitStates().size() == 2);

		ClosureBuilder.decorate(closure);
		//System.out.println(closure.diagnosis());
		assertTrue(closure.diagnosis().isEmpty());
	}
	
	@Test
	void test_pg65_12() {
		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		HashMap<Integer, SpaceState> rename = pg38.ridenominazione();
		System.out.println(rename);
		Iterator<Entry<Integer, SpaceState>> iter = rename.entrySet().iterator();
		int key = -1;
		while(iter.hasNext()) {
			Entry<Integer, SpaceState> e = iter.next();
			if(e.getValue().hasEvent(new Event()) && e.getValue().hasEvent(new Event("e2")))
				if(e.getValue().hasState(new ComportamentalState("21")) && e.getValue().hasState(new ComportamentalState("31")))
					key = e.getKey();
		}
		//System.out.println("key: "+key);
		//System.out.println("State: "+ridenominazione.get(key));
		SilentClosure closure = ClosureBuilder.buildSilentClosure(pg38, rename.get(key));
		
		//System.out.println(closure.toString());
		assertTrue(closure.states().size() == 4);
		assertTrue(closure.states().contains(rename.get(key)));
		assertTrue(closure.transitions().size() == 3);
		assertTrue(closure.acceptingStates().size() == 2);
		assertTrue(closure.exitStates().size() == 2);

		ClosureBuilder.decorate(closure);
		//System.out.println(closure.diagnosis());
		assertTrue(closure.diagnosis().isEmpty());
	}
	
	@Test
	void test_pg65_initialState() {
		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		SilentClosure closure = ClosureBuilder.buildSilentClosure(pg38, pg38.initialState());
		//System.out.println(closure.toString());
		
		assertTrue(closure.states().contains(pg38.initialState()) && closure.states().size() == 1);
		assertTrue(closure.transitions().size() == 0);
		assertTrue(closure.acceptingStates().size() == 1);
		assertTrue(closure.exitStates().size() == 1);

		ClosureBuilder.decorate(closure);
		//System.out.println(closure.diagnosis());
		assertTrue(closure.diagnosis().equals("ε"));
	}
	
	@Test
	void test_equals() {
		SilentClosure c1 = new SilentClosure("1");
		SilentClosure c2 = new SilentClosure("1");
		SilentClosure c3 = new SilentClosure("3");
		assertTrue(c1.equals(c2));
		assertFalse(c1.equals(c3));

		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		SilentClosure closure = ClosureBuilder.buildSilentClosure(pg38, pg38.initialState());
		c1 = new SilentClosure(closure.id());
		assertTrue(closure.equals(c1));
	}

}
