package gamers.associate.Slime.items;


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
		return new Box(this.spriteSheet, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Box item) {
	}
}
