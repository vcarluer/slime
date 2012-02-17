package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;
import gamers.associate.Slime.items.base.TextureAnimation;


public class LiquidSurfaceFactory extends GameItemPhysicFactory<LiquidSurface> {
		
	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(LiquidSurface.Anim_Init, 4);				
		this.createAnim(LiquidSurface.Anim_Init, 4);
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected LiquidSurface instantiate(float x, float y, float width, float height) {
		return new LiquidSurface(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(LiquidSurface item) {
	}
}
