package gamers.associate.Slime;

import java.util.Hashtable;

import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

public class SpriteSheetFactory {
	
	private static Hashtable<String, CCSpriteSheet> SpriteSheetList = new Hashtable<String, CCSpriteSheet>();
	
	public static void add(String plistPngName) {
		if (SpriteSheetList.get(plistPngName) == null) {
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(plistPngName + ".plist");
			CCSpriteSheet spriteSheet = CCSpriteSheet.spriteSheet(plistPngName + ".png");
			SpriteSheetList.put(plistPngName, spriteSheet);
		}
	}
	
	public static CCSpriteSheet getSpriteSheet(String plistPngName) {		
		add(plistPngName);
		return SpriteSheetList.get(plistPngName);
	}
}
