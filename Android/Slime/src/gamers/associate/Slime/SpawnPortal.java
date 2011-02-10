package gamers.associate.Slime;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

public class SpawnPortal extends GameItem {
	public static String Anim_Spawn_Portal = "blueportal";		
	
	public SpawnPortal(CCNode node, float x, float y) {
		super(node, x, y);		
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
		Slimy slimy = SlimyFactory.create(this.position.x, this.position.y);
		slimy.fall();
		return slimy;
	}
}
