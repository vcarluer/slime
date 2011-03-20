package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;
import gamers.associate.Slime.items.base.TextureAnimation;


public class LavaFactory extends GameItemPhysicFactory<Lava> {
		
	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(Lava.Anim_Init, 2);				
		this.createAnim(Lava.Anim_Init, 2, 0.5f);
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected Lava instantiate(float x, float y, float width, float height) {
		return new Lava(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Lava item) {
	}
}
