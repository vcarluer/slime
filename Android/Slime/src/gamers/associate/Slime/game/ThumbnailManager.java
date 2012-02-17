package gamers.associate.Slime.game;

import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.custom.Thumbnail;
import gamers.associate.Slime.layers.PauseLayer;

import java.util.ArrayList;
import java.util.HashMap;

import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class ThumbnailManager {
	public static float Thumbnail_Margin_Width = 50f;
	public static float Thumbnail_Margin_Height = 50f;
	
	private CameraManager cameraManager;
	private HashMap<ISelectable, Thumbnail> thumbnails;
	private Level level;
	
	private CGPoint tl;
	private CGPoint tr;
	private CGPoint br;
	private CGPoint bl;
	private CGPoint result;
	private CGPoint vector;
	private CGPoint st;
	
	private float topPadding = 75; // why 75? good question watson
	private CGRect thumbRect;
	
	public ThumbnailManager(Level level) {
		this.level = level;
		this.cameraManager = this.level.getCameraManager();
		this.thumbnails = new HashMap<ISelectable, Thumbnail>();
		this.tl = CGPoint.zero();
		this.tr = CGPoint.zero();
		this.br = CGPoint.zero();
		this.bl = CGPoint.zero();
		this.result = CGPoint.zero();
		this.vector = CGPoint.zero();
		this.st = CGPoint.zero();
		this.thumbRect = CGRect.zero();
	}
	
	public void handle(ArrayList<ISelectable> selectables) {
		for(ISelectable selectable : selectables) {
			if (!(selectable instanceof Thumbnail)) {
				this.manage(selectable);
			}			
		}
	}
	
	private void computeRect() {
		CGRect camera = this.cameraManager.getVirtualCamera();
		float zoom = this.cameraManager.getCurrentZoom();
		float ratio = 1 / zoom;
		this.thumbRect.set(camera.origin.x, camera.origin.y, camera.size.width, camera.size.height - (topPadding * ratio));
	}
	
	private void manage(ISelectable selectable) {
		this.computeRect();
		if (!CGRect.containsPoint(this.thumbRect, Util.mid(selectable.getSelectionRect())) 
				// && (selectable.isActive() || this.level.isPaused())) {
				&& (this.level.isPaused() || (selectable.isThumbnailAwaysOn() && selectable.isActive()))) {			
			float x = selectable.getPosition().x;
			float y = selectable.getPosition().y;						
			
			CGRect camera = this.thumbRect;
			
			float reverseScale = 1;
			if (this.level.getCameraManager().getCurrentZoom() != 0) {
				reverseScale = 1 / this.level.getCameraManager().getCurrentZoom();
			}	
			
			float maxMargeX = CGRect.maxX(camera) - (Thumbnail_Margin_Width * reverseScale) / 2;
			float maxMargeY = CGRect.maxY(camera) - (Thumbnail_Margin_Height * reverseScale) / 2;
			float minMargeX = CGRect.minX(camera) + (Thumbnail_Margin_Width * reverseScale) / 2;
			float minMargeY = CGRect.minY(camera) + (Thumbnail_Margin_Height * reverseScale) / 2;
			
			CGPoint centerScreen = this.cameraManager.getCenterScreen();
			CGPoint target = selectable.getPosition();
			tl.set(CGRect.minX(camera), CGRect.maxY(camera));
			tr.set(CGRect.maxX(camera), CGRect.maxY(camera));
			br.set(CGRect.maxX(camera), CGRect.minY(camera));
			bl.set(CGRect.minX(camera), CGRect.minY(camera));			
			
			if (!this.intersect(centerScreen, target, tl, tr, result)){
				if (!this.intersect(centerScreen, target, tr, br, result)){
					if (!this.intersect(centerScreen, target, br, bl, result)){
						this.intersect(centerScreen, target, bl, tl, result);
					}
				}
			}
			
			x = result.x;
			y = result.y;
			
			if (x > maxMargeX) {
				x = maxMargeX;
			}
			
			if (y > maxMargeY) {
				y = maxMargeY;
			}
			
			if (x < minMargeX) {
				x = minMargeX;
			}
			
			if (y < minMargeY) {
				y = minMargeY;
			}
						
			if (!this.thumbnails.containsKey(selectable)) {								
				Thumbnail tn = SlimeFactory.Thumbnail.create(x, y, selectable);
				this.level.addItemToAdd(tn);
				this.thumbnails.put(selectable, tn);
			}
			else {			
				this.thumbnails.get(selectable).setPosition(CGPoint.make(x, y));
			}
			
			this.thumbnails.get(selectable).setScale(reverseScale);
			
			this.vector.x = selectable.getPosition().x - this.thumbnails.get(selectable).getPosition().x;
			this.vector.y = selectable.getPosition().y - this.thumbnails.get(selectable).getPosition().y;
			
			float angle = CGPoint.ccpToAngle(vector);
			this.thumbnails.get(selectable).setAngle(angle);
		}
		else {
			this.removeThumbnail(selectable);
		}
	}
	
	private boolean intersect(CGPoint p1, CGPoint p2, CGPoint p3, CGPoint p4, CGPoint result) {		
		boolean intersected = false;
		if (CGPoint.ccpLineIntersect(p1, p2, p3, p4, this.st)) {
			if (this.st.x > 0 && this.st.y > 0 && this.st.x < 1 && this.st.y < 1)
			{
//				CGPoint s = CGPoint.ccpSub(p4, p3);
//				CGPoint m = CGPoint.ccpMult(s, st.y);								
//				CGPoint intersect =  CGPoint.ccpAdd(p3, m);				
				
				result.x = p3.x + st.y * (p4.x - p3.x);
				result.y = p3.y + st.y * (p4.y - p3.y);				
				intersected = true;
			}
		}
		
		return intersected;
	}
	
	public void removeThumbnail(ISelectable selectable) {
		if (selectable != null) {
			if (this.thumbnails.containsKey(selectable)) {
				this.level.addItemToRemove(this.thumbnails.get(selectable));
				this.thumbnails.remove(selectable);
			}
		}
	}
}
