package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class ButtonFactory extends GameItemPhysicFactory<Button> {

	@Override
	protected void createAnimList() {
		this.createAnim(Button.Anim_Wait, 1);
		this.createAnim(Button.Anim_Bump, 2);
	}

	@Override
	protected String getPlistPng() {
		return "items";
	}

	@Override
	protected Button instantiate(float x, float y, float width, float height) {		
		return new Button(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Button item) {		
		item.waitAnim();		
	}
	
	public Button create(float x, float y, float width, float height, String target, float resetTime) {
		Button button = super.create(x, y, width, height);
		button.setTarget(target);
		button.setResetTime(resetTime);
		return button;
	}
	
	public Button createBL(float x, float y, float width, float height, String target, float resetTime) {
		return this.create(x + width / 2, y + height / 2, width, height, target, resetTime);
	}
}
