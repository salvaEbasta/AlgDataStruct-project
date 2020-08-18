package spazio_comp_oss_lin;

import java.util.HashSet;
import java.util.Set;

import commoninterfaces.Automa;

public class SpaceAutoma extends Automa<SpaceStateOss, SpaceTransitionOss> {

	public SpaceAutoma(String id) {
		super(id);
	}

	@Override
	public Set<SpaceStateOss> acceptingStates() {
		Set<SpaceStateOss> acceptingStates = new HashSet<SpaceStateOss>();
		for(SpaceStateOss state: states()) {
			if(state.isFinalState())
				acceptingStates.add(state);
		}
		return acceptingStates;
	}

	
}
