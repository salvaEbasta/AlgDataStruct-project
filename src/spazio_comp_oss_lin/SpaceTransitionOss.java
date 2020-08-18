package spazio_comp_oss_lin;

import commoninterfaces.Transition;

public class SpaceTransitionOss extends Transition<SpaceStateOss> {

	public SpaceTransitionOss(String id, SpaceStateOss source, SpaceStateOss destination) {
		super(id, source, destination);
	}

}
