package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class BumperFactory extends GameItemPhysicFactory<Bumper> {

	@Override
	protected void createAnimList() {
		// this.createAnim(Bumper.Anim_Wait, 1);
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected Bumper instantiate(float x, float y, float width, float height) {		
		return new Bumper(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Bumper item) {
		// item.waitAnim();		
	}

	@Override
	public Bumper create(float x, float y, float width, float height) { 
		return this.create(x, y, width, height, Bumper.Default_Powa);		
	}
	
	public Bumper create(float x, float y, float width, float height, float powa) {
		Bumper bumper = super.create(x, y, width, height);
		bumper.setPowa(powa);
		return bumper;
	}
	
	public Bumper createBL(float x, float y, float width, float height, float powa) {
		return this.create(x + width / 2, y + height / 2, width, height, powa);
	}
}
