package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemPhysicFactory;


public class ButtonFactory extends GameItemPhysicFactory<Button> {

	@Override
	protected void createAnimList() {
		this.createAnim(Button.Anim_Wait_On, 1);
		this.createAnim(Button.Anim_Wait_Off, 1);
		this.createAnim(Button.Anim_Countdown, 4);
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
	
	public Button create(String name, float x, float y, float width, float height, String target, float resetTime) {
		Button button = super.create(name, x, y, width, height);
		button.setTarget(target);
		button.setResetTime(resetTime);
		return button;
	}
	
	public Button createBL(String name, float x, float y, float width, float height, String target, float resetTime) {
		return this.create(name, x + width / 2, y + height / 2, width, height, target, resetTime);
	}
	
	public Button createBL(float x, float y, float width, float height, String target, float resetTime) {
		return this.createBL(null, x, y, width, height, target, resetTime);
	}
}
