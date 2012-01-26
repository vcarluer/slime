package gamers.associate.Slime.items.custom;


import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

public class SpawnPortal extends GameItemCocos {
	public static String Anim_Spawn_Portal = "teleporter-blue-portal";		
	
	public SpawnPortal(float x, float y, float width, float height) {
		super(x, y, width, height);	
		
		this.spriteType = SpriteType.ANIM_SCALE;
	}
	
	public void createPortal() {
		CCAction animate = CCRepeatForever.action(CCAnimate.action(this.animationList.get(Anim_Spawn_Portal), false));				
		this.sprite.runAction(animate);		
	}
	
	public void MovePortalInLine(float moveBy, float speed) {
		CGPoint moveR = new CGPoint();
		moveR.x = moveBy;
		moveR.y = 0;
		CGPoint moveL = new CGPoint();
		moveL.x = -moveBy;
		moveL.y = 0;
		CCMoveBy moveRight = CCMoveBy.action(speed / 2, moveR);
		CCMoveBy moveRightReverse = moveRight.reverse();
		CCMoveBy moveLeft = CCMoveBy.action(speed / 2, moveL);
		CCMoveBy moveLeftReverse = moveLeft.reverse();
		CCAction moveSeq = CCRepeatForever.action(CCSequence.actions(moveRight, moveRightReverse, moveLeft, moveLeftReverse));		
		this.sprite.runAction(moveSeq);
	}
	
	public GameItem spawn() {
		Slimy slimy = SlimeFactory.Slimy.create(this.position.x, this.position.y, 1.5f);
//		if (slimy != null) {			
//			slimy.fall();
//		}
		
		return slimy;
	}
	
	@Override
	protected String getReferenceAnimationName() {
		return SpawnPortal.Anim_Spawn_Portal;
	}
}
