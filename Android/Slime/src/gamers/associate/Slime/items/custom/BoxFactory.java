package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class BoxFactory extends GameItemPhysicFactory<Box>{

	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected Box instantiate(float x, float y, float width, float height) {
		return new Box(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Box item) {
	}
}
