package gamers.associate.Slime;

import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

public class CameraManager {
	
	private CCLayer gameLayer;
	private float maxWidth;
	private float maxHeight;
	private boolean isCameraOnContinuousMove;
	private CGPoint moveCameraBy;
	
	public CameraManager(CCLayer gameLayer, float maxWidth, float maxHeight) {
		this.gameLayer = gameLayer;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		
		this.moveCameraBy = new CGPoint();
	}
	
	protected void tick(float delta) {
		if (this.isCameraOnContinuousMove) {
			this.moveCameraBy(this.moveCameraBy);
		}
	}
	
	public void cameraFollow(GameItem item) {		
		CCScaleTo scale = CCScaleTo.action(1, 1.5f);
		this.gameLayer.runAction(scale);
		//this.backgroundLayer.runAction(scale);
		
		/*CCCamera cam = this.levelLayer.getCamera();
		cam.setEye(0, 0, 1000f);*/
		
		/*CCFollow follow = CCFollow.action(item.getSprite());			
		this.levelLayer.runAction(follow);	*/	
	}
	
	public void moveCameraBy(CGPoint delta) {
		CGPoint position = this.gameLayer.getPosition();
		float maxLeft = - (this.maxWidth / 2) * this.gameLayer.getScale();				
		float left = - position.x - CCDirector.sharedDirector().winSize().width / 2;		
		if (delta.x > 0) {
			if ((left - delta.x) < maxLeft) {
				delta.x = left - maxLeft;
			}
		}
		
		float maxRight = this.maxWidth / 2 * this.gameLayer.getScale();				
		float right = - position.x + CCDirector.sharedDirector().winSize().width / 2;		
		if (delta.x < 0) {
			if ((right - delta.x) > maxRight) {
				delta.x = right - maxRight;
			}
		}
		
		float maxTop = this.maxHeight / 2 * this.gameLayer.getScale();
		float top = position.y + CCDirector.sharedDirector().winSize().height / 2;
		if (delta.y > 0) {
			if ((top + delta.y) > maxTop) {
				delta.y = maxTop - top;
			}
		}
		
		float maxBottom = - this.maxHeight / 2 * this.gameLayer.getScale();
		float bottom = position.y - CCDirector.sharedDirector().winSize().height / 2;
		if (delta.y < 0) {
			if ((bottom + delta.y) < maxBottom) {
				delta.y = maxBottom - bottom;
			}
		}
		
		position.x += delta.x;
		position.y += delta.y;
		this.gameLayer.setPosition(position);		
	}
	
	public void continuousMoveCameraBy(CGPoint delta) {
		// TODO: must be fix based on time (/sec)
		float maxMovePos = 10f;
		float maxMoveNeg = -10f;
		float minMove = 1.0f;
		
		if (Math.abs(delta.x) > minMove) {
			if (delta.x > maxMovePos) delta.x = maxMovePos;
			if (delta.x < maxMoveNeg) delta.x = maxMoveNeg;
		}
		else {
			delta.x = 0f;		
		}
		
		if (Math.abs(delta.y) > minMove) {
			if (delta.y > maxMovePos) delta.y = maxMovePos;
			if (delta.y < maxMoveNeg) delta.y = maxMoveNeg;		
		}
		else {
			delta.y = 0f;
		}
		
		this.moveCameraBy.x = delta.x;
		this.moveCameraBy.y = delta.y;
		this.isCameraOnContinuousMove = true;
	}
	
	public void stopContinousMoving() {
		this.isCameraOnContinuousMove = false;
	}
	
	public void zoomCameraBy(float zoomDelta) {
		float scale = this.gameLayer.getScale() + zoomDelta;
		this.gameLayer.setScale(scale);
	}
}
