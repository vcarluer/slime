package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.SlimeFactory;

import org.cocos2d.actions.ease.CCEaseOut;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class ScrollerLayer extends CCLayer {
	private static final int SLIDE_THREASOLD = 50;
	private static final int SCROLL_SPEED = 3;
	private CCNode handled;
	private boolean hasMoved;
	private CGPoint tmpPoint;
	private float minScoll;
	private float maxScroll;
	private IScrollable storyLayer;
	private float previousY;
	private long lastTouch;
	
	public ScrollerLayer() {
		this.setIsTouchEnabled(true);
		this.tmpPoint = CGPoint.zero();
	}
	
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (event.getHistorySize() > 0) {
				if (this.handled != null) {
					this.lastTouch = System.currentTimeMillis();
					float cumulativeY = 0;
					for(int pos = 0; pos < event.getHistorySize(); pos++) {
						float histY = event.getHistoricalY(pos);
						if (this.previousY > - 1) {
							cumulativeY += this.previousY - histY;
						}
						
						this.previousY = histY;
					}
					
					if (Math.abs(cumulativeY) > 5) {
						this.tmpPoint.x = getHandled().getPosition().x;
						this.tmpPoint.y = getHandled().getPosition().y + cumulativeY;
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
				
				float deltaX = event.getX() - event.getHistoricalX(0);
				if (deltaX < - SLIDE_THREASOLD) {
					this.storyLayer.toRight(this);
					this.hasMoved = true;
					return true;
				}
				
				if (deltaX > SLIDE_THREASOLD) {
					this.storyLayer.toLeft(this);
					this.hasMoved = true;
					return true;
				}
			}
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
//		float gestureTime = (System.currentTimeMillis() - this.lastTouch) / 1000f;
//		float gestureDistance = this.previousY - event.getY();
//		float gestureVelocity = gestureDistance / gestureTime;
		
//		final float distanceTimeFactor = 0.5f;
//		float gestureTime = (System.currentTimeMillis() - this.startTouch) / 1000f;
//		if (gestureTime > 0) {
//			float gestureDistance = this.startY - event.getY();
//			float gestureVelocity = gestureDistance / gestureTime;
//			
//			this.tmpPoint.x =getHandled().getPosition().x;
//			this.tmpPoint.y = getHandled().getPosition().y + (gestureVelocity * distanceTimeFactor);
//			if (this.tmpPoint.y > this.maxScroll) {
//				this.tmpPoint.y = this.maxScroll;
//			}
//			
//			if (this.tmpPoint.y < this.minScoll) {
//				this.tmpPoint.y = this.minScoll;
//			}
//				
//			CCMoveTo moveTo = CCMoveTo.action(distanceTimeFactor, this.tmpPoint);
//			CCEaseOut ease = CCEaseOut.action(moveTo, 10.0f);
//			getHandled().stopAllActions();
//			getHandled().runAction(ease);			
//			
//			this.hasMoved = false;
//			return true;
//		}
//		
//		this.hasMoved = false;
//		return false;
		return true;
		
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		this.hasMoved = false;
		this.lastTouch = System.currentTimeMillis();
		this.previousY = event.getY();
		return true;
	}

	public void setLimits(float min, float max) {
		this.minScoll = min;
		this.maxScroll = max;
	}
	
	public boolean hasMoved() {
		return this.hasMoved;
	}

	public IScrollable getStoryLayer() {
		return storyLayer;
	}

	public void setStoryLayer(IScrollable storyLayer) {
		this.storyLayer = storyLayer;
	}
}
