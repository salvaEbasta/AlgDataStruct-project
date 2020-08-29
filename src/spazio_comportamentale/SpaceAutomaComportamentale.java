package spazio_comportamentale;

public class SpaceAutomaComportamentale extends SpaceAutoma<SpaceState>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpaceAutomaComportamentale(String id) {
		super(id);
	}
	
	public SpaceAutomaComportamentale(SpaceAutomaComportamentale sac) {
		super(sac);
	}
	
	
	@Override
	public Object clone() {
		SpaceAutomaComportamentale deepCopy = new SpaceAutomaComportamentale(this);
		return deepCopy;
	}
	
}
