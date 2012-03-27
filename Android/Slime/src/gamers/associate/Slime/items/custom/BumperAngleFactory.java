package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class BumperAngleFactory extends GameItemPhysicFactory<BumperAngle> {

	@Override
	protected void createAnimList() {
		this.createAnim(BumperAngle.Anim_Wait, 1);
		this.createAnim(BumperAngle.Anim_Bump, 2);
	}

	@Override
	protected String getPlistPng() {
		return "items";
	}

	@Override
	protected BumperAngle instantiate(float x, float y, float width, float height) {		
		return new BumperAngle(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(BumperAngle item) {		
		item.waitAnim();		
	}

	@Override
	public BumperAngle create(float x, float y, float width, float height) { 
		return this.create(x, y, width, height, BumperAngle.Default_Powa);		
	}
	
	public BumperAngle create(float x, float y, float width, float height, float powa) {
		BumperAngle bumper = super.create(x, y, width, height);
		bumper.setPowa(powa);
		return bumper;
	}
	
	public BumperAngle createBL(float x, float y, float width, float height, float powa) {
		return this.create(x + width / 2, y + height / 2, width, height, powa);
	}
}
