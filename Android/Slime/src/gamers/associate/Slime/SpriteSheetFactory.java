package gamers.associate.Slime;

import java.util.ArrayList;
import java.util.Hashtable;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

public class SpriteSheetFactory {
	public static int Included_For_Attach = 0;
	public static int Excluded_For_Attach = 1;
	
	private static Hashtable<String, CCSpriteSheet> SpriteSheetList = new Hashtable<String, CCSpriteSheet>();
	private static CCNode rootNode;
	private static boolean isAttached;
	
	public static void add(String plistPngName) {
		add(plistPngName, false);
	}
	
	public static void add(String plistPngName, boolean isExcluded) {
		if (plistPngName != "") {
			if (SpriteSheetList.get(plistPngName) == null) {
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(plistPngName + ".plist");
				CCSpriteSheet spriteSheet = CCSpriteSheet.spriteSheet(plistPngName + ".png");
				int tag = Included_For_Attach;
				if (isExcluded) {
					tag = Excluded_For_Attach;
				}
				
				spriteSheet.setTag(tag);
				SpriteSheetList.put(plistPngName, spriteSheet);
			}
		}
	}
	
	public static CCSpriteSheet getSpriteSheet(String plistPngName) {		
		return getSpriteSheet(plistPngName, false);
	}
	
	public static CCSpriteSheet getSpriteSheet(String plistPngName, boolean isExcluded) {		
		if (plistPngName != "") {
			add(plistPngName, isExcluded);
			return SpriteSheetList.get(plistPngName);
		}
		else {
			return null;
		}
	}
	
	public static void destroy() {		
		SpriteSheetList.clear();
	}
	
	public static void attachAll(CCNode attachNode) {
		rootNode = attachNode;		
		if (rootNode != null) {
			detachAll();
			for(CCSpriteSheet spriteSheet : SpriteSheetList.values()) {
				if (spriteSheet.getTag() == Included_For_Attach) {
					rootNode.addChild(spriteSheet);
				}
			}
			
			isAttached = true;
		}		
	}
	
	public static void detachAll() {
		if (isAttached && rootNode != null) {
			for(CCSpriteSheet spriteSheet : SpriteSheetList.values()) {
				if (spriteSheet.getTag() == Included_For_Attach) {
					rootNode.removeChild(spriteSheet, true);
				}				
			}
		}		
	}
}
