package gamers.associate.SlimeAttack.items.custom;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;

import gamers.associate.SlimeAttack.items.base.GameItemCocos;
import gamers.associate.SlimeAttack.items.base.SpriteType;

public class Gate extends GameItemCocos {
	public static String Anim_Closed = "gate-closed";
	public static String Anim_Opened = "gate-opened";
	public static String Anim_Opening = "gate-opening";	
	
	private static float Default_Width = 31f;
	private static float Default_Height = 31f;
	private CCAction waitAction;
	
	public Gate(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.spriteType = SpriteType.ANIM_SCALE;
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;
		}
	}

	@Override
	protected String getReferenceAnimationName() {
		return Anim_Closed;
	}

	public void open() {
		if (this.waitAction != null) {
			this.sprite.stopAction(this.waitAction);
		}
		
		CCAnimate anim = CCAnimate.action(this.animationList.get(Anim_Opening), false);
		CCCallFunc call = CCCallFunc.action(this, "opened");
		CCSequence seq = CCSequence.actions(anim, call);
		this.sprite.runAction(seq);
	}	
	
	public void opened() {
		CCAnimate anim = CCAnimate.action(this.animationList.get(Anim_Opened), false);
		CCRepeatForever repeat = CCRepeatForever.action(anim);
		if (this.waitAction != null) {
			this.sprite.stopAction(this.waitAction);
		}
		
		this.waitAction = repeat;
		this.sprite.runAction(this.waitAction);
	}
	
	public void closed() {		
		CCAnimate anim = CCAnimate.action(this.animationList.get(Anim_Closed), false);
		CCRepeatForever repeat = CCRepeatForever.action(anim);
		if (this.waitAction != null) {
			this.sprite.stopAction(this.waitAction);
		}
		
		this.waitAction = repeat;
		this.sprite.runAction(this.waitAction);
	}
}
