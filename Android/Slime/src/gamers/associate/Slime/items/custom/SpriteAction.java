package gamers.associate.Slime.items.custom;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class SpriteAction {
	public static int noActionReserved = 0;
	public static int MoveBL = 1;
	public static int FadeIn = 2;
	public static int MoveL = 3;
	public static int MoveB = 4;
	
	private int actionCode;
	private float actionValue;
	private float actionTime;
	private boolean inverse;
	private boolean repeat;	
	private float originalDelay;
	private boolean resetPosition;
	private float delayBefore;
	private CCSprite sprite;
	
	private float originalX;
	private float originalY;
	
	public SpriteAction(int action, float value, float time, boolean inverse, boolean repeat, float originalDelay, boolean resetPosition, float delayBefore) {
		this.actionCode = action;
		this.actionValue = value;
		this.actionTime = time;
		this.inverse = inverse;
		this.repeat = repeat;
		this.originalDelay = originalDelay;
		this.resetPosition = resetPosition;
		this.delayBefore = delayBefore;
	}
	
	public void apply(CCSprite sprite) {
		this.sprite = sprite;
		if (this.sprite != null) {
			this.originalX = sprite.getPosition().x;
			this.originalY = sprite.getPosition().y;
		}
				
		if (this.sprite != null && this.actionCode != noActionReserved) {
			if (actionCode == FadeIn) {
				this.sprite.setOpacity(0);
			}
			
			if (this.originalDelay > 0) {
				CCDelayTime delay = CCDelayTime.action(this.originalDelay);
				CCCallFunc call = CCCallFunc.action(this, "continueAction");
				CCSequence seq = CCSequence.actions(delay, call);
				this.sprite.runAction(seq);
			} else {
				this.continueAction();
			}
		}
	}
	
	public void continueAction() {
		if (this.sprite != null) {
			CCIntervalAction action = null;
			if (actionCode == MoveBL) {
				action = CCMoveBy.action(this.actionTime, CGPoint.ccp(- this.actionValue, -this.actionValue));			
			}
			
			if (actionCode == MoveL) {
				action = CCMoveBy.action(this.actionTime, CGPoint.ccp(- this.actionValue, 0));			
			}
			
			if (actionCode == FadeIn) {
				action = CCFadeIn.action(this.actionTime);			
			}
			
			if (actionCode == MoveB) {
				action = CCMoveBy.action(this.actionTime, CGPoint.ccp(0, - this.actionValue));
			}
					
			if (action != null) {								
				CCIntervalAction actionT1 = action;
				if (this.inverse) {
					actionT1 = CCSequence.actions(action, action.reverse());
				}
				
				CCIntervalAction actionT2 = actionT1;
				if (this.resetPosition) {
					CCMoveTo mt = CCMoveTo.action(0, CGPoint.ccp(this.originalX, this.originalY));
					actionT2 = CCSequence.actions(actionT1, mt);
				}
				
				CCIntervalAction actionT3 = actionT2;
				if (this.delayBefore > 0) {
					CCDelayTime delay = CCDelayTime.action(this.delayBefore);
					actionT3 = CCSequence.actions(delay, actionT2);
				}
				
				CCAction actionT4 = actionT3;
				if (this.repeat) {
					actionT4 = CCRepeatForever.action(actionT3);
				}
				
				sprite.runAction(actionT4);
			}
		}		
	}
	
	public int getActionCode() {
		return this.actionCode;
	}
	
	public float getActionValue() {
		return this.actionValue;
	}
	
	public float getActionTime() {
		return this.actionTime;
	}
	
	public boolean getInverse() {
		return this.inverse;
	}
	
	public boolean getRepeat() {
		return this.repeat;
	}
	
	public float getOriginalDelay() {
		return this.originalDelay;
	}
	
	public boolean getResetPosition() {
		return this.resetPosition;
	}
	
	public float getDelayBefore() {
		return this.delayBefore;
	}
}
