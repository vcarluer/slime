package gamers.associate.Slime;

import java.util.ArrayList;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.LevelFactory"
 */
public class LevelLayer extends CCLayer {
	
	/**
	 * @uml.property  name="level"
	 * @uml.associationEnd  
	 */
	private Level level;
	private ArrayList<TouchInfo> touchList;
	private boolean isZoomAction;
	private float lastDistance;
	private float lastZoomDelta;
	
	public LevelLayer(Level level) {
		super();
		this.level = level;
		this.setIsTouchEnabled(true);
		this.touchList = new ArrayList<TouchInfo>();		
	}
	
	private UpdateCallback tickCallback = new UpdateCallback() {
		
		@Override
		public void update(float d) {
			tick(d);
		}
	};
	
	@Override
	public void onEnter() {
		super.onEnter();
		
		// start ticking (for physics simulation)
		schedule(tickCallback);
	}

	@Override
	public void onExit() {
		super.onExit();
		
		// stop ticking (for physics simulation)			
		unschedule(tickCallback);
	}
		
	public synchronized void tick(float delta) {
    	this.level.tick(delta);
    }

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#ccTouchesMoved(android.view.MotionEvent)
	 */
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		 for (int i = 0; i < event.getPointerCount(); i++) {			 
			 TouchInfo touch = this.getTouch(event, i);
			 if (touch != null) { 
				touch.setMoving(true);	
				touch.getLastMoveDelta().x = touch.getLastMoveReference().x - event.getX(touch.getPointerId());
				touch.getLastMoveDelta().y = event.getY() - touch.getLastMoveReference().y;
				touch.getLastMoveReference().x = event.getX(touch.getPointerId());
				touch.getLastMoveReference().y = event.getY(touch.getPointerId());
				touch.setLastMoveTime(event.getEventTime());
			 }
		 }
		
		if (this.touchList.size() == 1) {
			TouchInfo touch = this.getTouch(event, 0);
			this.level.getCameraManager().moveCameraBy(touch.getLastMoveDelta());
		}
		
		if (this.isZoomAction) {
			CGPoint touch1Ref =this.touchList.get(0).getLastMoveReference(); 
			CGPoint touch2Ref =this.touchList.get(1).getLastMoveReference();
			float distance = CGPoint.ccpDistance(
					touch1Ref, 
					touch2Ref);			
			
			this.lastZoomDelta = distance - this.lastDistance;
			this.lastDistance = distance;
			
			this.getCameraManager().zoomCameraByScreenRatio(this.lastZoomDelta);
		}			
		
        return CCTouchDispatcher.kEventHandled;
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#ccTouchesEnded(android.view.MotionEvent)
	 */
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {		
		TouchInfo touch = this.touchList.get(this.getPId(event));
		if (!touch.isMoving()) {
			if (touch.getPointerId() == 0) {
				this.level.SpawnSlime();
			}
		}
		else {						
			if (!this.isZoomAction) {
				if (event.getEventTime() - touch.getLastMoveTime() < 300) {					
					this.getCameraManager().continuousMoveCameraBy(touch.getLastMoveDelta());
				}
								
				touch.setMoving(false);
			}			
		}
		
		if (this.isZoomAction) {
			this.isZoomAction = false;
			this.lastDistance = 0f;
			this.lastZoomDelta = 0f;
		}
		
		this.touchList.remove(touch);
        return CCTouchDispatcher.kEventHandled;
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#ccTouchesBegan(android.view.MotionEvent)
	 */
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {				
		TouchInfo touch = new TouchInfo(this.getPId(event));		
		touch.getMoveBeganAt().x = event.getX(touch.getPointerId());
		touch.getMoveBeganAt().y = event.getY(touch.getPointerId());
		touch.getLastMoveReference().x = event.getX(touch.getPointerId());
		touch.getLastMoveReference().y = event.getY(touch.getPointerId());
		this.touchList.add(touch);
		this.getCameraManager().stopContinousMoving();
		
		if (this.touchList.size() == 2) {
			this.isZoomAction = true;	
			this.lastZoomDelta = 0f;
			CGPoint touch1Ref = this.touchList.get(0).getLastMoveReference();
			CGPoint touch2Ref = this.touchList.get(1).getLastMoveReference();
			this.lastDistance = CGPoint.ccpDistance(
					touch1Ref,
					touch2Ref);
			
			CGPoint midPoint = CGPoint.ccpMidpoint(touch1Ref, touch2Ref);
			CGPoint anchorZoom = CGPoint.make(midPoint.x, CCDirector.sharedDirector().winSize().height - midPoint.y);
			this.getCameraManager().setZoomPoint(anchorZoom);
		}
		else {
			this.isZoomAction = false;
		}
		
		return CCTouchDispatcher.kEventHandled;		
	}	
	
	private CameraManager getCameraManager() {
		return this.level.getCameraManager();
	}
	
	private int getPId(MotionEvent event) {
		int pId = event.getAction() >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		return pId;
	}
	
	private TouchInfo getTouch(MotionEvent event, int idx) {
		TouchInfo returnTouch = null;
		
		if (idx <= event.getPointerCount()) {
			int pId = event.getPointerId(idx);
			for	(TouchInfo touch : this.touchList) {
				if (touch.getPointerId() == pId) {
					returnTouch = touch;
					break;
				}
			}
		}
		
		return returnTouch;
	}
	 
	 // Test
	 /*public void resetWon() {
		 this.level.resetWon();
	 }*/
}
