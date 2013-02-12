package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItemPhysicFactory;

public class StarFactory extends GameItemPhysicFactory<Star> {	

	@Override
	protected void createAnimList() {
		this.createAnim(Star.Anim_Wait, 4);
		this.createAnim(Star.Anim_Fade, 4);
	}

	@Override
	protected String getPlistPng() {
		return "items";
	}

	@Override
	protected Star instantiate(float x, float y, float width, float height) {
		SlimeFactory.LevelBuilder.addStar();
		return new Star(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Star item) {		
		item.waitAnim();		
	}
	
	public float getStarReferenceWidth() {
		return Star.Reference_Width;
	}
}
