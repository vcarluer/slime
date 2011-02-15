package gamers.associate.Slime;

import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class CameraManager {
	
	private CCLayer gameLayer;
	private float levelWidth;
	private float levelHeight;	
	private boolean isCameraOnContinuousMove;
	private CGPoint moveCameraBy;
	private CGRect cameraView;
	private CGPoint virtualCameraPos;
	private GameItem followed;
	
	public CameraManager(CCLayer gameLayer, float levelWidth, float levelHeight, CGPoint levelOrigin) {
		this.gameLayer = gameLayer;
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;		
		CGPoint origin = levelOrigin;
		this.cameraView = CGRect.make(origin, CCDirector.sharedDirector().winSize());
		this.moveCameraBy = new CGPoint();
		this.virtualCameraPos = CGPoint.getZero();
	}
	
	protected void tick(float delta) {
		if (this.isCameraOnContinuousMove) {
			this.moveCameraBy(this.moveCameraBy);
		}
		
		if (this.followed != null) {
			this.centerCameraOn(this.followed.getPosition());
		}
	}
	
	public void follow(GameItem item) {
		this.followed = item;
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
	
	// Always apply scale before calling this method
	/*public void moveCameraBy(CGPoint deltaIn) {
		CGPoint delta = CGPoint.make(- deltaIn.x, - deltaIn.y);
		CGPoint position = this.gameLayer.getPosition();
		
		float scale = this.gameLayer.getScale();		
		
		float maxLeft = CGRect.minX(this.cameraView);
		float left = position.x;
		if (delta.x > 0) {
			if (left + delta.x > maxLeft) {
				delta.x = maxLeft - left;
			}
		}
		
		float minRight = CGRect.maxX(this.cameraView);
		float right = position.x + this.levelWidth * scale;
		if (delta.x < 0) {
			if (right + delta.x < minRight) {
				delta.x = minRight - right;
			}
		}
		
		float maxBottom = CGRect.minY(this.cameraView);
		float bottom = position.y;
		if (delta.y > 0) {
			if (bottom + delta.y > maxBottom) {
				delta.y = maxBottom - bottom;
			}
		}
		
		float minTop = CGRect.maxY(this.cameraView);
		float top = position.y + this.levelHeight * scale;
		if (delta.y < 0) {
			if (top + delta.y < minTop) {
				delta.y = minTop - top;				
			}
		}		
		
		position.x += delta.x;
		position.y += delta.y;
		this.gameLayer.setPosition(position);
		this.virtualCameraPos.set(- position.x, - position.y);
		if (position.x == 0 && position.y == 0) {
			this.isCameraOnContinuousMove = false;
		}
	}*/
	
	public void normalizePosition() {
		float scale = this.gameLayer.getScale();		
		
		CGPoint position = this.gameLayer.getPosition();
		float maxLeft = CGRect.minX(this.cameraView);
		float left = position.x;
		if (left > maxLeft) {
			position.x += maxLeft - left;
		}
		
		float minRight = CGRect.maxX(this.cameraView);
		float right = position.x + this.levelWidth * scale;
		if (right < minRight) {
			position.x += minRight - right;
		}
		
		float maxBottom = CGRect.minY(this.cameraView);
		float bottom = position.y;
		if (bottom > maxBottom) {
			position.y += maxBottom - bottom;
		}
		
		float minTop = CGRect.maxY(this.cameraView);
		float top = position.y + this.levelHeight * scale;
		if (top < minTop) {
			position.y += minTop - top;
		}
				
		this.gameLayer.setPosition(position);
		this.virtualCameraPos.set(- position.x, - position.y);
		if (position.x == 0 && position.y == 0) {
			this.isCameraOnContinuousMove = false;
		}
	}
	
	public void centerCameraOn(CGPoint center) {		
		float scale = this.gameLayer.getScale();
		CGPoint position = CGPoint.make(
				- (center.x * scale - this.cameraView.size.width / 2),
				- (center.y * scale - this.cameraView.size.height / 2));
		this.setLayerPosition(position);
	}
	
	private void setLayerPosition(CGPoint position) {				
		this.gameLayer.setPosition(position);
		this.normalizePosition();
	}
	
	public void setCameraPosition(CGPoint origin) {
		float scale = this.gameLayer.getScale();
		CGPoint position = CGPoint.make(
				- origin.x * scale,
				- origin.y * scale);
		this.setLayerPosition(position);
	}
	
	public void moveCameraBy(CGPoint move) {
		float scale = this.gameLayer.getScale();
		CGPoint position = CGPoint.make(this.gameLayer.getPosition().x, this.gameLayer.getPosition().y);
		position.x += - move.x * scale;
		position.y += - move.y * scale;
		
		this.setLayerPosition(position);
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
		this.normalizePosition();
	}
}
