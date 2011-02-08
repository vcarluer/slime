package gamers.associate.Slime;

import java.util.Hashtable;

import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

import com.badlogic.gdx.physics.box2d.World;

public class SlimyFactory {
	private CCSpriteSheet spriteSheet;
	private CCNode rootNode;
	private Hashtable<String, CCAnimation> animationList;
	private World world;	
	private float worldRatio;
	
	public SlimyFactory(CCNode node, World world, float worldRatio) {		
		this.rootNode = node;
		this.world = world;
		this.worldRatio = worldRatio;
		
		// Animations		
		this.animationList = new Hashtable<String, CCAnimation>();
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("labo.plist");
		this.spriteSheet = CCSpriteSheet.spriteSheet("labo.png");
		this.rootNode.addChild(this.spriteSheet);
		this.createAnim(Slimy.Anim_Burned_Wait, 2);
		this.createAnim(Slimy.Anim_Burning, 5);
		this.createAnim(Slimy.Anim_Falling, 3);
		this.createAnim(Slimy.Anim_Landing_H, 3);
		this.createAnim(Slimy.Anim_Landing_V, 3);		
		this.createAnim(Slimy.Anim_Wait_H, 5);
		this.createAnim(Slimy.Anim_Wait_V, 5);
	}
	
	public Slimy create() {
		Slimy slimy = new Slimy(this.spriteSheet, this.world, this.worldRatio);		
		slimy.setAnimationList(this.animationList);
		slimy.waitAnim();
		
		return slimy;
	}
	
	protected void createAnim(String animName, int frameCount) {		
		this.animationList.put(animName, GameItem.createAnim(animName, frameCount));
	}
}
