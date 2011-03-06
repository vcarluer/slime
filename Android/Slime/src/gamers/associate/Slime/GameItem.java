package gamers.associate.Slime;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * @author  vince
 */
public abstract class GameItem {
	protected CGPoint position;
	protected float angle;
	protected float width;
	protected float height;
	protected Hashtable<String, CCAnimation> animationList;
	protected CCAction currentAction;
	protected CCSprite sprite;
	protected CCNode rootNode;
	protected UUID id;
		
	public GameItem(CCNode node, float x, float y, float width, float height) {
		this.id = UUID.randomUUID();
		this.animationList = new Hashtable<String, CCAnimation>();
		this.position = new CGPoint();
		this.position.x = x;
		this.position.y = y;
		this.angle = 0f;
		this.rootNode = node;
		this.width = width;
		this.height = height;
		
		CCSprite sprite = new CCSprite();
		this.setSprite(sprite);
	}
	
	public UUID getId() {
		return this.id;
	}
	
	public void destroy() {
		if (this.sprite != null) {
			if (this.rootNode != null) {
				this.rootNode.removeChild(this.sprite, true);
			}			
		}
	}
	
	public CCSprite getSprite() {
		return this.sprite;
	}
	
	public CGPoint getPosition() {
		return this.position;
	}
	
	public void setSprite(CCSprite affectSprite) {
		if (this.sprite != null) {
			if (this.rootNode != null) {
				this.rootNode.removeChild(this.sprite, true);
			}
		}
		
		this.sprite = affectSprite;		
		if (this.rootNode != null) {
			this.rootNode.addChild(this.sprite);
		}
		
		this.sprite.setPosition(this.position);
		this.sprite.setRotation(this.angle);
		this.transformTexture();		
	}		
	
	public CGSize getTextureSize() {
		CGSize size = CGSize.zero();
		if (this.sprite.getTextureRect().size.width != 0) {
			size = this.sprite.getTextureRect().size;
		}
		else
		{		
			CCAnimation anim = this.getReferenceAnimation();
			if (anim != null) {
				size = anim.frames().get(0).getRect().size;
			}					
		}
		
		return size;
	}
	
	protected CCAnimation getReferenceAnimation() {
		return null;
	}
	
	protected void transformTexture() {
		if (this.width != 0 && this.height != 0) {
			CGSize size = this.getTextureSize();			
			
			if (size.width != 0 && size.height != 0) {
				float wScale = this.width / size.width;
				float hScale = this.height / size.height;
				this.sprite.setScaleX(wScale);
				this.sprite.setScaleY(hScale);
			}			
		}
	}
	
	/**
	 * @param animations
	 * @uml.property  name="animationList"
	 */
	public void setAnimationList(Hashtable<String, CCAnimation> animations) {
		this.animationList = animations;
		this.transformTexture();
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
	
	public void draw(GL10 gl) {
		
	}
}
