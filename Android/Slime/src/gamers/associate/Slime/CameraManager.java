package gamers.associate.Slime;

import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class CameraManager {
	
	private float minScale;
	private float maxScale;
	private float screenW2 = CCDirector.sharedDirector().winSize().width * CCDirector.sharedDirector().winSize().width;
	private float screenH2 = CCDirector.sharedDirector().winSize().height * CCDirector.sharedDirector().winSize().height;
	
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
		
		this.minScale = 1 / (this.levelWidth / CGRect.width(this.cameraView));
		this.maxScale = 2.0f;
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
		
	public void normalizePosition() {
		float scale = this.gameLayer.getScale();		
		
		CGPoint position = this.gameLayer.getPosition();
		boolean isLeftNormalized;
		boolean isRightNormalized;
		boolean isTopNormalized;
		boolean isBottomNormalized;
		
		float maxLeft = CGRect.minX(this.cameraView);
		float left = position.x;
		if (left > maxLeft) {
			position.x += maxLeft - left;
			isLeftNormalized = true;
		}
		
		float minRight = CGRect.maxX(this.cameraView);
		float right = position.x + this.levelWidth * scale;
		if (right < minRight) {
			position.x += minRight - right;
			isRightNormalized = true;
		}
		
		float maxBottom = CGRect.minY(this.cameraView);
		float bottom = position.y;
		if (bottom > maxBottom) {
			position.y += maxBottom - bottom;
			isBottomNormalized = true;
		}
		
		float minTop = CGRect.maxY(this.cameraView);
		float top = position.y + this.levelHeight * scale;
		if (top < minTop) {
			position.y += minTop - top;
			isTopNormalized = true;
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
	
	public void zoomCameraByScreenRatio(float zoomDelta) {		
		double max = Math.sqrt(screenW2 + screenH2);
		float zoom = (float) (zoomDelta * maxScale / max);
		this.zoomCameraBy(zoom);
	}
	
	public void zoomCameraBy(float zoomDelta) {		
		float scale = this.gameLayer.getScale() + zoomDelta;
		if (scale <= minScale) {
			scale = minScale;
		}
		
		if (scale >= maxScale) {
			scale = maxScale;
		}
		
		this.gameLayer.setScale(scale);
		this.normalizePosition();
	}
}
