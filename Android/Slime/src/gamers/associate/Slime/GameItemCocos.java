package gamers.associate.Slime;

import java.util.ArrayList;
import java.util.Hashtable;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGSize;

/**
 * @author  vince
 */
public abstract class GameItemCocos extends GameItem {	
	protected Hashtable<String, CCAnimation> animationList;
	protected CCAction currentAction;
	protected CCSprite sprite;
	protected CCNode rootNode;
		
	public GameItemCocos(CCNode node, float x, float y, float width, float height) {
		super(x, y, width, height);		
		this.animationList = new Hashtable<String, CCAnimation>();		
		this.rootNode = node;		
		
		CCSprite sprite = new CCSprite();
		this.setSprite(sprite);
	}
	
	@Override
	public void destroy() {		
		if (this.sprite != null) {
			if (this.rootNode != null) {
				this.rootNode.removeChild(this.sprite, true);
			}			
		}
		
		super.destroy();
	}
	
	public CCSprite getSprite() {
		return this.sprite;
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
	
	@Override
	public void render(float delta) {
		if (this.sprite != null) {
			this.position = this.sprite.getPosition();
		}
	}
	
	protected void addAnim(String animName, int frameCount) {
		if (this.animationList != null) {
			this.animationList.put(animName, GameItemCocos.createAnim(animName, frameCount));
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

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.GameItem#pause()
	 */
	@Override
	protected void pause() {
		super.pause();
		if (this.sprite != null) {
			this.sprite.pauseSchedulerAndActions();
		}
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.GameItem#resume()
	 */
	@Override
	protected void resume() {		
		super.resume();
		if (this.sprite != null) {
			this.sprite.resumeSchedulerAndActions();
		}
	}	
}
