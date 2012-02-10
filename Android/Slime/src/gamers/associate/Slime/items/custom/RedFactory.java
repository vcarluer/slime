package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;

public class RedFactory extends GameItemPhysicFactory<Red> {

	private boolean isBoss;

	@Override
	protected void createAnimList() {
		this.createAnim(Red.Anim_Bite, 5);
		this.createAnim(Red.Anim_Breaking, 7);
		this.createAnim(Red.Anim_Contracting, 3);
		this.createAnim(Red.Anim_Wait, 2, 1f);
		this.createAnim(Red.Anim_WaitDefense, 1);
		this.createAnim(Red.Anim_WaitDead, 1);
	}

	@Override
	protected String getPlistPng() {
		return "red";
	}

	@Override
	protected Red instantiate(float x, float y, float width, float height) {
		return new Red(x, y, width, height, this.world, this.worldRatio, this.isBoss);
	}

	@Override
	protected void runFirstAnimations(Red item) {
		item.waitAnimReal();
	}
	
	public Red createBL(float x, float y) {
		float width = Red.Default_Width;
		float height = Red.Default_Height;
		return this.create(x + width / 2, y + height / 2, width, height);
	}
	
	public Red createBL(float x, float y, float width, float height, boolean isBoss) {
		this.isBoss = isBoss;
		return this.create(x + width / 2, y + height / 2, width, height);
	}
}
