package spazio_comportamentale;

import java.util.Set;

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
	
	@Override
	public Object clone() {
		SpaceAutomaComportamentale deepCopy = (SpaceAutomaComportamentale) super.clone();
		return deepCopy;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("SpaceAutomaComportamentale: %s\n", id()));
		sb.append(String.format("[Numero Stati: %d - Numero Transizioni: %d]\n", states().size(), transitions().size()));
		for(SpaceState state: states()) {
			sb.append(state.toString());
			Set<SpaceTransition<SpaceState>>  in = to(state);
			Set<SpaceTransition<SpaceState>> out = from(state);
			if(!in.isEmpty()) {
				sb.append("\n\t- Input Transitions:");
				for(SpaceTransition<SpaceState> inTransition: in) {
					sb.append(String.format("\n\t\t* %s", inTransition));
				}
			}
			if(!out.isEmpty()) {
				sb.append("\n\t- Output Transitions:");
				for(SpaceTransition<SpaceState> outTransition: out) {
					sb.append(String.format("\n\t\t* %s", outTransition));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
