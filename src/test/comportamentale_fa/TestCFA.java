package test.comportamentale_fa;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import comportamental_fsm.ComportamentalFSM;
import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalState;
import comportamental_fsm.ComportamentalTransition;
import comportamental_fsm.Event;
import comportamental_fsm.Link;
import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.ObservationsList;
import comportamental_fsm.labels.RelevantLabel;
import fsm_algorithms.RegexBuilder;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpaceTransition;
import spazio_comportamentale.SpazioComportamentale;
import spazio_comportamentale.oss_lineare.BuilderSpaceComportamentaleObsLin;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpaceStateObs;
import spazio_comportamentale.oss_lineare.SpazioComportamentaleObs;

class TestCFA {
	
	private ComportamentalFSM c2;
	private ComportamentalFSM c3;	
	
	private ComportamentalState s20;
	private ComportamentalState s21;
	private ComportamentalState s30;
	private ComportamentalState s31;
	
	
	private ComportamentalTransition t2a;
	private ComportamentalTransition t2b;
	private ComportamentalTransition t3a;
	private ComportamentalTransition t3b;
	private ComportamentalTransition t3c;
	
	private Event e2;
	private Event e3;
	private Event emptyEv;
	private Link l2;
	private Link l3;
	
	
	private CFSMnetwork initialize() {
		//AUTOMATA C2
		c2 = new ComportamentalFSM("C2");
		s20 = new ComportamentalState("20");
		s21 = new ComportamentalState("21");
		c2.insert(s20);
		c2.insert(s21);
		c2.setInitial(s20);
		
		//AUTOMATA C3
		c3 = new ComportamentalFSM("C3");
		s30 = new ComportamentalState("30");
		s31 = new ComportamentalState("31");
		c3.insert(s30);
		c3.insert(s31);
		c3.setInitial(s30);
		
		e2 = new Event("e2");
		e3 = new Event("e3");
		emptyEv = new Event();
		l2 = new Link("L2", c3, c2);
		l3 = new Link("L3", c2, c3);
		HashMap<Event, Link> out = new HashMap<Event, Link>();
		out.put(e3, l3);
		HashMap<Event, Link> out2 = new HashMap<Event, Link>();
		out2.put(e2, l2);
		
		t2a = new ComportamentalTransition("t2a", s20, s21, e2, l2, out, new ObservableLabel("o2"), new RelevantLabel());
		t2b = new ComportamentalTransition("t2b", s21, s20, out, new ObservableLabel(), new RelevantLabel("r"));
		
		t3a = new ComportamentalTransition("t3a", s30, s31, out2, new ObservableLabel("o3"), new RelevantLabel());
		t3b = new ComportamentalTransition("t3b", s31, s30, e3, l3, new ObservableLabel(), new RelevantLabel());
		t3c = new ComportamentalTransition("t3c", s31, s31, e3, l3, new ObservableLabel(), new RelevantLabel("f"));
		
		
		c2.add(t2a);
		c2.add(t2b);
		
		c3.add(t3a);
		c3.add(t3b);
		c3.add(t3c);
		
		
		ArrayList<Link> listLink = new ArrayList<Link>();
		 listLink.add(l2); listLink.add(l3);
		
		return new CFSMnetwork(listLink);
		
		
	}
	
	private HashMap<String, ComportamentalState> statesToHashMap(ComportamentalState stateC2, ComportamentalState stateC3) {
		HashMap<String , ComportamentalState> map = new HashMap<String , ComportamentalState>();
		map.put(c2.id(), stateC2);
		map.put(c3.id(), stateC3);
		return map;
	}
	
	private HashMap<Link, Event> eventsToHashMap(Link[] links, Event[] events) {
		HashMap<Link , Event> map = new HashMap<Link , Event>();
		for(int i=0; i<links.length; i++)
			map.put(links[i], events[i]);
		return map;
	}
	
	@Test
	void spazioComportamentale() throws Exception{		
		CFSMnetwork net = initialize();
		SpazioComportamentale sc = new SpazioComportamentale(net);
		SpaceAutomaComportamentale toMatch = new SpaceAutomaComportamentale("Test");
		SpaceAutomaComportamentale computedSpace = null;
		try {
			computedSpace = sc.call();
		} catch (Exception e) {
			assertFalse(false);
		}
		
		
		SpaceState sc0 = new SpaceState(statesToHashMap(s20, s30), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, emptyEv}));
		SpaceState sc1 = new SpaceState(statesToHashMap(s20, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{e2, emptyEv}));
		SpaceState sc2 = new SpaceState(statesToHashMap(s21, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, e3}));
		SpaceState sc6 = new SpaceState(statesToHashMap(s21, s30), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, emptyEv}));
		SpaceState sc7 = new SpaceState(statesToHashMap(s20, s30), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, e3}));
		SpaceState sc8 = new SpaceState(statesToHashMap(s20, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{e2, e3}));
		SpaceState sc9 = new SpaceState(statesToHashMap(s20, s30), eventsToHashMap(new Link[]{l2, l3}, new Event[]{e2, emptyEv}));
		SpaceState sc10 = new SpaceState(statesToHashMap(s21, s30), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, e3}));
		SpaceState sc11 = new SpaceState(statesToHashMap(s21, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{e2, e3}));
		SpaceState sc12 = new SpaceState(statesToHashMap(s21, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{e2, emptyEv}));
		SpaceState sc3 = new SpaceState(statesToHashMap(s21, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, emptyEv}));
		SpaceState sc4 = new SpaceState(statesToHashMap(s20, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, e3}));
		SpaceState sc5 = new SpaceState(statesToHashMap(s20, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, emptyEv}));
		
		toMatch.insert(sc0);
		toMatch.insert(sc1);
		toMatch.insert(sc2);
		toMatch.insert(sc3);
		toMatch.insert(sc4);
		toMatch.insert(sc5);
		toMatch.insert(sc6);
		toMatch.insert(sc7);
		toMatch.insert(sc8);
		toMatch.insert(sc9);
		toMatch.insert(sc10);
		toMatch.insert(sc11);
		toMatch.insert(sc12);
		
		SpaceTransition<SpaceState> t01 = new SpaceTransition<SpaceState>(sc0, sc1, t3a);
		SpaceTransition<SpaceState> t12 = new SpaceTransition<SpaceState>(sc1, sc2, t2a);
		SpaceTransition<SpaceState> t26 = new SpaceTransition<SpaceState>(sc2, sc6, t3b);
		SpaceTransition<SpaceState> t67 = new SpaceTransition<SpaceState>(sc6, sc7, t2b);
		SpaceTransition<SpaceState> t612 = new SpaceTransition<SpaceState>(sc6, sc12, t3a);
		SpaceTransition<SpaceState> t78 = new SpaceTransition<SpaceState>(sc7, sc8, t3a);
		SpaceTransition<SpaceState> t89 = new SpaceTransition<SpaceState>(sc8, sc9, t3b);
		SpaceTransition<SpaceState> t81 = new SpaceTransition<SpaceState>(sc8, sc1, t3c);
		SpaceTransition<SpaceState> t910 = new SpaceTransition<SpaceState>(sc9, sc10, t2a);
		SpaceTransition<SpaceState> t1011 = new SpaceTransition<SpaceState>(sc10, sc11, t3a);
		SpaceTransition<SpaceState> t1112 = new SpaceTransition<SpaceState>(sc11, sc12, t3c);
		SpaceTransition<SpaceState> t128 = new SpaceTransition<SpaceState>(sc12, sc8, t2b);
		SpaceTransition<SpaceState> t23 = new SpaceTransition<SpaceState>(sc2, sc3, t3c);
		SpaceTransition<SpaceState> t34 = new SpaceTransition<SpaceState>(sc3, sc4, t2b);
		SpaceTransition<SpaceState> t45 = new SpaceTransition<SpaceState>(sc4, sc5, t3c);
		SpaceTransition<SpaceState> t40 = new SpaceTransition<SpaceState>(sc4, sc0, t3b);
		
		toMatch.add(t01);
		toMatch.add(t12);
		toMatch.add(t26);
		toMatch.add(t67);
		toMatch.add(t612);
		toMatch.add(t78);
		toMatch.add(t89);
		toMatch.add(t81);
		toMatch.add(t910);
		toMatch.add(t1011);
		toMatch.add(t1112);
		toMatch.add(t128);
		toMatch.add(t23);
		toMatch.add(t34);
		toMatch.add(t45);
		toMatch.add(t40);		
		
		toMatch.ridenominazione();
		
		ArrayList<SpaceState> computedStates = new ArrayList<SpaceState>(computedSpace.states());
		ArrayList<SpaceState> matchStates = new ArrayList<SpaceState>(toMatch.states());
		ArrayList<SpaceState> computedStatesCopy = new ArrayList<SpaceState>(computedStates);
		
		ArrayList<SpaceTransition<SpaceState>> computedTr = new ArrayList<SpaceTransition<SpaceState>>(computedSpace.transitions());
		ArrayList<SpaceTransition<SpaceState>> matchTr = new ArrayList<SpaceTransition<SpaceState>>(toMatch.transitions());
		ArrayList<SpaceTransition<SpaceState>> computedTrCopy = new ArrayList<SpaceTransition<SpaceState>>(computedTr);
					
		computedStates.removeAll(matchStates);
		matchStates.removeAll(computedStatesCopy);
		computedTr.removeAll(matchTr);
		matchTr.removeAll(computedTrCopy);
				
		assertTrue(computedStates.isEmpty() && matchStates.isEmpty() &&
				computedTr.isEmpty() && matchTr.isEmpty());		

	}
	
	@Test

	void spazioComportamentaleOsservazioni() {

		CFSMnetwork net = initialize();
		ObservationsList obsList = new ObservationsList();
		obsList.add(new ObservableLabel("o3"));
		obsList.add(new ObservableLabel("o2"));
		SpazioComportamentaleObs sc = new SpazioComportamentaleObs(net, obsList);
		SpaceAutomaObsLin toMatch = new SpaceAutomaObsLin("Test con osservazione");
		SpaceAutomaObsLin computedSpace = null;
		try {
			computedSpace = sc.call();
		} catch (Exception e) {
			assertFalse(false);
		}
		SpaceStateObs sc1 = new SpaceStateObs(statesToHashMap(s20, s30), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, emptyEv}), 0, obsList.size());
		SpaceStateObs sc2 = new SpaceStateObs(statesToHashMap(s20, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{e2, emptyEv}), 1, obsList.size());
		SpaceStateObs sc3 = new SpaceStateObs(statesToHashMap(s21, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, e3}), 2, obsList.size());
		SpaceStateObs sc5 = new SpaceStateObs(statesToHashMap(s21, s30), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, emptyEv}), 2, obsList.size());
		SpaceStateObs sc4 = new SpaceStateObs(statesToHashMap(s21, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, emptyEv}), 2, obsList.size());
		SpaceStateObs sc6 = new SpaceStateObs(statesToHashMap(s20, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, e3}), 2, obsList.size());
		SpaceStateObs sc7 = new SpaceStateObs(statesToHashMap(s20, s31), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, emptyEv}), 2, obsList.size());
		SpaceStateObs sc8 = new SpaceStateObs(statesToHashMap(s20, s30), eventsToHashMap(new Link[]{l2, l3}, new Event[]{emptyEv, emptyEv}), 2, obsList.size());
		
		toMatch.insert(sc1);
		toMatch.insert(sc2);
		toMatch.insert(sc3);
		toMatch.insert(sc5);
		toMatch.insert(sc4);
		toMatch.insert(sc6);
		toMatch.insert(sc7);
		toMatch.insert(sc8);
		
		SpaceTransition<SpaceStateObs> t12 = new SpaceTransition<SpaceStateObs>(sc1, sc2, t3a);
		SpaceTransition<SpaceStateObs> t23 = new SpaceTransition<SpaceStateObs>(sc2, sc3, t2a);
		SpaceTransition<SpaceStateObs> t35 = new SpaceTransition<SpaceStateObs>(sc3, sc5, t3b);
		SpaceTransition<SpaceStateObs> t34 = new SpaceTransition<SpaceStateObs>(sc3, sc4, t3c);
		SpaceTransition<SpaceStateObs> t46 = new SpaceTransition<SpaceStateObs>(sc4, sc6, t2b);
		SpaceTransition<SpaceStateObs> t67 = new SpaceTransition<SpaceStateObs>(sc6, sc7, t3c);
		SpaceTransition<SpaceStateObs> t68 = new SpaceTransition<SpaceStateObs>(sc6, sc8, t3b);

		
		toMatch.add(t12);
		toMatch.add(t23);
		toMatch.add(t35);
		toMatch.add(t34);
		toMatch.add(t46);
		toMatch.add(t67);
		toMatch.add(t68);
		
		ArrayList<SpaceStateObs> computedStates = new ArrayList<SpaceStateObs>(computedSpace.states());
		ArrayList<SpaceStateObs> matchStates = new ArrayList<SpaceStateObs>(toMatch.states());
		ArrayList<SpaceStateObs> computedStatesCopy = new ArrayList<SpaceStateObs>(computedStates);
		
		ArrayList<SpaceTransition<SpaceStateObs>> computedTr = new ArrayList<SpaceTransition<SpaceStateObs>>(computedSpace.transitions());
		ArrayList<SpaceTransition<SpaceStateObs>> matchTr = new ArrayList<SpaceTransition<SpaceStateObs>>(toMatch.transitions());
		ArrayList<SpaceTransition<SpaceStateObs>> computedTrCopy = new ArrayList<SpaceTransition<SpaceStateObs>>(computedTr);
		
		
		computedStates.removeAll(matchStates);
		matchStates.removeAll(computedStatesCopy);
		computedTr.removeAll(matchTr);
		matchTr.removeAll(computedTrCopy);
		
		
		assertTrue(computedStates.isEmpty() && matchStates.isEmpty()
				&& computedTr.isEmpty()	&& matchTr.isEmpty());		
	}
	

	@Test
	void diagnostica() throws Exception{
		CFSMnetwork net = initialize();
		ObservationsList obsLin = new ObservationsList();
		obsLin.add(new ObservableLabel("o3"));
		obsLin.add(new ObservableLabel("o2"));
		SpazioComportamentaleObs sc = new SpazioComportamentaleObs(net, obsLin);
		SpaceAutomaObsLin computedSpace = sc.call();
		computedSpace.potatura();
		computedSpace.ridenominazione();
		String output = RegexBuilder.relevanceRegex(computedSpace, new BuilderSpaceComportamentaleObsLin());
		System.out.println("Result: "+output);
		//simplifiedOutput = "(f(r(f)?)?)?" = "eps|(f((r(f|eps))|eps))" = "ε|(f((r(f|ε))|ε))"
		assertTrue(output.equals("εε(f(r(εε|fε)|ε)|εε)") || 
				output.equals("εε(εε|f(ε|r(εε|fε)))") ||
				output.equals("εε(f(r(fε|εε)|ε)|εε)"));
	}

	
	@Test
	void enabledTransitions() {
		CFSMnetwork net = initialize();
		Set<ComportamentalTransition> enabledT = new HashSet<ComportamentalTransition>();
		enabledT.add(t3a);
		assertTrue(net.enabledTransitions().equals(enabledT));
		net.transitionTo(t3a);
		enabledT.remove(t3a);
		enabledT.add(t2a);
		assertTrue(net.enabledTransitions().equals(enabledT));
	}
	
	@Test
	void test_silentTransitions() {
		CFSMnetwork net = initialize();
		assertTrue(!t3a.isSilent());
		assertTrue(!t2a.isSilent());
		assertTrue(t2b.isSilent());
	}

}
