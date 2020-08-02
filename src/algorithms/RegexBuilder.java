package algorithms;

import finiteStateAutomata.FiniteStateMachine;
import finiteStateAutomata.State;
import finiteStateAutomata.Transition;

public class RegexBuilder {
	/**
	 * @param asf
	 * @return
	 * Esecuzione:
	 * 
	 * if entra una transizione nello stato iniziale B_0
	 * then:
	 * 		crea nuovo stato iniziale n_0 e una eps-transizione da n_0 a B_0
	 * else:
	 * 		n_0 è stato iniziale
	 * 
	 * if esistono più stati di accettazione OR esiste una transizione uscente dall'unico stato di accettazione
	 * then:
	 * 		crea lo stato finale n_q
	 * 		for ogni stato di accettazione B_q:
	 * 			crea eps-transizione da B_q a n_q
	 * else:
	 * 		indicare come stato finale n_q l'unico stato di accettazione
	 * 
	 * N è l'ASF così ottenuto
	 * 
	 * while N ha più di una transizione
	 * do:
	 * 		if esiste una sequenza di transizioni(con num transizioni >=2) dove ogni stato non ha altre transizioni entranti o uscenti
	 * 		then:
	 * 			sostituisci la sequenza con una transizione che vada dal primo all'ultimo stato, con regex pari alla concatenazione delle regex
	 * 		else if esiste un insieme di transizioni parallele uscenti dallo stato n e entranti in n^1
	 * 		then:
	 * 			sostituisci le transizioni con una transizione da n a n^1 con regex pari all'alternativa di ogni singola regex delle transizioni alternative
	 * 		else:
	 * 			sia n uno stato diverso da n_0 e diverso da n_q
	 * 			for ogni transizione entrante in n, con origine n^1 diversa da n e regex r^1:
	 * 				for ogni transizione uscente da n, con destinazione n^2 diversa da n e regex r^2:
	 * 					if esiste un'autotransizione su n con regex r
	 * 					then:
	 * 						inserisci transizione da n^1 a n^2 con regex r^1(r)*r^2
	 * 					else:
	 * 						inserisci transizione da n^1 a n^2 con regex r^1r^2
	 * 			rimuovi n e tutte le transizioni entranti e uscenti da n
	 * return R, regex dell'unica transizione da n_0 a n_q
	 * 
	 */
	public static String valuta(FiniteStateMachine asf) {
		return "";
	}
}
