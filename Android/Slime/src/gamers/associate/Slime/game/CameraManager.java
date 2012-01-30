package gamers.associate.Slime.game;

import gamers.associate.Slime.items.base.GameItem;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccMacros;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class CameraManager {
	private static float margeFollowUnzoom = 50f;
	private static float margeSelectUnzoom = 100f;
	
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
	private CGPoint layerPosition;
	
	private CGPoint zoomAnchor;
	private CGPoint zoomScreenPin;
	
	private ArrayList<CameraAction> actions;
	private ArrayList<CameraAction> actionsToRemove;
	
	private CGPoint st;
	private CGPoint tl;
	private CGPoint tr;
	private CGPoint br;
	private CGPoint bl;
	private CGPoint result1;
	private CGPoint result2;
	private CGPoint centerScreen;
	private CGPoint currentOpposite;
	private CGPoint screenPoint;
	
	private float zoomStart;
	
	public CameraManager(CCLayer gameLayer) {
		this.gameLayer = gameLayer;
				
		this.moveCameraBy = new CGPoint();
		this.virtualCamera = CGRect.getZero();
		this.cameraMargin = margeFollowUnzoom;
		this.margeRect = CGRect.zero();
		this.actions = new ArrayList<CameraAction>();
		this.actionsToRemove = new ArrayList<CameraAction>();
		this.layerPosition = CGPoint.zero();
		this.st = CGPoint.zero();
		this.tl = CGPoint.zero();
		this.tr = CGPoint.zero();
		this.br = CGPoint.zero();
		this.bl = CGPoint.zero();
		this.result1 = CGPoint.zero();
		this.result2 = CGPoint.zero();
		this.centerScreen = CGPoint.zero();
		this.currentOpposite = CGPoint.zero();
		this.screenPoint = CGPoint.zero();
	}
	
	protected void tick(float delta) {
		if (this.isCameraOnContinuousMove) {
			this.moveCameraBy(this.moveCameraBy);
		}
		
		// Todo: transform this to action
		if (this.followed != null) {
			if (!this.followZoom) {
				this.centerCameraOn(this.followed.getPosition());
			}
			else {
				this.tryFollowUnzoom();
			}				
		}
				
		for(CameraAction action : this.actions) {
			if (action.action(delta)) {
				this.actionsToRemove.add(action);
			}				
		}				
		
		if (this.actionsToRemove.size() > 0) {
			for(CameraAction action : this.actionsToRemove)  {
				this.actions.remove(action);
			}
			
			this.actionsToRemove.clear();
		}
	}
	
	private void tryFollowUnzoom() {
		if (this.followed != null && this.followed.isActive()) {
			CGPoint position = this.followed.getPosition();
			//if (!CGRect.containsPoint(this.margeRect, position)) {
				this.setZoomPoint(this.getTargetAnchorForMargin(position), true);
				
				// Just unzoom 
				float targetZoom = this.getTargetZoomForMargin(position);
				
				if (targetZoom > this.minFollowScale && targetZoom < this.zoomStart) {
					this.zoomCameraTo(targetZoom);
				}
			//}
		}
	}
	
	private CGPoint getTargetAnchorForMargin(CGPoint targetPosition) {
		// Opposite point anchor
//		float anchorX = (CGRect.maxX(this.margeRect) - targetPosition.x);
//		float anchorY = (CGRect.maxY(this.margeRect) - targetPosition.y);
//		return CGPoint.make(anchorX, anchorY);
		this.findOppositePointOnCamera(targetPosition, this.currentOpposite);
		return this.currentOpposite;
	}
	
	private float getTargetZoomForMargin(CGPoint targetPosition) {
		// Todo: This is bad compute. good compute is ratio between segment of center/slimy & center/cross point.
		float targetZoom = this.currentZoom;
		if (targetPosition.x > CGRect.maxX(this.margeRect)) {					
			targetZoom = this.currentZoom * (CGRect.width(this.margeRect) / (targetPosition.x - this.margeRect.origin.x));					
		}
						
		if (targetPosition.y > CGRect.maxY(this.margeRect)) {					
			targetZoom = this.currentZoom * (CGRect.height(this.margeRect) / (targetPosition.y - this.margeRect.origin.y));				
		}
		if (targetPosition.x < CGRect.minX(this.margeRect)) {					
			targetZoom = this.currentZoom * (CGRect.width(this.margeRect) / (CGRect.maxX(this.margeRect) - targetPosition.x));
		}

		if (targetPosition.y < CGRect.minY(this.margeRect)) {					
			targetZoom = this.currentZoom * (CGRect.height(this.margeRect) / (CGRect.maxY(this.margeRect) - targetPosition.y));				
		}
		
		return targetZoom;
	}		
	
	public void follow(GameItem item) {
		this.followed = item;
		this.followZoom = false;
	}
	
	public void followZoom(GameItem item) {
		this.cameraMargin = margeFollowUnzoom;
		this.followed = item;
		this.followZoom = true;
		this.zoomStart = this.getCurrentZoom();
	}
		
	public void normalizePosition() {
		float scale = this.gameLayer.getScale();		
		boolean isNormalized = false;
		CGPoint position = this.gameLayer.getPosition();
		
		float maxLeft = CGRect.minX(this.screenView) - (this.levelOrigin.x * scale);;
		float left = position.x;
		if (left > maxLeft) {
			position.x += maxLeft - left;
			isNormalized = true;
		}
		
		float minRight = CGRect.maxX(this.screenView) - (this.levelOrigin.x * scale);;
		float right = position.x + this.levelWidth * scale;
		if (right < minRight) {
			position.x += minRight - right;
			isNormalized = true;
		}
		
		float maxBottom = CGRect.minY(this.screenView) - (this.levelOrigin.y * scale);
		float bottom = position.y;
		if (bottom > maxBottom) {
			position.y += maxBottom - bottom;
			isNormalized = true;
		}
		
		float minTop = CGRect.maxY(this.screenView) - (this.levelOrigin.y * scale);
		float top = position.y - this.levelOrigin.y + this.levelHeight * scale;
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
		this.layerPosition.x = - (gamePoint.x * scale - screenPin.x);
		this.layerPosition.y = - (gamePoint.y * scale - screenPin.y);
		this.setLayerPosition(this.layerPosition);
	}
	
	private void setLayerPosition(CGPoint position) {				
		this.gameLayer.setPosition(position);
		this.normalizePosition();
	}
	
	public void setCameraPosition(CGPoint origin) {
		float scale = this.gameLayer.getScale();
		this.layerPosition.x = - origin.x * scale;
		this.layerPosition.y = - origin.y * scale;
		this.setLayerPosition(this.layerPosition);
	}
	
	public void moveCameraBy(CGPoint move) {
		float scale = this.gameLayer.getScale();
		this.layerPosition.x  = this.gameLayer.getPosition().x;
		this.layerPosition.y = this.gameLayer.getPosition().y;
		this.layerPosition.x += - move.x * scale;
		this.layerPosition.y += - move.y * scale;
		
		this.setLayerPosition(this.layerPosition);
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
	
	public void setZoomPoint(CGPoint zoomScreenPoint) {
		this.zoomScreenPin = CGPoint.make(zoomScreenPoint.x, zoomScreenPoint.y);				
		this.zoomAnchor = this.getGamePoint(zoomScreenPoint);
	}
	
	public void setZoomPoint(CGPoint zoomGamePoint, boolean isGamePoint) {
		this.zoomScreenPin = this.getScreenPoint(zoomGamePoint);				
		this.zoomAnchor = zoomGamePoint;
	}
	
	public CGPoint getScreenPoint(CGPoint gamePoint) {						
		this.screenPoint.x = (gamePoint.x - this.virtualCamera.origin.x) * this.getCurrentZoom();
		this.screenPoint.y = (gamePoint.y - this.virtualCamera.origin.y) * this.getCurrentZoom();	
		
		return this.screenPoint;
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
	
	public void zoomCameraCenterBy(float zoomDelta) {		
		float scale = this.gameLayer.getScale() + zoomDelta;
		this.zoomCameraCenterTo(scale);
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
		if (this.zoomAnchor != null) {
			this.keepPointAt(this.zoomAnchor, this.zoomScreenPin);
		}
		
		this.normalizePosition();
	}
	
	public void zoomCameraCenterTo(float zoomValue) {
		if (zoomValue <= minScale) {
			zoomValue = minScale;
		}
		
		if (zoomValue >= maxScale) {
			zoomValue = maxScale;
		}
		
		this.zoomScreenPin = CGPoint.make(CGRect.midX(this.screenView), CGRect.midY(this.screenView));
		this.zoomAnchor = this.getGamePoint(this.zoomScreenPin);
				
		this.gameLayer.setScale(zoomValue);		
		this.currentZoom = zoomValue;
		if (this.zoomScreenPin != null) {
			this.keepPointAt(this.zoomAnchor, this.zoomScreenPin);
		}				
		
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
		
		this.computeMargeRect();
	}
	
	private void computeMargeRect() {
		float zoom = this.minScale;
		if (this.currentZoom != 0) {
			zoom = this.currentZoom;
		}
		float ratio = 1 / zoom;
		
		float ratioMarge = this.cameraMargin * ratio;
		float wRatioMarge = ratioMarge * 2;
		float hRationMarge = ratioMarge * 2;
		float extBound = 100f;
		// Handle special case on level boudary limits
		float xMarge = this.virtualCamera.origin.x;
		if (xMarge != this.levelOrigin.x) {
			xMarge += ratioMarge;
		}
		else {
			xMarge -= extBound;
			wRatioMarge -= ratioMarge + extBound;
		}
		
		float yMarge = this.virtualCamera.origin.y;
		if (yMarge != this.levelOrigin.y) {
			yMarge += ratioMarge;
		}
		else {
			yMarge -= extBound;
			hRationMarge -= ratioMarge + extBound;
		}
		
		float wMarge = this.virtualCamera.size.width;
		if (this.virtualCamera.origin.x + wMarge != this.levelWidth) {
			wMarge -= wRatioMarge;
		}
		else {
			wMarge += extBound;
		}
		
		float hMarge = this.virtualCamera.size.height;
		if (this.virtualCamera.origin.y + hMarge != this.levelHeight) {
			hMarge -= hRationMarge;
		}
		else {
			hMarge += extBound;
		}
		
		this.margeRect.set(
				xMarge, 
				yMarge,
				wMarge,
				hMarge);
	}
	
	public void setCameraView() {		
		CGPoint origin = this.levelOrigin;		
		// Default view is windows size
		// this.screenView = CGRect.make(origin, CCDirector.sharedDirector().winSize());					
		this.screenView = CGRect.make(CGPoint.zero(), CCDirector.sharedDirector().winSize());
		
		// By default camera can not be unzoom more than the most little size		
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
		if (Level.DebugMode) {
			// Drawing marge rectangle
			Util.draw(gl, this.margeRect, 2, 1, 0, 0, 1);
			if (this.zoomAnchor != null) {
				gl.glDisable(GL10.GL_LINE_SMOOTH);
				gl.glLineWidth(2.0f);
				gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
				float size = 10;
				if (this.getCurrentZoom() != 0) {
					size = size / this.getCurrentZoom();
				}
				
				CCDrawingPrimitives.ccDrawCircle(gl, this.zoomAnchor, size, ccMacros.CC_DEGREES_TO_RADIANS(90), 10, true);			
			}
			
			if (this.zoomScreenPin != null) {
				CGPoint pinGame = this.getGamePoint(this.zoomScreenPin);
				gl.glDisable(GL10.GL_LINE_SMOOTH);
				gl.glLineWidth(1.0f);
				gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
				float size = 5;
				if (this.getCurrentZoom() != 0) {
					size = size / this.getCurrentZoom();
				}
				CCDrawingPrimitives.ccDrawCircle(gl, pinGame, size, ccMacros.CC_DEGREES_TO_RADIANS(90), 5, true);
			}
		}
	}
	
	public void cancelFollow() {
		this.followed = null;		
	}
	
	public void cancelActions() {
		this.actions.clear();
	}
	
	public void moveInterpolateTo(GameItem target, float time) {						
		this.addAction(new MoveInterpolateAction(this, time, target));
	}
	
	public CGRect getVirtualCamera() {
		return this.virtualCamera;
	}
	
	public void addAction(CameraAction action) {
		this.cancelFollow();
		this.actions.add(action);
	}
	
	public void zoomInterpolateTo(GameItem target, float targetZoom, float time) {
		this.addAction(new ZoomInterpolateAction(this, time, target, targetZoom));
	}
	
	public void zoomInterpolateTo(float targetZoom, float time) {
		this.addAction(new ZoomInterpolateAction(this, time, targetZoom));
	}
	
	public void unzoomForMargin(CGPoint targetPosition, float time) {
		this.cancelFollow();
		this.cameraMargin = margeSelectUnzoom;
		this.computeMargeRect();
		float targetZoom = this.getTargetZoomForMargin(targetPosition);
		if (targetZoom != this.currentZoom) {
			CGPoint anchor = this.getTargetAnchorForMargin(targetPosition);			
			this.addAction(new ZoomInterpolateAction(this, time, anchor, targetZoom));
		}
	}
	
	public void cancelZoomAnchor() {
		this.zoomAnchor = null;
		this.zoomScreenPin = null;
	}
	
	private void findOppositePointOnCamera(CGPoint itemPoint, CGPoint result) {
		tl.set(CGRect.minX(this.virtualCamera), CGRect.maxY(this.virtualCamera));
		tr.set(CGRect.maxX(this.virtualCamera), CGRect.maxY(this.virtualCamera));
		br.set(CGRect.maxX(this.virtualCamera), CGRect.minY(this.virtualCamera));
		bl.set(CGRect.minX(this.virtualCamera), CGRect.minY(this.virtualCamera));
		
		CGPoint centerScreen = this.getCenterScreen();
		
		CGPoint tempResult = null;
		float du = this.intersectCameraView(itemPoint, centerScreen, tl, tr, this.result1);
		if (du != -1) {
			float db = this.intersectCameraView(itemPoint, centerScreen, br, bl, this.result2);
			if (du > db) {
				tempResult = this.result1;				
			}
			else {
				tempResult = this.result2;
			}
		}
		else
		{
			float dr = this.intersectCameraView(itemPoint, centerScreen, tr, br, this.result1);			
			float dl = this.intersectCameraView(itemPoint, centerScreen, bl, tl, this.result2);
			if (dr > dl) {
				tempResult = this.result1;
			}
			else {
				tempResult = this.result2;
			}		
		}
		
		if (tempResult != null) {
			result.x = tempResult.x;
			result.y = tempResult.y;
		}
	}
	
	// p1, p2 = item, center
	// p3, p4 = screen array
	private float intersectCameraView(CGPoint p1, CGPoint p2, CGPoint p3, CGPoint p4, CGPoint result) {		
		float length = -1;
		if (CGPoint.ccpLineIntersect(p1, p2, p3, p4, this.st)) {
			if (this.st.y > 0 && this.st.y < 1)
			{			
				result.x = p3.x + st.y * (p4.x - p3.x);
				result.y = p3.y + st.y * (p4.y - p3.y);				
				length = CGPoint.ccpDistance(p1, result);
			}
		}
		
		return length;
	}
	
	public CGPoint getCenterScreen() {
		Util.mid(this.virtualCamera, this.centerScreen);
		return this.centerScreen;
	}
}