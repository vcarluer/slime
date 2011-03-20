package gamers.associate.Slime.items;


public class LavaFactory extends GameItemPhysicFactory<Lava> {
		
	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(Lava.Anim_Init, 2);				
		this.createAnim(Lava.Anim_Init, 2, 0.5f);
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected Lava instantiate(float x, float y, float width, float height) {
		return new Lava(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Lava item) {
	}
}
