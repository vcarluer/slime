package gamers.associate.Slime.items;

import gamers.associate.Slime.CCSpriteRepeat;

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
	protected TextureMode textureMode;
	protected SpriteType spriteType;
	protected boolean attachedToRoot;
		
	public GameItemCocos(float x, float y, float width, float height) {
		super(x, y, width, height);		
		this.animationList = new Hashtable<String, CCAnimation>();		
		this.textureMode = TextureMode.SCALE;
		this.spriteType = SpriteType.UNKNOWN;
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
		if (this.attachedToRoot) {
			if (this.sprite != null) {
				if (this.rootNode != null) {
					this.rootNode.removeChild(this.sprite, true);
					this.attachedToRoot = false;
				}
			}
		}
		
		this.sprite = affectSprite;
		if (this.sprite != null) {
			if (this.rootNode != null) {
				this.rootNode.addChild(this.sprite);
				this.attachedToRoot = true;
			}
			
			this.sprite.setPosition(this.position);
			this.sprite.setRotation(this.angle);
			this.transformTexture();
		}
	}		
	
	protected void attachToRoot() {
		if (this.attachedToRoot) {
			if (this.sprite != null) {
				if (this.rootNode != null) {
					this.rootNode.removeChild(this.sprite, false);
					this.attachedToRoot = false;
				}
			}
		}
		
		if (this.sprite != null) {
			if (this.rootNode != null) {
				this.rootNode.addChild(this.sprite);
				this.attachedToRoot = true;
			}						
		}
	}
	
	protected void setSpriteInternal(CCSprite affectSprite) {
		this.sprite = affectSprite;
		if (this.sprite != null) {
			this.sprite.setPosition(this.position);
			this.sprite.setRotation(this.angle);
			this.transformTexture();
		}
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
		String referenceName = this.getReferenceAnimationName();
		if (referenceName != null) {
			return this.animationList.get(referenceName);
		}
		else {
			return null;
		}
			
	}
	
	protected String getReferenceAnimationName() {
		return null;
	}
	
	protected void transformTexture() {
		if (this.spriteType == SpriteType.ANIM || this.textureMode == TextureMode.SCALE) {
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
	}
	
	/**
	 * @param animations
	 * @uml.property  name="animationList"
	 */
	public void setAnimationList(Hashtable<String, CCAnimation> animations) {
		this.animationList = animations;
		//this.transformTexture();
	}
	
	@Override
	public void render(float delta) {
		if (this.sprite != null) {
			this.position = this.sprite.getPosition();			
			this.angle = this.sprite.getRotation();
		}
	}
	
	protected void addAnim(String animName, int frameCount) {
		if (this.animationList != null) {
			this.animationList.put(animName, GameItemCocos.createAnim(animName, frameCount));
		}
	}
	
	public static CCAnimation createAnim(String animName, int frameCount) {
		return createAnim(animName, frameCount, 0.1f);
	}
	
	public static CCAnimation createAnim(String animName, int frameCount, float interval) {
		ArrayList<CCSpriteFrame> animArray = new ArrayList<CCSpriteFrame>();
		for(int i = 0; i < frameCount; i++) {			
			String frameName = TextureAnimation.keyName(animName, i + 1);
			CCSpriteFrame frame = CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(frameName);
			if (frame != null) {
				animArray.add(frame);
			}
		}
		
		CCAnimation animation = CCAnimation.animation(animName, interval, animArray);
		return animation;
	}
	
	public static CCSpriteFrame getAnimFirstFrame(String animName) {
		String frameName = animName + "-1.png";
		return CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(frameName);
	}
	
	public static CCSprite createSpriteFromFirstFrame(String animName) {
		String frameName = animName + "-1.png";
		return CCSprite.sprite(frameName, true);
	}
	
	protected CCSpriteFrame getReferenceFirstFrame() {
		String reference = this.getReferenceAnimationName();
		if (reference != null) {
			CCSpriteFrame frame = GameItemCocos.getAnimFirstFrame(reference);
			return frame;
		}
		else {
			return null;
		}
			
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
	
	@Override
	public void initItem() {
		this.initSprite();
	}
	
	protected void initSprite() {
		CCSprite sprite = null;			
		switch (this.spriteType) {
			case UNKNOWN:
				sprite = new CCSprite();				
				break;
			case ANIM:
				// Todo: Works on iphone?
				// Problem: If sprite frame setted here => Shown during 1 frame before animation begins...
				// sprite = CCSprite.sprite(this.getReferenceFirstFrame());
				// If a texture for CCSprite is needed here create a dummy transparent texture?
				sprite = new CCSprite();				
				break;
			case SINGLE:
				sprite = CCSprite.sprite(this.getReferenceFirstFrame());
				break;
			case SINGLE_REPEAT:
				sprite = CCSpriteRepeat.sprite(this.getReferenceFirstFrame(), width, height);								
				break;
			case ANIM_REPEAT:
				sprite = CCSpriteRepeat.sprite(this.getReferenceFirstFrame(), width, height);											
				break;
			case POLYGON_REPEAT:
				sprite = CCSpritePolygon.sprite(this.getReferenceFirstFrame(), width, height);
				this.postCreateSprite(sprite);
				break;
			default:								
				break;
		}
		
		if (sprite != null) {
			this.setSpriteInternal(sprite);
			this.runReferenceAction();
			this.attachToRoot();
		}
	}
	
	protected void runReferenceAction() {		
	}
	
	protected void postCreateSprite(CCSprite createdSprite) {	
	}
	
	public SpriteType getSpriteType() {
		return this.spriteType;
	}
	
	public void setRootNode(CCNode node) {
		this.rootNode = node;
	}
}
