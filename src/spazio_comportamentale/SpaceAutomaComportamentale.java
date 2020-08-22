package spazio_comportamentale;

public class SpaceAutomaComportamentale extends SpaceAutoma<SpaceState>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpaceAutomaComportamentale(String id) {
		super(id);
	}
	
	/**
	 * Controlla se uno stato ha una transizione entrante osservabile
	 * @param s
	 * @return
	 */
	public boolean hasEnteringObsTransitions(SpaceState s) {
		if(hasState(s))
			return super.structure.get(s).hasObservableEntering();
		else
			return false;
	}
}
