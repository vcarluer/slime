package gamers.associate.Slime;

import java.util.Hashtable;

import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

import com.badlogic.gdx.physics.box2d.World;

public class GoalPortalFactory {
	private static String plist = "labo.plist";
	private static String png = "labo.png";	
	private static Hashtable<String, CCAnimation> sharedAnimations;
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
			
			sharedAnimations = new Hashtable<String, CCAnimation>();
			createAnim(GoalPortal.Anim_Goal_Portal, 4);						
			isInit = true;
		}
	}	
		
	public static GoalPortal create(float x, float y) {
		if (isAttached) {
			GoalPortal portal = new GoalPortal(spriteSheet, x, y, world, worldRatio);			
			portal.setAnimationList(sharedAnimations);
			portal.createPortal();			
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
