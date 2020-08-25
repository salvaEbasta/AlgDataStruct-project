package test.comportamentale_fa;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

class TestOtherNet {
	
	private ComportamentalState s0;
	private ComportamentalState s1;
	private ComportamentalState b0;
	private ComportamentalState b1;
	
	
	private ComportamentalTransition ts1;
	private ComportamentalTransition ts2;
	private ComportamentalTransition ts3;
	private ComportamentalTransition ts4;
	private ComportamentalTransition tb1;
	private ComportamentalTransition tb2;
	private ComportamentalTransition tb3;
	private ComportamentalTransition tb4;
	private ComportamentalTransition tb5;
	private ComportamentalTransition tb6;
	private ComportamentalTransition tb7;
	private ComportamentalTransition tb8;
	
	private Event op;
	private Event cl;
	private Event emptyEv;
	
	
	CFSMnetwork initialize() {
		//AUTOMA s
		ComportamentalFSM s = new ComportamentalFSM("s");
		s0 = new ComportamentalState("0");
		s1 = new ComportamentalState("1");
		s.insert(s0);
		s.insert(s1);
		s.setInitial(s0);
		
		//AUTOMA b
		ComportamentalFSM b = new ComportamentalFSM("b");
		b0 = new ComportamentalState("0");
		b1 = new ComportamentalState("1");
		b.insert(b0);
		b.insert(b1);
		b.setInitial(b0);
		
		op = new Event("op");
		cl = new Event("cl");
		emptyEv = new Event();
		Link l = new Link("L", s, b);
		HashMap<Event, Link> outOP = new HashMap<Event, Link>();
		outOP.put(op, l);
		HashMap<Event, Link> outCL = new HashMap<Event, Link>();
		outOP.put(cl, l);
		
		ts1 = new ComportamentalTransition("s1", s0, s1, outOP, new ObservableLabel("act"), new RelevantLabel());
		ts2 = new ComportamentalTransition("s2", s1, s0, outCL, new ObservableLabel("sby"), new RelevantLabel());
		ts3 = new ComportamentalTransition("s3", s0, s0, outCL, new ObservableLabel(), new RelevantLabel("f1"));
		ts4 = new ComportamentalTransition("s4", s1, s1, outOP, new ObservableLabel(), new RelevantLabel("f2"));
				
		tb1 = new ComportamentalTransition("b1", b0, b1, op, l, new ObservableLabel("opn"), new RelevantLabel());
		tb2 = new ComportamentalTransition("b2", b1, b0, cl, l, new ObservableLabel("cls"), new RelevantLabel());
		tb3 = new ComportamentalTransition("b3", b0, b0, op, l, new ObservableLabel(), new RelevantLabel("f3"));
		tb4 = new ComportamentalTransition("b4", b1, b1, cl, l, new ObservableLabel(), new RelevantLabel("f4"));
		tb5 = new ComportamentalTransition("b5", b0, b0, cl, l, new ObservableLabel("nop"), new RelevantLabel());
		tb6 = new ComportamentalTransition("b6", b1, b1, op, l, new ObservableLabel("nop"), new RelevantLabel());
		tb7 = new ComportamentalTransition("b7", b0, b1, op, l, new ObservableLabel("opn"), new RelevantLabel("f5"));
		tb8 = new ComportamentalTransition("b8", b1, b0, op, l, new ObservableLabel("cls"), new RelevantLabel("f6"));
		
		
		s.add(ts1);
		s.add(ts2);
		s.add(ts3);
		s.add(ts4);
		
		b.add(tb1);
		b.add(tb2);
		b.add(tb3);
		b.add(tb4);
		b.add(tb5);
		b.add(tb6);
		b.add(tb7);
		b.add(tb8);
		
		
		ArrayList<Link> listLink = new ArrayList<Link>();
		listLink.add(l);
		
		return new CFSMnetwork(listLink);
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
		SpaceState sc0 = new SpaceState(new ArrayList<ComportamentalState>(Arrays.asList(s0, b0)), 
				new ArrayList<Event>(Arrays.asList(emptyEv)));
		SpaceState sc1 = new SpaceState(new ArrayList<ComportamentalState>(Arrays.asList(s1, b0)), 
				new ArrayList<Event>(Arrays.asList(op)));
		SpaceState sc2 = new SpaceState(new ArrayList<ComportamentalState>(Arrays.asList(s0, b0)), 
				new ArrayList<Event>(Arrays.asList(cl)));
		SpaceState sc3 = new SpaceState(new ArrayList<ComportamentalState>(Arrays.asList(s1, b1)), 
				new ArrayList<Event>(Arrays.asList(emptyEv)));
		SpaceState sc4 = new SpaceState(new ArrayList<ComportamentalState>(Arrays.asList(s1, b0)), 
				new ArrayList<Event>(Arrays.asList(emptyEv)));
		SpaceState sc5 = new SpaceState(new ArrayList<ComportamentalState>(Arrays.asList(s0, b1)), 
				new ArrayList<Event>(Arrays.asList(cl)));
		SpaceState sc6 = new SpaceState(new ArrayList<ComportamentalState>(Arrays.asList(s1, b1)), 
				new ArrayList<Event>(Arrays.asList(op)));
		SpaceState sc7 = new SpaceState(new ArrayList<ComportamentalState>(Arrays.asList(s0, b1)), 
				new ArrayList<Event>(Arrays.asList(emptyEv)));
		
		toMatch.insert(sc0);
		toMatch.insert(sc1);
		toMatch.insert(sc2);
		toMatch.insert(sc3);
		toMatch.insert(sc4);
		toMatch.insert(sc5);
		toMatch.insert(sc6);
		toMatch.insert(sc7);
		
		SpaceTransition<SpaceState> t01 = new SpaceTransition<SpaceState>(sc0, sc1, ts1);
		SpaceTransition<SpaceState> t02 = new SpaceTransition<SpaceState>(sc0, sc2, ts3);
		SpaceTransition<SpaceState> t14 = new SpaceTransition<SpaceState>(sc1, sc4, tb3);
		SpaceTransition<SpaceState> t20 = new SpaceTransition<SpaceState>(sc2, sc0, tb5);
		SpaceTransition<SpaceState> t42 = new SpaceTransition<SpaceState>(sc4, sc2, ts2);
		SpaceTransition<SpaceState> t41 = new SpaceTransition<SpaceState>(sc4, sc1, ts4);
		SpaceTransition<SpaceState> t13 = new SpaceTransition<SpaceState>(sc1, sc3, tb1);
		SpaceTransition<SpaceState> t36 = new SpaceTransition<SpaceState>(sc3, sc6, ts4);
		SpaceTransition<SpaceState> t63 = new SpaceTransition<SpaceState>(sc6, sc3, tb6);
		SpaceTransition<SpaceState> t35 = new SpaceTransition<SpaceState>(sc3, sc5, ts2);
		SpaceTransition<SpaceState> t50 = new SpaceTransition<SpaceState>(sc5, sc0, tb2);
		SpaceTransition<SpaceState> t57 = new SpaceTransition<SpaceState>(sc5, sc7, tb4);
		SpaceTransition<SpaceState> t75 = new SpaceTransition<SpaceState>(sc5, sc7, ts3);
		SpaceTransition<SpaceState> t76 = new SpaceTransition<SpaceState>(sc7, sc6, ts1);
		SpaceTransition<SpaceState> t64 = new SpaceTransition<SpaceState>(sc6, sc4, tb8);
		SpaceTransition<SpaceState> t27 = new SpaceTransition<SpaceState>(sc2, sc7, tb7);
		
		toMatch.add(t01);
		toMatch.add(t02);
		toMatch.add(t14);
		toMatch.add(t20);
		toMatch.add(t42);
		toMatch.add(t41);
		toMatch.add(t13);
		toMatch.add(t36);
		toMatch.add(t63);
		toMatch.add(t35);
		toMatch.add(t50);
		toMatch.add(t57);
		toMatch.add(t75);
		toMatch.add(t76);
		toMatch.add(t64);
		toMatch.add(t27);				
		
		ArrayList<SpaceState> computedStates = new ArrayList<SpaceState>(computedSpace.states());
		ArrayList<SpaceState> matchStates = new ArrayList<SpaceState>(toMatch.states());
		ArrayList<SpaceState> computedStatesCopy = new ArrayList<SpaceState>(computedStates);
		
		ArrayList<SpaceTransition<SpaceState>> computedTr = new ArrayList<SpaceTransition<SpaceState>>(computedSpace.transitions());
		ArrayList<SpaceTransition<SpaceState>> matchTr = new ArrayList<SpaceTransition<SpaceState>>(toMatch.transitions());
		ArrayList<SpaceTransition<SpaceState>> computedTrCopy = new ArrayList<SpaceTransition<SpaceState>>(computedTr);
		
		System.out.println(computedSpace.toString());
		
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
		SpaceStateObs sc1 = new SpaceStateObs(new ArrayList<ComportamentalState>(Arrays.asList(s20, s30)), 
				new ArrayList<Event>(Arrays.asList(emptyEv, emptyEv)), 0, obsList.size());
		SpaceStateObs sc2 = new SpaceStateObs(new ArrayList<ComportamentalState>(Arrays.asList(s20, s31)), 
				new ArrayList<Event>(Arrays.asList(e2, emptyEv)), 1, obsList.size());
		SpaceStateObs sc3 = new SpaceStateObs(new ArrayList<ComportamentalState>(Arrays.asList(s21, s31)), 
				new ArrayList<Event>(Arrays.asList(emptyEv, e3)), 2, obsList.size());
		SpaceStateObs sc5 = new SpaceStateObs(new ArrayList<ComportamentalState>(Arrays.asList(s21, s30)), 
				new ArrayList<Event>(Arrays.asList(emptyEv, emptyEv)), 2, obsList.size());
		SpaceStateObs sc4 = new SpaceStateObs(new ArrayList<ComportamentalState>(Arrays.asList(s21, s31)), 
				new ArrayList<Event>(Arrays.asList(emptyEv, emptyEv)), 2, obsList.size());
		SpaceStateObs sc6 = new SpaceStateObs(new ArrayList<ComportamentalState>(Arrays.asList(s20, s31)), 
				new ArrayList<Event>(Arrays.asList(emptyEv, e3)), 2, obsList.size());
		SpaceStateObs sc7 = new SpaceStateObs(new ArrayList<ComportamentalState>(Arrays.asList(s20, s31)), 
				new ArrayList<Event>(Arrays.asList(emptyEv, emptyEv)), 2, obsList.size());
		SpaceStateObs sc8 = new SpaceStateObs(new ArrayList<ComportamentalState>(Arrays.asList(s20, s30)), 
				new ArrayList<Event>(Arrays.asList(emptyEv, emptyEv)), 2, obsList.size());
		
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
		assertTrue(output.equals("εε(f(r(εε|fε)|ε)|εε)"));
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
