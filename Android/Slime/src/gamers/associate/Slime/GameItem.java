package gamers.associate.Slime;

import java.util.ArrayList;
import java.util.Hashtable;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;

/**
 * @author  vince
 */
public abstract class GameItem {
	protected CGPoint position;
	protected float angle;
	protected float width;
	protected float height;
	protected float scale;
	protected Hashtable<String, CCAnimation> animationList;
	protected CCSprite sprite;
	protected CCSpriteSheet spriteSheet;
	protected CCNode rootNode;
	
	public GameItem(CCNode node) {
		this.animationList = new Hashtable<String, CCAnimation>();
		this.position = new CGPoint();
		this.angle = 0f;
		this.rootNode = node;
		this.scale = 1.0f;
		
		this.sprite = new CCSprite();		
		this.rootNode.addChild(this.sprite);
	}
		
	/**
	 * @param animations
	 * @uml.property  name="animationList"
	 */
	public void setAnimationList(Hashtable<String, CCAnimation> animations) {
		this.animationList = animations;
	}
	
	public void render(float delta) {	
	}
	
	public static CCAnimation createAnim(String animName, int frameCount) {
		ArrayList<CCSpriteFrame> animArray = new ArrayList<CCSpriteFrame>();
		for(int i = 0; i < frameCount; i++) {			
			String frameName = animName + "-" + String.valueOf(i + 1) + ".png";
			animArray.add(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(frameName));		
		}
		
		CCAnimation animation = CCAnimation.animation(animName, 0.1f, animArray);
		return animation;
	}
}
