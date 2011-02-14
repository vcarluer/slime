package gamers.associate.Slime;

import java.util.HashMap;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
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
	private HashMap<Integer, TouchInfo> touchList;
	private boolean isZoomAction;
	private float lastDistance;
	private float lastZoomDelta;
	
	public LevelLayer(Level level) {
		super();
		this.level = level;
		this.setIsTouchEnabled(true);
		this.touchList = new HashMap<Integer, TouchInfo>();		
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
		TouchInfo touch = this.touchList.get(event.getPointerId(event.getActionIndex()));
		
		touch.setMoving(true);	
		touch.getLastMoveDelta().x = event.getX() - touch.getLastMoveReference().x;
		touch.getLastMoveDelta().y = touch.getLastMoveReference().y - event.getY();
		touch.getLastMoveReference().x = event.getX();
		touch.getLastMoveReference().y = event.getY();
		touch.setLastMoveTime(event.getEventTime());
		
		if (this.touchList.size() == 1) {
			this.level.getCameraManager().moveCameraBy(touch.getLastMoveDelta());
		}
		
		if (this.isZoomAction) {
			float distance = CGPoint.ccpDistance(
					this.touchList.get(0).getLastMoveReference(), 
					this.touchList.get(1).getLastMoveReference());
			
			this.lastZoomDelta = distance - this.lastDistance;
			this.lastDistance = distance;
			
			this.getCameraManager().zoomCameraBy(this.lastZoomDelta);
		}			
		
        return CCTouchDispatcher.kEventHandled;
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#ccTouchesEnded(android.view.MotionEvent)
	 */
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {		
		TouchInfo touch = this.touchList.get(event.getPointerId(event.getActionIndex()));
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
		}
		
		this.touchList.remove(touch.getPointerId());
        return CCTouchDispatcher.kEventHandled;
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#ccTouchesBegan(android.view.MotionEvent)
	 */
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {				
		TouchInfo touch = new TouchInfo(event.getPointerId(event.getActionIndex()));		
		touch.getMoveBeganAt().x = event.getX();
		touch.getMoveBeganAt().y = event.getY();
		touch.getLastMoveReference().x = event.getX();
		touch.getLastMoveReference().y = event.getY();
		this.touchList.put(touch.getPointerId(), touch);
		this.getCameraManager().stopContinousMoving();
		
		if (this.touchList.size() == 2) {
			this.isZoomAction = true;	
			this.lastZoomDelta = 0f;
			this.lastDistance = CGPoint.ccpDistance(this.touchList.get(0).getLastMoveReference(), touch.getLastMoveReference());
		}
		else {
			this.isZoomAction = false;
		}
		
		return CCTouchDispatcher.kEventHandled;		
	}	
	
	private CameraManager getCameraManager() {
		return this.level.getCameraManager();
	}
	 
	 // Test
	 /*public void resetWon() {
		 this.level.resetWon();
	 }*/
}
