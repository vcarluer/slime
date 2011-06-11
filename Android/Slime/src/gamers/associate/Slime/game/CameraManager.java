package gamers.associate.Slime.game;

import javax.microedition.khronos.opengles.GL10;

import gamers.associate.Slime.items.base.GameItem;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class CameraManager {
	
	private float minScale;
	private float maxScale;
	private float zoomSpeed;
	private float currentZoom;
	private float minFollowScale;
	private boolean followZoom;
	private float screenW2 = CCDirector.sharedDirector().winSize().width * CCDirector.sharedDirector().winSize().width;
	private float screenH2 = CCDirector.sharedDirector().winSize().height * CCDirector.sharedDirector().winSize().height;
	
	private CCLayer gameLayer;
	private float levelWidth;
	private float levelHeight;	
	private boolean isCameraOnContinuousMove;
	private CGPoint moveCameraBy;
	private CGRect screenView;
	private CGRect virtualCamera;
	private GameItem followed;	
	private CGPoint levelOrigin;	
	private float cameraMargin;
	private CGRect margeRect;
	
	public CameraManager(CCLayer gameLayer) {
		this.gameLayer = gameLayer;
				
		this.moveCameraBy = new CGPoint();
		this.virtualCamera = CGRect.getZero();
		this.cameraMargin = 50f;
		this.margeRect = CGRect.zero();
	}
	
	protected void tick(float delta) {
		if (this.isCameraOnContinuousMove) {
			this.moveCameraBy(this.moveCameraBy);
		}
		
		if (this.followed != null) {
			if (!this.followZoom) {
				this.centerCameraOn(this.followed.getPosition());
			}
			else {
				this.tryFollowUnzoom();
			}				
		}
	}
	
	public void tryFollowUnzoom() {
		if (this.followed != null) {
			CGPoint position = this.followed.getPosition();
			if (!CGRect.containsPoint(this.margeRect, position)) {
				float scale = this.gameLayer.getScale();				
				// Just unzoom 
				if (position.x > CGRect.maxX(this.margeRect))
				{
					this.setZoomPoint(CGPoint.zero());
					float targetZoom = this.currentZoom * (CGRect.width(this.margeRect) / (position.x - this.margeRect.origin.x));
					if (targetZoom > this.minFollowScale && targetZoom < this.currentZoom) {
						this.zoomCameraTo(targetZoom);
					}
				}				
				
				if (position.y > CGRect.maxY(this.margeRect))
				{
					this.setZoomPoint(CGPoint.zero());
					float targetZoom = this.currentZoom * (CGRect.height(this.margeRect) / (position.y - this.margeRect.origin.y));
					if (targetZoom > this.minFollowScale && targetZoom < this.currentZoom) {
						this.zoomCameraTo(targetZoom);
					}
				}
				
				if (position.y < CGRect.minY(this.margeRect))
				{
					this.setZoomPoint(CGPoint.make(CGRect.maxX(this.virtualCamera), CGRect.maxY(this.virtualCamera)));
					float targetZoom = this.currentZoom * (CGRect.height(this.margeRect) / (CGRect.maxY(this.margeRect) - position.y));
					if (targetZoom > this.minFollowScale && targetZoom < this.currentZoom) {
						this.zoomCameraTo(targetZoom);
					}
				}
				
				if (position.x < CGRect.minX(this.margeRect))
				{
					this.setZoomPoint(CGPoint.make(CGRect.maxX(this.virtualCamera), CGRect.maxY(this.virtualCamera)));
					float targetZoom = this.currentZoom * (CGRect.width(this.margeRect) / (CGRect.maxX(this.margeRect) - position.x));
					if (targetZoom > this.minFollowScale && targetZoom < this.currentZoom) {
						this.zoomCameraTo(targetZoom);
					}
				}	
			}
		}
	}		
	
	public void follow(GameItem item) {
		this.followed = item;
		this.followZoom = false;
	}
	
	public void followZoom(GameItem item) {
		this.followed = item;
		this.followZoom = true;
	}
		
	public void normalizePosition() {
		float scale = this.gameLayer.getScale();		
		boolean isNormalized = false;
		CGPoint position = this.gameLayer.getPosition();
		
		float maxLeft = CGRect.minX(this.screenView);
		float left = position.x;
		if (left > maxLeft) {
			position.x += maxLeft - left;
			isNormalized = true;
		}
		
		float minRight = CGRect.maxX(this.screenView);
		float right = position.x + this.levelWidth * scale;
		if (right < minRight) {
			position.x += minRight - right;
			isNormalized = true;
		}
		
		float maxBottom = CGRect.minY(this.screenView);
		float bottom = position.y;
		if (bottom > maxBottom) {
			position.y += maxBottom - bottom;
			isNormalized = true;
		}
		
		float minTop = CGRect.maxY(this.screenView);
		float top = position.y + this.levelHeight * scale;
		if (top < minTop) {
			position.y += minTop - top;
			isNormalized = true;
		}						
		
		this.gameLayer.setPosition(position);
		this.defineVirtualCameraRect();
		if (isNormalized) {
			this.isCameraOnContinuousMove = false;
		}
	}
	
	public void centerCameraOn(CGPoint center) {		
		this.keepPointAt(center, CGPoint.make(this.screenView.size.width / 2, this.screenView.size.height / 2));
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
		this.zoomAnchor = this.getGamePoint(zoomPoint);
	}
	
	public CGPoint getGamePoint(CGPoint screenPoint) {		
		float scale = this.gameLayer.getScale();
		CGPoint gamePoint = CGPoint.zero();
		gamePoint.x =  (screenPoint.x - this.gameLayer.getPosition().x) / scale; 
		gamePoint.y = (screenPoint.y - this.gameLayer.getPosition().y) / scale;
		
		return gamePoint;
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
		this.currentZoom = zoomValue;
		this.keepPointAt(this.zoomAnchor, this.zoomScreenPin);
		this.normalizePosition();
	}
		
	public float getCurrentZoom() {
		return this.currentZoom;
	}
	
	private void defineVirtualCameraRect() {
		this.virtualCamera.origin.set(this.getGamePoint(CGPoint.zero()));		
		
		float zoom = this.minScale;
		if (this.currentZoom != 0) {
			zoom = this.currentZoom;
		}
		float ratio = 1 / zoom;
		this.virtualCamera.size.width = CGRect.width(this.screenView) * ratio;
		this.virtualCamera.size.height = CGRect.height(this.screenView) * ratio;
		float ratioMarge = this.cameraMargin * ratio;
		this.margeRect.set(
				this.virtualCamera.origin.x + ratioMarge, 
				this.virtualCamera.origin.y + ratioMarge,
				this.virtualCamera.size.width - (ratioMarge * 2),
				this.virtualCamera.size.height - (ratioMarge * 2));
	}
	
	public void setCameraView() {		
		CGPoint origin = this.levelOrigin;		
		// Default view is windows size
		this.screenView = CGRect.make(origin, CCDirector.sharedDirector().winSize());					
		
		// By default camera can not be unzoom more than the most little size
		this.zoomAnchor = CGPoint.getZero();
		this.zoomScreenPin = CGPoint.getZero();
		float minScaleW = 1 / (this.levelWidth / CGRect.width(this.screenView));
		float minScaleH = 1 / (this.levelHeight / CGRect.height(this.screenView));
		this.minScale = Math.max(minScaleW, minScaleH);
		this.maxScale = 2.0f;
		this.minFollowScale = 0.5f;
		this.zoomSpeed = 3.0f;
		// To take into account new limits
		this.zoomCameraTo(1.0f);
		// this.defineVirtualCameraRect();
		// this.zoomCameraBy(0);
		// this.normalizePosition();
	}
	
	public void attachLevel(float levelWidth, float levelHeight, CGPoint levelOrigin) {
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;				
		this.levelOrigin = levelOrigin;
	}
	
	public void draw(GL10 gl) {
		// Drawing marge rectangle
		Util.draw(gl, this.margeRect, 2, 1, 0, 0, 1);
	}
}