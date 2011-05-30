package gamers.associate.Slime.items.base;

import java.util.Hashtable;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

public class SpriteSheetFactory {
		
	private static Hashtable<String, SpriteSheetCacheInfo> SpriteSheetList = new Hashtable<String, SpriteSheetCacheInfo>();
	private static CCNode rootNode;
	private static boolean isAttached;
	public static int zDefault = 1;
	
	public static void add(String plistPngName, int zOrder) {
		add(plistPngName, false, zOrder);
	}
	
	public static void add(String plistPngName, boolean isExcluded, int zOrder) {
		if (plistPngName != "") {
			if (SpriteSheetList.get(plistPngName) == null) {
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(plistPngName + ".plist");
				CCSpriteSheet spriteSheet = CCSpriteSheet.spriteSheet(plistPngName + ".png");
				int tag = SpriteSheetCacheInfo.Included_For_Attach;
				if (isExcluded) {
					tag = SpriteSheetCacheInfo.Excluded_For_Attach;
				}
				
				SpriteSheetCacheInfo sci = new SpriteSheetCacheInfo(plistPngName, spriteSheet, zOrder, tag);
				
				SpriteSheetList.put(sci.getName(), sci);
			}
		}
	}
	
	public static CCSpriteSheet getSpriteSheet(String plistPngName) {		
		return getSpriteSheet(plistPngName, false);
	}
	
	public static CCSpriteSheet getSpriteSheet(String plistPngName, boolean isExcluded) {		
		if (plistPngName != "") {
			add(plistPngName, isExcluded, SpriteSheetFactory.zDefault);
			return SpriteSheetList.get(plistPngName).getSpriteSheet();
		}
		else {
			return null;
		}
	}
	
	public static void destroy() {		
		SpriteSheetList.clear();
		isAttached = false;
		rootNode = null;
	}
	
	public static void attachAll(CCNode attachNode) {
		rootNode = attachNode;		
		if (rootNode != null) {
			detachAll();			
			for(SpriteSheetCacheInfo sci : SpriteSheetList.values()) {
				if (sci.getAttachType() == SpriteSheetCacheInfo.Included_For_Attach) {										
					rootNode.addChild(sci.getSpriteSheet(), sci.getZOrder());
				}
			}
			
			isAttached = true;
		}		
	}
	
	public static void detachAll() {
		if (isAttached && rootNode != null) {
			for(SpriteSheetCacheInfo sci : SpriteSheetList.values()) {
				if (sci.getAttachType() == SpriteSheetCacheInfo.Included_For_Attach) {
					rootNode.removeChild(sci.getSpriteSheet(), true);
				}				
			}
		}		
	}
}
