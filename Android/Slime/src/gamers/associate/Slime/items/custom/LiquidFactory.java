package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;
import gamers.associate.Slime.items.base.TextureAnimation;


public class LiquidFactory extends GameItemPhysicFactory<Liquid> {
		
	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(Liquid.Anim_Init, 8);				
		this.createAnim(Liquid.Anim_Init, 8);
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected Liquid instantiate(float x, float y, float width, float height) {
		return new Liquid(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Liquid item) {
	}
}
