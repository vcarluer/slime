package gamers.associate.Slime;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class CameraManager {
	
	private float minScale;
	private float maxScale;
	private float zoomSpeed;
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
	private CGPoint levelOrigin;
	
	public CameraManager(CCLayer gameLayer) {
		this.gameLayer = gameLayer;
				
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
		
	public void normalizePosition() {
		float scale = this.gameLayer.getScale();		
		boolean isNormalized = false;
		CGPoint position = this.gameLayer.getPosition();
		
		float maxLeft = CGRect.minX(this.cameraView);
		float left = position.x;
		if (left > maxLeft) {
			position.x += maxLeft - left;
			isNormalized = true;
		}
		
		float minRight = CGRect.maxX(this.cameraView);
		float right = position.x + this.levelWidth * scale;
		if (right < minRight) {
			position.x += minRight - right;
			isNormalized = true;
		}
		
		float maxBottom = CGRect.minY(this.cameraView);
		float bottom = position.y;
		if (bottom > maxBottom) {
			position.y += maxBottom - bottom;
			isNormalized = true;
		}
		
		float minTop = CGRect.maxY(this.cameraView);
		float top = position.y + this.levelHeight * scale;
		if (top < minTop) {
			position.y += minTop - top;
			isNormalized = true;
		}						
		
		this.gameLayer.setPosition(position);
		this.virtualCameraPos.set(- position.x, - position.y);
		if (isNormalized) {
			this.isCameraOnContinuousMove = false;
		}
	}
	
	public void centerCameraOn(CGPoint center) {		
		this.keepPointAt(center, CGPoint.make(this.cameraView.size.width / 2, this.cameraView.size.height / 2));
	}
	
	public void keepPointAt(CGPoint gamePoint, CGPoint screenPin) {
		float scale = this.gameLayer.getScale();
		CGPoint position = CGPoint.make(
				- (gamePoint.x * scale - screenPin.x),
				- (gamePoint.y * scale - screenPin.y));
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
		float zoom = (float) (zoomDelta * maxScale / max) * this.zoomSpeed;
		this.zoomCameraBy(zoom);
	}
	
	private CGPoint zoomAnchor;
	private CGPoint zoomScreenPin;
	
	public void setZoomPoint(CGPoint zoomPoint) {
		this.zoomScreenPin = CGPoint.make(zoomPoint.x, zoomPoint.y);
		float scale = this.gameLayer.getScale();
		CGPoint zoomScaled = CGPoint.zero();
		zoomScaled.x =  (zoomPoint.x - this.gameLayer.getPosition().x) / scale; 
		zoomScaled.y = (zoomPoint.y - this.gameLayer.getPosition().y) / scale;
		
		this.zoomAnchor = zoomScaled;
	}
	
	public void zoomCameraBy(float zoomDelta) {		
		float scale = this.gameLayer.getScale() + zoomDelta;
		this.zoomCameraTo(scale);
	}
	
	public void zoomCameraTo(float zoomValue) {
		if (zoomValue <= minScale) {
			zoomValue = minScale;
		}
		
		if (zoomValue >= maxScale) {
			zoomValue = maxScale;
		}
		
		this.gameLayer.setScale(zoomValue);
		this.keepPointAt(this.zoomAnchor, this.zoomScreenPin);
		this.normalizePosition();
	}
	
	public void setCameraView() {		
		CGPoint origin = this.levelOrigin;		
		// Default view is windows size
		this.cameraView = CGRect.make(origin, CCDirector.sharedDirector().winSize());
		
		// By default camera can not be unzoom more than the most little size
		this.zoomAnchor = CGPoint.getZero();
		this.zoomScreenPin = CGPoint.getZero();
		float minScaleW = 1 / (this.levelWidth / CGRect.width(this.cameraView));
		float minScaleH = 1 / (this.levelHeight / CGRect.height(this.cameraView));
		this.minScale = Math.max(minScaleW, minScaleH);
		this.maxScale = 2.0f;
		this.zoomSpeed = 3.0f;
		// To take into account new limits
		this.zoomCameraBy(0);
		this.normalizePosition();
	}
	
	public void attachLevel(float levelWidth, float levelHeight, CGPoint levelOrigin) {
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;				
		this.levelOrigin = levelOrigin;
	}
}
