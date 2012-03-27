package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemCocosFactory;
import gamers.associate.Slime.items.base.GameItemFactory;
import gamers.associate.Slime.items.base.TextureAnimation;

public class TargetFactory extends GameItemCocosFactory<Target> {
	
	
	@Override
	protected Target instantiate(float x, float y, float width, float height) {
		return new Target(x, y, width, height);
	}

	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(Target.Anim_Base, 1);		
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected void runFirstAnimations(Target item) {
	}
}
