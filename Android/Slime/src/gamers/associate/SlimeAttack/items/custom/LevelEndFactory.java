package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemPhysicFactory;



public class LevelEndFactory extends GameItemPhysicFactory<LevelEnd>{
		
	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected LevelEnd instantiate(float x, float y, float width, float height) {
		return new LevelEnd(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(LevelEnd item) {	
	}	
}
