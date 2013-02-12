package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemPhysicFactory;

public class EnergyBallGunFactory extends GameItemPhysicFactory<EnergyBallGun> {
	private boolean isOn;
	private String target;
	private float ballSpeed;
	private float waitTime;
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
	protected EnergyBallGun instantiate(float x, float y, float width,
			float height) {
		return new EnergyBallGun(x, y,width, height, world, worldRatio);
	}

	@Override
	protected void runFirstAnimations(EnergyBallGun item) {
		item.initanimation();
	}

	public EnergyBallGun create(float x, float y, float width, float height, String name, String target, boolean isOn, float ballSpeed, float waitTime) {		
		this.isOn = isOn;
		this.target = target;
		this.ballSpeed = ballSpeed;
		this.waitTime = waitTime;
		EnergyBallGun gun = super.create(name, x, y, width, height);			
		return gun;
	}
	
	public EnergyBallGun createBL(float x, float y, float width, float height, String name, String target, boolean isOn, float ballSpeed, float waitTime) {
		return this.create(x + width / 2, y + height / 2, width, height, name, target, isOn, ballSpeed, waitTime);
	}		

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.ItemFactoryBase#initItem(gamers.associate.Slime.items.base.GameItemCocos)
	 */
	@Override
	protected void initItem(EnergyBallGun item) {
		item.setStartOn(this.isOn);
		item.setTarget(this.target);
		item.setBallSpeed(this.ballSpeed);
		item.setWaitTime(this.waitTime);
		super.initItem(item);
	}
}
