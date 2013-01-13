package gamers.associate.Slime.layers;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.actions.ease.CCEaseElasticOut;
import org.cocos2d.actions.ease.CCEaseIn;
import org.cocos2d.actions.ease.CCEaseOut;
import org.cocos2d.actions.ease.CCEaseSineOut;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class ScrollerLayer extends CCLayer {
	private static final float frictionBase = 2000f;
	private static final int flingCaptureTime = 200;
	private static final int SLIDE_THREASOLD = 50;
	private static final float timeRatio = 5f;
	private CCNode handled;
	private boolean hasMoved;
	private CGPoint tmpPoint;
	private float minScoll;
	private float maxScroll;
	private IScrollable storyLayer;
	
	private List<TouchInfoMini> touchInfos;
	private List<TouchInfoMini> toDelete;
	
	public ScrollerLayer() {
		this.touchInfos = new ArrayList<TouchInfoMini>();
		this.toDelete = new ArrayList<TouchInfoMini>();
		this.setIsTouchEnabled(true);
		this.tmpPoint = CGPoint.zero();
	}
	
	@Override
	public void onEnter() {
		this.touchInfos.clear();
		super.onEnter();
	}

	@Override
	public void onExit() {
		this.touchInfos.clear();
		super.onExit();
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (event.getHistorySize() > 0) {
				if (this.handled != null) {
					this.tmpPoint.x = getHandled().getPosition().x;
					
					float cumulativeDeltaY = 0;
					float previousY = this.touchInfos.get(this.touchInfos.size() - 1).y;
					for(int pos = 0; pos < event.getHistorySize(); pos++) {
						float histY = event.getHistoricalY(pos);
						if (previousY > - 1) {
							cumulativeDeltaY += previousY - histY;
						}
						
						previousY = histY;
					}
					
					if (Math.abs(cumulativeDeltaY) > 5) {
						
						this.tmpPoint.x = getHandled().getPosition().x;
						this.tmpPoint.y = getHandled().getPosition().y + cumulativeDeltaY;
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
				
				TouchInfoMini info = new TouchInfoMini();
				info.time = System.currentTimeMillis();
				info.y = event.getY();
				this.touchInfos.add(info);
				
				this.cleanTouchHistory();
				
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

	protected void cleanTouchHistory() {
		long time = System.currentTimeMillis();
		
		for(TouchInfoMini infoHist : this.touchInfos) {
			if (time - infoHist.time > flingCaptureTime) {
				this.toDelete.add(infoHist);
			}
		}
		
		for(TouchInfoMini infoDel : this.toDelete) {
			this.touchInfos.remove(infoDel);
		}
			
		this.toDelete.clear();
	}

	public CCNode getHandled() {
		return handled;
	}

	public void setHandled(CCNode handled) {
		this.handled = handled;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		this.cleanTouchHistory();
		TouchInfoMini info = new TouchInfoMini();
		info.time = System.currentTimeMillis();
		info.y = event.getY();
		this.touchInfos.add(info);
		
		if (this.touchInfos.size() > 1) {
			long time = System.currentTimeMillis();
			TouchInfoMini firstTouch = this.touchInfos.get(0);
			float gestureDistance = firstTouch.y - event.getY();
			if (Math.abs(gestureDistance) > 5) {
				float gestureTime = (time - firstTouch.time) / 1000f;
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
				
				float deltaDistRef = (Math.abs(targetYRef - getHandled().getPosition().y));
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
				
				float targetY = getHandled().getPosition().y + (targetYDelta * direction);
				this.tmpPoint.x = getHandled().getPosition().x;
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
		
		TouchInfoMini info = new TouchInfoMini();
		info.time = System.currentTimeMillis();
		info.y = event.getY();
		this.touchInfos.add(info);
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
