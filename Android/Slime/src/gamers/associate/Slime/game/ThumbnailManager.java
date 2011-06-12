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
		if (!CGRect.containsPoint(this.cameraManager.getVirtualCamera(), Util.mid(selectable.getSelectionRect()))) {
			float x = selectable.getPosition().x;
			float y = selectable.getPosition().y;
			float maxX = CGRect.maxX(this.cameraManager.getVirtualCamera());
			float maxY = CGRect.maxY(this.cameraManager.getVirtualCamera());
			float minX = CGRect.minX(this.cameraManager.getVirtualCamera());
			float minY = CGRect.minY(this.cameraManager.getVirtualCamera());
			float reverseScale = 1;
			if (this.level.getCameraManager().getCurrentZoom() != 0) {
				reverseScale = 1 / this.level.getCameraManager().getCurrentZoom();
			}
			
			if (selectable.getPosition().x > maxX) {
				x = maxX - (Thumbnail_Margin_Width * reverseScale) / 2;
			}
			
			if (selectable.getPosition().y > maxY) {
				y = maxY - (Thumbnail_Margin_Height * reverseScale) / 2;
			}
			
			if (selectable.getPosition().x < minX) {
				x = minX + (Thumbnail_Margin_Width * reverseScale) / 2;
			}
			
			if (selectable.getPosition().y < minY) {
				y = minY + (Thumbnail_Margin_Height * reverseScale) / 2;
			}
			
			if (!this.thumbnails.containsKey(selectable)) {								
				Thumbnail tn = new Thumbnail(x, y, Thumbnail_Margin_Width, Thumbnail_Margin_Height, selectable);
				this.level.addItemToAdd(tn);
				this.thumbnails.put(selectable, tn);
			}
			else {
				// reLimit boundaries
				maxX = maxX - (Thumbnail_Margin_Width * reverseScale) / 2;
				maxY = maxY - (Thumbnail_Margin_Height * reverseScale) / 2;
				minX = minX + (Thumbnail_Margin_Width * reverseScale) / 2;
				minY = minY + (Thumbnail_Margin_Height * reverseScale) / 2;
				
				if (x > maxX) {
					x = maxX;
				}
				
				if (y > maxY) {
					y = maxY;
				}
				
				if (x < minX) {
					x = minX;
				}
				
				if (y < minY) {
					y = minY;
				}
				
				this.thumbnails.get(selectable).setPosition(CGPoint.make(x, y));
			}
			
			this.thumbnails.get(selectable).getSprite().setScale(reverseScale);
		}
		else {
			if (this.thumbnails.containsKey(selectable)) {
				this.level.addItemToRemove(this.thumbnails.get(selectable));
				this.thumbnails.remove(selectable);
			}
		}
	}	
}
