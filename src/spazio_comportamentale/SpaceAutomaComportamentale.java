package spazio_comportamentale;

public class SpaceAutomaComportamentale extends SpaceAutoma<SpaceState>{

	public SpaceAutomaComportamentale(String id) {
		super(id);
	}
	
	public boolean hasEnteringObsTransitions(SpaceState s) {
		if(hasState(s))
			return super.structure.get(s).hasObservableEntering();
		else
			return false;
	}
}
