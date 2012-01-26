package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;



public class SpawnCannonFactory extends GameItemPhysicFactory<SpawnCannon>{

	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "items";
	}

	@Override
	protected SpawnCannon instantiate(float x, float y, float width, float height) {
		return new SpawnCannon(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(SpawnCannon item) {	
	}	
}
