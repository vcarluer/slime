package gamers.associate.SlimeAttack.items.custom;

import org.cocos2d.types.CGPoint;

import gamers.associate.SlimeAttack.items.base.GameItemPhysicFactory;


public class LaserBeamFactory extends GameItemPhysicFactory<LaserBeam> {
	private CGPoint source;
	private CGPoint target;
	private boolean isOn;
	@Override
	protected void createAnimList() {		
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected LaserBeam instantiate(float x, float y, float width, float height) {		
		return new LaserBeam(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(LaserBeam item) {		
	}
		
	public LaserBeam create(CGPoint source, CGPoint target, boolean isOn) {
		this.source = source;
		this.target = target;
		this.isOn = isOn;
		LaserBeam laser = super.create(0, 0, 0, 0);		
		return laser;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.ItemFactoryBase#initItem(gamers.associate.Slime.items.base.GameItemCocos)
	 */
	@Override
	protected void initItem(LaserBeam item) {
		item.setSource(this.source);
		item.setTarget(this.target);
		item.setOn(this.isOn);
		super.initItem(item);
	}
}
