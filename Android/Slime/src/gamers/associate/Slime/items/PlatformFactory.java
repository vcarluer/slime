package gamers.associate.Slime.items;

public class PlatformFactory extends GameItemPhysicFactory<Platform>{
	
	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected Platform instantiate(float x, float y, float width, float height) {
		return new Platform(this.rootNode, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Platform item) {	
	}	
}
