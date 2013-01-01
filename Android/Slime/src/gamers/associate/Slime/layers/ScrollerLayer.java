package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.SlimeFactory;

import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class ScrollerLayer extends CCLayer {
	private static final int SCROLL_SPEED = 3;
	private CCNode handled;
	private boolean hasMoved;
	private CGPoint tmpPoint;
	private float lastDelta;
	private float minScoll;
	private float maxScroll;
	
	public ScrollerLayer() {
		this.setIsTouchEnabled(true);
		this.tmpPoint = CGPoint.zero();
	}
	
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		if (this.handled != null) {
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				if (event.getHistorySize() > 0) {
					this.lastDelta = - (event.getY() - event.getHistoricalY(0)) * (SCROLL_SPEED * SlimeFactory.SGSDensity);
					if (Math.abs(this.lastDelta) > 5) {
						this.tmpPoint.x = getHandled().getPosition().x;
						this.tmpPoint.y = getHandled().getPosition().y + this.lastDelta;
						if (this.tmpPoint.y > this.maxScroll) {
							this.tmpPoint.y = this.maxScroll;
						}
						
						if (this.tmpPoint.y < this.minScoll) {
							this.tmpPoint.y = this.minScoll;
						}
						
						getHandled().stopAllActions();					
						CCMoveTo moveTo = CCMoveTo.action(0, this.tmpPoint);
						getHandled().runAction(moveTo);
						this.hasMoved = true;
					}					
				}			
			}			    

			return true;
		}
		
		return false;
	}

	public CCNode getHandled() {
		return handled;
	}

	public void setHandled(CCNode handled) {
		this.handled = handled;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if (this.hasMoved) {
//			this.tmpPoint.x = 0;
//			this.tmpPoint.y = - this.lastDelta / 2f;
//			if (getHandled().getPosition().y + this.tmpPoint.y > this.maxScroll) {
//				this.tmpPoint.y = 0;
//			}
//			
//			if (getHandled().getPosition().y + this.tmpPoint.y < this.minScoll) {
//				this.tmpPoint.y = 0;
//			}
//			
//			CCMoveBy moveBy = CCMoveBy.action(0.5f, this.tmpPoint);
//			getHandled().stopAllActions();
//			getHandled().runAction(moveBy);
			return true;
		} else {
			return false;
		}
		
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		this.hasMoved = false;
		return true;
	}

	public void setLimits(float min, float max) {
		this.minScoll = min;
		this.maxScroll = max;
	}
	
	public boolean hasMoved() {
		return this.hasMoved;
	}
}
