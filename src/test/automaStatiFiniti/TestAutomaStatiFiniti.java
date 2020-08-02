package test.automaStatiFiniti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import automaStatiFiniti.SempliceASF;
import automaStatiFiniti.Stato;
import automaStatiFiniti.Transizione;

class TestAutomaStatiFiniti {

	@Test
	void building_benchmarkC1() {
		SempliceASF C1 = new SempliceASF("C1");
		assertTrue(C1.id()=="C1");
		
		Stato _10 = new Stato("10");
		_10.setAccettazione(true);
		Stato _11 = new Stato("11");
		_11.setAccettazione(true);
		C1.inserisci(_11);
		C1.inserisci(_10);
		C1.setIniziale(_10);
		
		Transizione t1a = new Transizione("t1a", _10, _11, "a");
		Transizione t1b = new Transizione("t1b", _11, _10, "b");
		Transizione t1c = new Transizione("t1c", _10, _11, "c");
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
