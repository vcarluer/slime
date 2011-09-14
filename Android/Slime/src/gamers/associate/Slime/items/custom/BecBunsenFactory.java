package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class BecBunsenFactory extends GameItemPhysicFactory<BecBunsen>{
	private boolean isOn;
	private float delay;
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
	
	public BecBunsen[] create(float x, float y, float width, float height, int number, String name, boolean isOn) {
		BecBunsen[] created = new BecBunsen[number];
		
		for (int i = 0; i < number; i++) {
			this.isOn = isOn;
			this.delay = i / 2f;

			BecBunsen becBunsen = super.create(x + i * width, y, width, height);
			becBunsen.setName(name);				
			created[i] = becBunsen;
		}
				
		return created;
	}
	
	public BecBunsen create(float x, float y, float width, float height, String name, float delay, boolean isOn) {		
		this.isOn = isOn;
		this.delay = delay;

		BecBunsen becBunsen = super.create(x, y, width, height);
		becBunsen.setName(name);			
				
		return becBunsen;
	}
	
	public BecBunsen createBL(float x, float y, float width, float height, String name, float delay, boolean isOn) {
		return this.create(x + width / 2, y + height / 2, width, height, name, delay, isOn);
	}
	
	public BecBunsen[] createBL(float x, float y, float width, float height, int number, String name) {
		return this.create(x + width / 2, y + height / 2, width, height, number, name, true);
	}
	
	public BecBunsen[] createBL(float x, float y, float width, float height, int number) {
		return this.createBL(x, y, width, height, number);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.ItemFactoryBase#initItem(gamers.associate.Slime.items.base.GameItemCocos)
	 */
	@Override
	protected void initItem(BecBunsen item) {
		item.setStartOn(this.isOn);
		item.setAnimDelay(this.delay);
		super.initItem(item);
	}
}
