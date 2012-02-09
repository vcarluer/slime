package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;

public class RedFactory extends GameItemPhysicFactory<Red> {

	@Override
	protected void createAnimList() {
		this.createAnim(Red.Anim_Bite, 5);
		this.createAnim(Red.Anim_Breaking, 7);
		this.createAnim(Red.Anim_Contracting, 3);
		this.createAnim(Red.Anim_Wait, 2, 1f);
	}

	@Override
	protected String getPlistPng() {
		return "red";
	}

	@Override
	protected Red instantiate(float x, float y, float width, float height) {
		return new Red(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Red item) {
		item.waitAnim();
	}
	
	public Red createBL(float x, float y) {
		float width = Red.Default_Width;
		float height = Red.Default_Height;
		return this.create(x + width / 2, y + height / 2, width, height);
	}
}
