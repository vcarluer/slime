package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;

public class EvacuationPlugfactory extends GameItemPhysicFactory<EvacuationPlug> {

	private static final String TANK = "tank";

	@Override
	protected void createAnimList() {
		this.createAnim(EvacuationPlug.Anim_Base, 1);
	}

	@Override
	protected String getPlistPng() {
		return TANK;
	}

	@Override
	protected EvacuationPlug instantiate(float x, float y, float width,
			float height) {
		return new EvacuationPlug(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(EvacuationPlug item) {		
	}
}
