package gamers.associate.SlimeAttack.layers;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.actions.ease.CCEaseSineOut;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class ScrollerLayer extends CCLayer {
	private static final int DefaultMinYDistance = 5;
	private static final float frictionBase = 2000f;
	private static final int flingCaptureTime = 200;
	private static final int SLIDE_THREASOLD = 50;
	private int minYDistance;
	private CCNode handled;
	private boolean hasMoved;
	private CGPoint tmpPoint;
	private float minScoll;
	private float maxScroll;
	private IScrollable storyLayer;

//	private List<TouchInfoMini> touchInfos;
//	private List<TouchInfoMini> toDelete;
	
	private long timem2;
	private long timem1;
	private float ym2;
	private float ym1;
	private float yPrevious;
	
	public ScrollerLayer(int minimumYDistance) {
		this.minYDistance = minimumYDistance;
		this.setIsTouchEnabled(true);
		this.tmpPoint = CGPoint.zero();
	}
	
	public ScrollerLayer() {
		this(DefaultMinYDistance);
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
	}

	@Override
	public void onExit() {
//		this.touchInfos.clear();		
		super.onExit();
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (event.getHistorySize() > 0) {
				if (this.handled != null) {
					this.tmpPoint.x = getHandled().getPositionRef().x;
					
					float cumulativeDeltaY = 0;
					float previousY = this.yPrevious;
					for(int pos = 0; pos < event.getHistorySize(); pos++) {
						float histY = event.getHistoricalY(pos);
						if (previousY > - 1) {
							cumulativeDeltaY += previousY - histY;
						}
						
						previousY = histY;
					}
					
					if (Math.abs(cumulativeDeltaY) > this.minYDistance) {
						
						this.tmpPoint.x = getHandled().getPositionRef().x;
						this.tmpPoint.y = getHandled().getPositionRef().y + cumulativeDeltaY;
						if (this.tmpPoint.y > this.maxScroll) {
							this.tmpPoint.y = this.maxScroll;
						}
						
						if (this.tmpPoint.y < this.minScoll) {
							this.tmpPoint.y = this.minScoll;
						}
						
						getHandled().stopAllActions();
						getHandled().setPosition(this.tmpPoint.x, this.tmpPoint.y);
						this.hasMoved = true;
					}
				}
				
				this.yPrevious = event.getY();
				long time = System.currentTimeMillis();
				if (time - timem1 > 100) {
					timem2 = timem1;
					ym2 = ym1;
					timem1 = time;
					ym1 = this.yPrevious;
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
		long time = System.currentTimeMillis();
		if (time - timem1 > 100) {
			timem2 = timem1;
			ym2 = ym1;
		}
		
		if (this.ym2 > -1) {
			float gestureDistance = this.ym2 - event.getY();
			if (Math.abs(gestureDistance) > this.minYDistance) {
				float gestureTime = (time - timem2) / 1000f;
				float gestureVelocityBase = (gestureDistance / gestureTime);
				float gestureVelocity = Math.abs(gestureVelocityBase);
				int direction = 0;
				float targetYRef = 0;
				if (gestureVelocityBase > 0) {
					direction = 1;
					targetYRef = this.maxScroll;
					
				} else {
					direction = -1;
					targetYRef = this.minScoll;
				}
				
				float deltaDistRef = (Math.abs(targetYRef - getHandled().getPositionRef().y));
				float deltaRef =  deltaDistRef / gestureVelocity;
				
				float friction = (gestureTime * frictionBase) / (flingCaptureTime / 1000f);
				float targetYDelta = 0;
				float velo = gestureVelocity;
				while (velo > 0) {
					float delta = friction;
					if (velo < friction) {
						delta = velo;
					}
					
					targetYDelta += velo;
					velo -= delta;
				}
				
				float deltaTime = (targetYDelta * deltaRef) / deltaDistRef;
				
				float targetY = getHandled().getPositionRef().y + (targetYDelta * direction);
				this.tmpPoint.x = getHandled().getPositionRef().x;
				this.tmpPoint.y = targetY;
				
				boolean recalc = false;
				if (this.tmpPoint.y > this.maxScroll) {
					this.tmpPoint.y = this.maxScroll;
					recalc = true;
				}
				
				if (this.tmpPoint.y < this.minScoll) {
					this.tmpPoint.y = this.minScoll;
					recalc = true;
				}
				
				if (recalc) {
					deltaTime = deltaRef;
				}
				
				CCMoveTo moveTo = CCMoveTo.action(deltaTime, this.tmpPoint);
				CCEaseSineOut ease = CCEaseSineOut.action(moveTo);
				getHandled().stopAllActions();
				getHandled().runAction(ease);
			}
		}
		
		return true;
		
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		this.hasMoved = false;
		
		getHandled().stopAllActions();
		this.yPrevious = event.getY();
		this.ym1 = this.yPrevious;
		this.timem1 = System.currentTimeMillis();
		this.ym2 = -1;
		this.timem2 = 0;
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
