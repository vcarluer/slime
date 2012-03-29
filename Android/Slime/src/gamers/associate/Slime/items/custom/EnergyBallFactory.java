package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;
import gamers.associate.Slime.items.base.TextureAnimation;

public class EnergyBallFactory extends GameItemPhysicFactory<EnergyBall> {

	@Override
	protected void createAnimList() {
		// temp from files
		TextureAnimation.createFramesFromFiles(EnergyBall.Anim_Wait, 5);
		this.createAnim(EnergyBall.Anim_Wait, 5);
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected EnergyBall instantiate(float x, float y, float width, float height) {
		return new EnergyBall(x, y, width, height, world, worldRatio);
	}

	@Override
	protected void runFirstAnimations(EnergyBall item) {
		item.waitAnim();
	}
}
