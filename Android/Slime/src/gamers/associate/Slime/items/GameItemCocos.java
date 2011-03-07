package gamers.associate.Slime.items;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CCTexParams;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

/**
 * @author  vince
 */
public abstract class GameItemCocos extends GameItem {	
	protected Hashtable<String, CCAnimation> animationList;
	protected CCAction currentAction;
	protected CCSprite sprite;
	protected CCNode rootNode;
	protected CCTexture2D texture;
		
	public GameItemCocos(CCNode node, float x, float y, float width, float height) {
		super(x, y, width, height);		
		this.animationList = new Hashtable<String, CCAnimation>();				
		this.rootNode = node;
		this.textureMode = TextureMode.Scale;
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
	
	protected TextureMode textureMode;
	
	protected void transformTexture() {
		if (this.textureMode == TextureMode.Scale) {
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
		
		/*if (this.textureMode == TextureMode.Clip) {
			if (this.width != 0 && this.height != 0) {
				CGRect rect = this.sprite.getTextureRect();
				rect.size = CGSize.make(this.width, this.height);
				this.sprite.setTextureRect(rect);
			}
		}*/
	}
	
	public void setTexture(CCTexture2D texture) {
		this.texture = texture;	
	}

	@Override
	public void draw(GL10 gl) {
		if (this.textureMode == TextureMode.Clip) {
			if (this.width != 0 && this.height != 0) {
				if (this.texture != null) {
					CGRect rect = CGRect.make(this.position.x - this.width / 2, this.position.y - this.height / 2, this.width, this.height);
					this.texture.drawRepeatInRect(gl, rect);
				}
			}
		}
		
		super.draw(gl);
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
			this.angle = this.sprite.getRotation();
			if (this.textureMode == TextureMode.Clip) {
				this.transformTexture();
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
			String frameName = animName + "-" + String.valueOf(i + 1) + ".png";
			CCSpriteFrame frame = CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(frameName);
			if (frame != null) {
				animArray.add(frame);
			}
		}
		
		CCAnimation animation = CCAnimation.animation(animName, interval, animArray);
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
