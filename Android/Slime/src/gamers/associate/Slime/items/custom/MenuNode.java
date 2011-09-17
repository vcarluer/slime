package gamers.associate.Slime.items.custom;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;

import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.SpriteType;

public class MenuNode extends GameItemCocos {
	public static String Anim_Node = "blueportal";
	
	private static float Default_Width = 32f;
	private static float Default_Height = 10f;
	
	public MenuNode(float x, float y, float width, float height) {
		super(x, y, width, height);		
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;
		}
		
		this.spriteType = SpriteType.ANIM_SCALE;
	}
	
	@Override
	protected String getReferenceAnimationName() {
		return MenuNode.Anim_Node;
	}
	
	public void createPortal() {
		CCAction animate = CCRepeatForever.action(CCAnimate.action(this.animationList.get(Anim_Node), false));				
		this.sprite.runAction(animate);		
	}
}
