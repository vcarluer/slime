package gamers.associate.Slime.game;

import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.custom.Thumbnail;

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
	
	public ThumbnailManager(Level level) {
		this.level = level;
		this.cameraManager = this.level.getCameraManager();
		this.thumbnails = new HashMap<ISelectable, Thumbnail>();
	}
	
	public void handle(ArrayList<ISelectable> selectables) {
		for(ISelectable selectable : selectables) {
			if (!(selectable instanceof Thumbnail)) {
				this.manage(selectable);
			}			
		}
	}
	
	private void manage(ISelectable selectable) {		
		if (!CGRect.containsPoint(this.cameraManager.getVirtualCamera(), Util.mid(selectable.getSelectionRect())) && selectable.isActive()) {
			float x = selectable.getPosition().x;
			float y = selectable.getPosition().y;						
			
			CGRect camera = this.cameraManager.getVirtualCamera();
			
			float reverseScale = 1;
			if (this.level.getCameraManager().getCurrentZoom() != 0) {
				reverseScale = 1 / this.level.getCameraManager().getCurrentZoom();
			}	
			
			float maxMargeX = CGRect.maxX(camera) - (Thumbnail_Margin_Width * reverseScale) / 2;
			float maxMargeY = CGRect.maxY(camera) - (Thumbnail_Margin_Height * reverseScale) / 2;
			float minMargeX = CGRect.minX(camera) + (Thumbnail_Margin_Width * reverseScale) / 2;
			float minMargeY = CGRect.minY(camera) + (Thumbnail_Margin_Height * reverseScale) / 2;
			
			CGPoint centerScreen = Util.mid(camera);
			CGPoint target = selectable.getPosition();
			CGPoint tl = CGPoint.make(CGRect.minX(camera), CGRect.maxY(camera));
			CGPoint tr = CGPoint.make(CGRect.maxX(camera), CGRect.maxY(camera));
			CGPoint br = CGPoint.make(CGRect.maxX(camera), CGRect.minY(camera));
			CGPoint bl = CGPoint.make(CGRect.minX(camera), CGRect.minY(camera));
			CGPoint result = CGPoint.zero();
			
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
			
			CGPoint vector = CGPoint.ccpSub(selectable.getPosition(), this.thumbnails.get(selectable).getPosition());
			float angle = CGPoint.ccpToAngle(vector);
			this.thumbnails.get(selectable).setAngle(angle);
		}
		else {
			if (this.thumbnails.containsKey(selectable)) {
				this.level.addItemToRemove(this.thumbnails.get(selectable));
				this.thumbnails.remove(selectable);
			}
		}
	}
	
	private boolean intersect(CGPoint p1, CGPoint p2, CGPoint p3, CGPoint p4, CGPoint result) {
		CGPoint st = CGPoint.zero();
		boolean intersected = false;
		if (CGPoint.ccpLineIntersect(p1, p2, p3, p4, st)) {
			if (st.x > 0 && st.y > 0 && st.x < 1 && st.y < 1)
			{
				CGPoint s = CGPoint.ccpSub(p4, p3);
				CGPoint m = CGPoint.ccpMult(s, st.y);								
				CGPoint intersect =  CGPoint.ccpAdd(p3, m);				
				
				result.x = intersect.x;
				result.y = intersect.y;
				intersected = true;
			}
		}
		
		return intersected;
	}
}
