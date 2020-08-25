package diagnosticatore.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import algorithm_interfaces.Algorithm;
import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.ObservationsList;
import diagnosticatore.ClosureSpace;
import diagnosticatore.SilentClosure;
import utility.Constants;

public class LinearDiagnosis extends Algorithm<String>{
	private ClosureSpace cSpace;
	private ObservationsList linearObs;
	private StringBuilder R;
	
	public LinearDiagnosis(ClosureSpace diagnosticatore, ObservationsList linearObs) {
		super();
		this.cSpace = diagnosticatore;
		this.linearObs = linearObs;
		this.R = new StringBuilder();
	}
	
	@Override
	public String call() throws Exception {
		log.info(this.getClass().getSimpleName()+"::call...");
		log.info("Executed on: "+cSpace.toString());
		log.info("LinearObs: "+linearObs.toString());
		
		//Initialize stringBuilder
		R.setLength(0);
		
		//Initialize X
		HashMap<SilentClosure, String> X = new HashMap<SilentClosure, String>();
		X.put(cSpace.initialState(), Constants.EPSILON);
		
		//Main cycle
		for(int i=0; i < linearObs.size(); i++) {
			ObservableLabel o = linearObs.get(i);
			
			log.info("Observable event: "+o);
			
			HashMap<SilentClosure, String> X_new = new HashMap<SilentClosure, String>();
			
			X.forEach((x, p)->{
				log.info(String.format("Selected (x:%s, p:%s)", x.id(), p));
				cSpace.from(x).forEach(t->{
					
					// For each <x, (o,p), x2>
					// x2 = t.sink()
					if(t.observableLabel().equals(o)) {
						log.info("Selected transition: "+t);
						
						String p2 = p.concat(t.relevantLabelContent());
						if(X_new.containsKey(t.sink())) {
							String p2_i = X_new.get(t.sink());
							X_new.put(t.sink(), "(".concat(p2_i).concat("|").concat(p2).concat(")"));
						} else 
							X_new.put(t.sink(), p2);
						
						log.info(String.format("New Mapping: (x2:%s, p:%s)", t.sink().id(), X_new.get(t.sink())));
					}
				});
			});
			
			X = X_new;
			
			log.info("New X: "+X);
		}

		//Remove not accepting states
		log.info("Removing notAccepting states...");
		
		Set<SilentClosure> tmp = new HashSet<SilentClosure>(X.keySet());
		for(SilentClosure s : tmp)
			if(!s.isAccepting())
				X.remove(s);
		
		//Compose R
		log.info("Composing R...");
		
		Iterator<SilentClosure> iter = X.keySet().iterator();
		if(X.size() == 1) {
			SilentClosure x = iter.next();
			R.append(X.get(x).concat("(").concat(x.diagnosis()).concat(")"));
		} else {
			SilentClosure x = iter.next();
			R.append("(");
			R.append(X.get(x).concat("(").concat(x.diagnosis()).concat(")"));
			R.append(")");
			while(iter.hasNext()) {
				x = iter.next();
				R.append("|(");
				R.append(X.get(x).concat("(").concat(x.diagnosis()).concat(")"));
				R.append(")");
			}
		}
		
		log.info("R: "+R.toString());
		return R.toString();
	}

	@Override
	public String midResult() {
		return R.toString();
	}
	
	
}
