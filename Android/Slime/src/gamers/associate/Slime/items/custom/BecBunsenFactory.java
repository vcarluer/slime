package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class BecBunsenFactory extends GameItemPhysicFactory<BecBunsen>{

	@Override
	protected void createAnimList() {
		this.createAnim(BecBunsen.Anim_Base, 5);
		this.createAnim(BecBunsen.Anim_Starting, 3);
		this.createAnim(BecBunsen.Anim_Wait, 1);
	}

	@Override
	protected String getPlistPng() {
		return "items";
	}

	@Override
	protected BecBunsen instantiate(float x, float y, float width, float height) {
		return new BecBunsen(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(BecBunsen item) {
		item.turnOn();
	}
	
	public BecBunsen[] create(float x, float y, float width, float height, int number) {
		BecBunsen[] created = new BecBunsen[number];
		
		for (int i = 0; i < number; i++) {
			BecBunsen becBunsen = super.create(x + i * width, y, width, height);
			created[i] = becBunsen;
		}
		
		
		return created;
	}
	
	public BecBunsen[] createBL(float x, float y, float width, float height, int number) {
		return this.create(x + width / 2, y + height / 2, width, height, number);
	}
}
