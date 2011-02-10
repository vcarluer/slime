package gamers.associate.Slime;

import java.util.ArrayList;
import java.util.Hashtable;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

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
	protected CCAction currentAction;
	protected CCSprite sprite;
	protected CCSpriteSheet spriteSheet;
	protected CCNode rootNode;
	
	public GameItem(CCNode node, float x, float y) {
		this.animationList = new Hashtable<String, CCAnimation>();
		this.position = new CGPoint();
		this.position.x = x;
		this.position.y = y;
		this.angle = 0f;
		this.rootNode = node;
		this.scale = 1.0f;
		
		CCSprite sprite = new CCSprite();
		this.setSprite(sprite);		
	}
	
	public CGPoint getPosition() {
		return this.position;
	}
	
	public void setSprite(CCSprite affectSprite) {
		if (this.sprite != null) {
			this.rootNode.removeChild(this.sprite, true);
		}
		
		this.sprite = affectSprite;
		this.rootNode.addChild(this.sprite);
		this.sprite.setPosition(this.position);
		this.sprite.setRotation(this.angle);
		this.sprite.setScale(this.scale);
		if (this.width != 0 && this.height != 0) {
			float texW = CGRect.width(this.sprite.getTextureRect());
			float textH = CGRect.height(this.sprite.getTextureRect());
			float wScale = this.width / texW;
			float hScale = this.height / textH;
			this.sprite.setScaleX(wScale);
			this.sprite.setScaleY(hScale);
		}		
	}
	
	/**
	 * @param animations
	 * @uml.property  name="animationList"
	 */
	public void setAnimationList(Hashtable<String, CCAnimation> animations) {
		this.animationList = animations;
	}
	
	public void render(float delta) {
		if (this.sprite != null) {
			this.position = this.sprite.getPosition();
		}
	}
	
	protected void addAnim(String animName, int frameCount) {
		if (this.animationList != null) {
			this.animationList.put(animName, GameItem.createAnim(animName, frameCount));
		}
	}
	
	public static CCAnimation createAnim(String animName, int frameCount) {
		ArrayList<CCSpriteFrame> animArray = new ArrayList<CCSpriteFrame>();
		for(int i = 0; i < frameCount; i++) {			
			String frameName = animName + "-" + String.valueOf(i + 1) + ".png";
			CCSpriteFrame frame = CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(frameName);
			if (frame != null) {
				animArray.add(frame);
			}
		}
		
		CCAnimation animation = CCAnimation.animation(animName, 0.1f, animArray);
		return animation;
	}
}
