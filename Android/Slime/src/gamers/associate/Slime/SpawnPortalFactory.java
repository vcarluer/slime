package gamers.associate.Slime;

import java.util.Hashtable;

import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

public class SpawnPortalFactory {
	private static String plist = "labo.plist";
	private static String png = "labo.png";	
	private static Hashtable<String, CCAnimation> sharedAnimations;
	private static CCSpriteSheet spriteSheet;	
	private static boolean isInit = false;	
	private static boolean isAttached = false;
	private static CCNode rootNode;	
	
	public static void Attach(CCNode attachNode) {
		rootNode = attachNode;		
		init();
		rootNode.addChild(spriteSheet);
		isAttached = true;
	}
	
	public static void Detach() {
		if (isAttached && spriteSheet != null && rootNode != null) {
			// true here?
			rootNode.removeChild(spriteSheet, true);
			rootNode = null;			
			isAttached = false;
		}
	}
	
	private static void init() {
		if (!isInit) {
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(plist);
			spriteSheet = CCSpriteSheet.spriteSheet(png);			
			
			sharedAnimations = new Hashtable<String, CCAnimation>();
			createAnim(SpawnPortal.Anim_Spawn_Portal, 4);						
			isInit = true;
		}
	}	
		
	public static SpawnPortal create(float x, float y, float moveBy, float speed) {
		if (isAttached) {
			SpawnPortal portal = new SpawnPortal(spriteSheet, x, y);
			portal.setAnimationList(sharedAnimations);
			portal.createPortal(x, y);
			portal.MovePortalInLine(moveBy, speed);
			return portal;
		}
		else
		{
			return null;
		}			
	}
	
	private static void createAnim(String animName, int frameCount) {		
		sharedAnimations.put(animName, GameItem.createAnim(animName, frameCount));
	}

}
