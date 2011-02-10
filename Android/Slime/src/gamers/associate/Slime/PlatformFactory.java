package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

import com.badlogic.gdx.physics.box2d.World;

public class PlatformFactory {
	private static String plist = "labo.plist";
	private static String png = "labo.png";	
	//private static Hashtable<String, CCAnimation> sharedAnimations;
	private static CCSpriteSheet spriteSheet;	
	private static boolean isInit = false;	
	private static boolean isAttached = false;
	private static CCNode rootNode;
	private static World world;
	private static float worldRatio;
	
	public static void Attach(CCNode attachNode, World attachWorld, float attachWorldRatio) {
		rootNode = attachNode;
		world = attachWorld;
		worldRatio = attachWorldRatio;
		init();
		rootNode.addChild(spriteSheet);
		isAttached = true;
	}
	
	public static void Detach() {
		if (isAttached && spriteSheet != null && rootNode != null) {
			// true here?
			rootNode.removeChild(spriteSheet, true);
			rootNode = null;
			world = null;
			worldRatio = 0f;
			isAttached = false;
		}
	}
	
	private static void init() {
		if (!isInit) {
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(plist);				
			spriteSheet = CCSpriteSheet.spriteSheet(png);
			
			// sharedAnimations = new Hashtable<String, CCAnimation>();
			isInit = true;
		}
	}	
		
	public static Platform create(float x, float y, float width, float height) {
		if (isAttached) {
			Platform platform = new Platform(spriteSheet, x, y, world, worldRatio, width, height);		
			// platform.setAnimationList(sharedAnimations);
			CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(Platform.texture));			
			platform.setSprite(sprite);
			
			return platform;
		}
		else
		{
			return null;
		}			
	}
	
	/*private static void createAnim(String animName, int frameCount) {		
		sharedAnimations.put(animName, GameItem.createAnim(animName, frameCount));
	}*/
}
