package gamers.associate.Slime.items.base;


import gamers.associate.Slime.items.custom.SpriteAction;

import java.util.ArrayList;
import java.util.Hashtable;

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
public class GameItemCocos extends GameItem {	
	protected Hashtable<String, CCAnimation> animationList;
	protected CCAction currentAction;
	protected CCSprite sprite;
	protected CCNode rootNode;	
	protected SpriteType spriteType;
	protected boolean attachedToRoot;
	protected int zOrder;
	protected CGSize referenceSize;
	private String referenceAnimationName;
	private String pList;
	private String frameName;
	private int frameCount;
	private SpriteAction spriteAction;
	
	protected boolean positionChanged;
	protected boolean angleChanged;
		
	public GameItemCocos(float x, float y, float width, float height) {
		this(null, x, y, width, height);
	}
	
	public GameItemCocos(String name, float x, float y, float width, float height) {
		super(name, x, y, width, height);		
		this.animationList = new Hashtable<String, CCAnimation>();				
		this.spriteType = SpriteType.UNKNOWN;
		this.referenceSize = CGSize.zero();
	}
	
	public GameItemCocos(float x, float y, float width, float height, int zOrder) {
		this(null, x, y, width, height, zOrder);
	}
	
	public GameItemCocos(String name, float x, float y, float width, float height, int zOrder) {
		this(name, x, y, width, height);
		this.zOrder = zOrder;
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
				this.rootNode.addChild(this.sprite, this.zOrder);
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
		return this.referenceAnimationName;
	}
	
	// Used by CcosFactory
	public void setReferenceAnimationName(String referenceAnimationName) {
		this.referenceAnimationName = referenceAnimationName;
	}
	
	protected void transformTexture() {
		if (this.spriteType == SpriteType.ANIM_SCALE || this.spriteType == SpriteType.SINGLE_SCALE || this.spriteType == SpriteType.SINGLE_SCALE_DIRECT) {
			if (this.width != 0 && this.height != 0) {
				CGSize size = null;
				if (this.referenceSize.width != 0 && this.referenceSize.height != 0) {
					size = this.referenceSize;
				}
				else {
					size = this.getTextureSize();
				}			
				
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
			// get sprite position reference (not copy)
			this.positionChanged = this.position.x != this.sprite.getPositionRef().x || this.position.y != this.getSprite().getPositionRef().y;
			if (this.positionChanged) {
				this.position = this.sprite.getPositionRef();
			}
			
			this.angleChanged = this.angle != this.sprite.getRotation();
			if (this.angleChanged) {
				this.angle = this.sprite.getRotation();
			}			
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
		String frameName = animName + "-" + TextureAnimation.formatFrameNumber(1) + ".png";
		return CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(frameName);
	}
	
	public static CCSprite createSpriteFromFirstFrame(String animName) {
		String frameName = animName + "-" + TextureAnimation.formatFrameNumber(1) + ".png";
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
			case ANIM_SCALE:
				// Todo: Works on iphone?
				// Problem: If sprite frame setted here => Shown during 1 frame before animation begins...
				// sprite = CCSprite.sprite(this.getReferenceFirstFrame());
				// If a texture for CCSprite is needed here create a dummy transparent texture?
				sprite = new CCSprite();				
				break;
			case ANIM_SCALE_DIRECT:
			case SINGLE_SCALE:
			case SINGLE_SCALE_DIRECT:
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
			case NONE:
			default:								
				break;
		}
		
		if (sprite != null) {
			this.setSpriteInternal(sprite);
			this.runReferenceAction();
			this.attachToRoot();
			
			this.setPosition(this.position);			
		}
		
		this.postSpriteInit();
	}
	
	protected void postSpriteInit() {		
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

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItem#setPosition(org.cocos2d.types.CGPoint)
	 */
	@Override
	public void setPosition(CGPoint position) {		
		super.setPosition(position);
		if (this.sprite != null) {
			this.sprite.setPosition(this.position);
		}
	}
	
	public void setScale(float scale) {
		if (this.sprite != null) {
			this.sprite.setScale(scale);
		}
	}

	@Override
	public void setSize(float width, float height) {		
		super.setSize(width, height);
		if (this.sprite != null) {
			this.transformTexture();
		}
	}

	@Override
	// In degree
	public void setAngle(float angle) {		
		super.setAngle(angle);
		if (this.sprite != null) {
			this.sprite.setRotation(angle);
		}
	}
	
	public void setSpriteType(SpriteType type) {
		this.spriteType = type;
	}

	
	public String getpList() {
		return pList;
	}

	public void setpList(String pList) {
		this.pList = pList;
	}

	public void setFrameName(String currentFrameName) {
		this.frameName = currentFrameName;
	}
	
	public String getFrameName() {
		return this.frameName;
	}

	public void setFrameCount(int currentFrameCount) {
		this.frameCount = currentFrameCount;
	}
	
	public int getFrameCount() {
		return this.frameCount;
	}

	public SpriteAction getSpriteAction() {
		return spriteAction;
	}

	public void setSpriteAction(SpriteAction spriteAction) {
		this.spriteAction = spriteAction;
	}
}
