package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class LaserGunFactory extends GameItemPhysicFactory<LaserGun>{
	private boolean isOn;
	private String name;
	private String target;
	@Override
	protected void createAnimList() {
		this.createAnim(LaserGun.Anim_Firing, 4);
		this.createAnim(LaserGun.Anim_On, 1);
		this.createAnim(LaserGun.Anim_Wait, 1);
	}

	@Override
	protected String getPlistPng() {
		return "items";
	}

	@Override
	protected LaserGun instantiate(float x, float y, float width, float height) {
		LaserGun laser = new LaserGun(x, y, width, height, this.world, this.worldRatio);		
		return laser;
	}

	@Override
	protected void runFirstAnimations(LaserGun item) {
		item.initanimation();
	}
	
	public LaserGun create(float x, float y, float width, float height, String name, String target, boolean isOn) {		
		this.isOn = isOn;
		this.name = name;
		this.target = target;
		LaserGun laser = super.create(x, y, width, height);			
		return laser;
	}
	
	public LaserGun createBL(float x, float y, float width, float height, String name, String target, boolean isOn) {
		return this.create(x + width / 2, y + height / 2, width, height, name, target, isOn);
	}		

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.ItemFactoryBase#initItem(gamers.associate.Slime.items.base.GameItemCocos)
	 */
	@Override
	protected void initItem(LaserGun item) {
		item.setStartOn(this.isOn);
		item.setName(this.name);	
		item.setTarget(this.target);
		super.initItem(item);
	}
}
