package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.CameraManager;
import gamers.associate.Slime.game.Level;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

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
		this.level.activate();
		//this.level.setStartCamera();
		
//		if(this.level.getStartItem() != null) {						
//			this.level.getCameraManager().zoomInterpolateTo(this.level.getStartItem(), 1.0f, 1.0f);
//			this.level.getCameraManager().follow(this.level.getStartItem());
//		}
	}

	@Override
	public void onExit() {
		super.onExit();
		
		// stop ticking (for physics simulation)			
		unschedule(tickCallback);
		this.level.desactivate();
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
				touch.getLastMoveDelta().y = touch.getLastMoveReference().y - this.getGameY(event.getY(touch.getPointerId()));
				touch.getLastMoveReference().x = event.getX(touch.getPointerId());
				touch.getLastMoveReference().y = this.getGameY(event.getY(touch.getPointerId()));
				touch.setLastMoveTime(event.getEventTime());
			 }
		 }
				 
		if (this.touchList.size() == 1) {
			TouchInfo touch = this.getTouch(event, 0);
			if (!this.level.moveSelection(this.getGamePoint(touch))) {				
				this.level.getCameraManager().moveCameraBy(touch.getLastMoveDelta());
			}								
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
		TouchInfo touch = this.getTouch(event);
		if (touch != null) {
			/*if (!touch.isMoving()) {*/
				if (touch.getPointerId() == 0) {
					/*this.level.spawnSlime(
							CGPoint.make(
									event.getX(touch.getPointerId()), 
									this.getGameY(event.getY(touch.getPointerId()))
									)
								);
					this.level.getSpawnCannon().unselect();*/
					
					CGPoint deltaMove = CGPoint.zero();
					deltaMove.x = event.getX(touch.getPointerId()) - touch.getFirstMoveReference().x;
					deltaMove.y = this.getGameY(event.getY(touch.getPointerId())) - touch.getFirstMoveReference().y;					
										
					if (CGPoint.ccpLength(deltaMove) < 5) {
						this.level.simpleSelect();
					}
					else {
						CGPoint touchPoint = CGPoint.make(
								event.getX(touch.getPointerId()), 
								this.getGameY(event.getY(touch.getPointerId())));					
											
						this.level.activateSelection(this.getGamePoint(touchPoint));					
					}
				}
			/*}*/
			/*else {						
				if (!this.isZoomAction) {*/
					/*if (event.getEventTime() - touch.getLastMoveTime() < 300) {					
						this.getCameraManager().continuousMoveCameraBy(touch.getLastMoveDelta());
					}*/
			/*	}		*/	
				
				touch.setMoving(false);
			/*}*/
			
			if (this.isZoomAction) {
				this.isZoomAction = false;
				this.lastDistance = 0f;
				this.lastZoomDelta = 0f;
			}
			
			this.touchList.remove(touch);
		}				
		
        return CCTouchDispatcher.kEventHandled;
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#ccTouchesBegan(android.view.MotionEvent)
	 */
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {	
		int pid = this.getPId(event);		
		TouchInfo touch = new TouchInfo(pid);		
		touch.getMoveBeganAt().x = event.getX(touch.getPointerId());
		touch.getMoveBeganAt().y = this.getGameY(event.getY(touch.getPointerId()));
		float x = event.getX(touch.getPointerId());
		float y = this.getGameY(event.getY(touch.getPointerId())); 		
		touch.getLastMoveReference().x = x;
		touch.getLastMoveReference().y = y;
		touch.getFirstMoveReference().x = x;
		touch.getFirstMoveReference().y = y;
		touch.setLastMoveTime(event.getEventTime());
		touch.setFirstMoveTime(event.getEventTime());		
		this.touchList.add(touch);
		this.getCameraManager().stopContinousMoving();
		this.level.getCameraManager().cancelFollow();
		this.level.getCameraManager().cancelActions();
		
		if (this.touchList.size() == 2) {
			this.isZoomAction = true;	
			this.lastZoomDelta = 0f;
			CGPoint touch1Ref = this.touchList.get(0).getLastMoveReference();
			CGPoint touch2Ref = this.touchList.get(1).getLastMoveReference();
			this.lastDistance = CGPoint.ccpDistance(
					touch1Ref,
					touch2Ref);
			
			CGPoint midPoint = CGPoint.ccpMidpoint(touch1Ref, touch2Ref);			
			this.getCameraManager().setZoomPoint(midPoint);
			this.level.unselectCurrent();
		}
		else {
			this.isZoomAction = false;					
			this.level.trySelect(this.getGamePoint(touch));
		}		
		
		return CCTouchDispatcher.kEventHandled;		
	}	
	
	private CGPoint getGamePoint(TouchInfo touch) {
		return this.getGamePoint(touch.getLastMoveReference());
	}
	
	private CGPoint getGamePoint(CGPoint screenPoint) {
		return Level.currentLevel.getCameraManager().getGamePoint(screenPoint);
	}
	
	private CameraManager getCameraManager() {
		return this.level.getCameraManager();
	}
	
	private int getPId(MotionEvent event) {		
		// int pId = event.getPointerId(event.getActionIndex());		
		int pId = event.getPointerId(this.getPointerIndex(event));
		return pId;
	}
	
	private int getPointerIndex(MotionEvent event) {
		int action = event.getAction();
		final int pointerIndex = (action & MotionEvent.ACTION_POINTER_ID_MASK) 
        >> MotionEvent.ACTION_POINTER_ID_SHIFT;
		 return pointerIndex;
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
	 
	private TouchInfo getTouch(MotionEvent event) {
		TouchInfo returnTouch = null;
				
		// int pId = event.getPointerId(event.getActionIndex());
		int pId = event.getPointerId(this.getPointerIndex(event));
		for	(TouchInfo touch : this.touchList) {
			if (touch.getPointerId() == pId) {
				returnTouch = touch;
				break;
			}
		}
		
		return returnTouch;
	}
	
	private float getGameY(float touchY) {
		return CCDirector.sharedDirector().winSize().height - touchY;
	}
	
	 // Test
	 /*public void resetWon() {
		 this.level.resetWon();
	 }*/
	
	public void reset() {
		this.touchList.clear();
	}
}
