package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;
import gamers.associate.Slime.items.base.TextureAnimation;

public class PlatformFactory extends GameItemPhysicFactory<Platform>{
	
	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base, 1);		
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected Platform instantiate(float x, float y, float width, float height) {
		return new Platform(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Platform item) {	
	}	
}