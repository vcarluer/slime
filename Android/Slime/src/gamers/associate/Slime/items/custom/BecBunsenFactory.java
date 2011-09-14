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
		BecBunsen bec = new BecBunsen(x, y, width, height, this.world, this.worldRatio);		
		return bec;
	}

	@Override
	protected void runFirstAnimations(BecBunsen item) {
		item.initanimation();
	}
	
	public BecBunsen[] create(float x, float y, float width, float height, int number, String name, float delay, boolean isOn) {
		BecBunsen[] created = new BecBunsen[number];
		
		for (int i = 0; i < number; i++) {
			BecBunsen becBunsen = super.create(x + i * width, y, width, height);
			becBunsen.setName(name);
			becBunsen.setStartOn(isOn);
			becBunsen.setAnimDelay(delay + (i / 2f));
			created[i] = becBunsen;
		}
				
		return created;
	}
	
	public BecBunsen create(float x, float y, float width, float height, String name, float delay, boolean isOn) {		
		BecBunsen becBunsen = super.create(x, y, width, height);
		becBunsen.setName(name);
		becBunsen.setStartOn(isOn);
		becBunsen.setAnimDelay(delay);
				
		return becBunsen;
	}
	
	public BecBunsen createBL(float x, float y, float width, float height, String name, float delay, boolean isOn) {
		return this.create(x + width / 2, y + height / 2, width, height, name, delay, isOn);
	}
	
	public BecBunsen[] createBL(float x, float y, float width, float height, int number, String name) {
		return this.create(x + width / 2, y + height / 2, width, height, number, name, 0f, true);
	}
	
	public BecBunsen[] createBL(float x, float y, float width, float height, int number) {
		return this.createBL(x, y, width, height, number);
	}
}
