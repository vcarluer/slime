package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemCocos;
import gamers.associate.SlimeAttack.items.base.ITrigerable;
import gamers.associate.SlimeAttack.items.base.SpriteType;

public class Target extends GameItemCocos implements ITrigerable {	
	public static String Anim_Base = "empty";
	
	public Target(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		this.spriteType = SpriteType.SINGLE_SCALE;
	}

	@Override
	protected String getReferenceAnimationName() {
		return Anim_Base;
	}

	public void trigger(Object source, String data) {		
	}

	@Override
	public void triggerOn(Object source) {
	}

	@Override
	public void triggerOff(Object source) {
	}
}
