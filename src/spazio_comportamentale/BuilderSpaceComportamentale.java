package spazio_comportamentale;

import commoninterfaces.Builder;

public class BuilderSpaceComportamentale implements Builder<SpaceState, SpaceTransition<SpaceState>>{

	@Override
	public SpaceState newState(String id) {
		return new SpaceState(id);
	}

	@Override
	public SpaceTransition<SpaceState> newTransition(String id, SpaceState source, SpaceState destination) {
		return new SpaceTransition<SpaceState>(id, source, destination);
	}

}
