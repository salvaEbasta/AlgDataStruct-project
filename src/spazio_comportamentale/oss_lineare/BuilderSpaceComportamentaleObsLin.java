package spazio_comportamentale.oss_lineare;

import commoninterfaces.Builder;
import spazio_comportamentale.SpaceTransition;

public class BuilderSpaceComportamentaleObsLin implements Builder<SpaceStateObs, SpaceTransition<SpaceStateObs>>{

	@Override
	public SpaceStateObs newState(String id) {
		return new SpaceStateObs(id);
	}

	@Override
	public SpaceTransition<SpaceStateObs> newTransition(String id, SpaceStateObs source, SpaceStateObs destination) {
		return new SpaceTransition<SpaceStateObs>(id, source, destination);
	}

}
