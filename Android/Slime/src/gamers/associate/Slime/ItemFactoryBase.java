package gamers.associate.Slime;

import java.util.Hashtable;

import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

public abstract class ItemFactoryBase<T extends GameItem> {	
	protected Hashtable<String, CCAnimation> sharedAnimations;
	protected CCSpriteSheet spriteSheet;	
	protected boolean isInit;	
	protected boolean isAttached;
	protected CCNode rootNode;
	
	protected ItemFactoryBase() {
		this.isInit = false;
		this.isAttached = false;
	}				
	
	protected void initAnimation() {
		if (!this.isInit) {
			CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(this.getPlist());
			spriteSheet = CCSpriteSheet.spriteSheet(this.getPng());			
			
			sharedAnimations = new Hashtable<String, CCAnimation>();
			this.createAnimList();						
			
			isInit = true;
		}
	}
	
	protected abstract void createAnimList();
	
	protected void createAnim(String animName, int frameCount) {		
		this.sharedAnimations.put(animName, GameItem.createAnim(animName, frameCount));
	}
	
	protected abstract String getPlist();
	
	protected abstract String getPng();
	
	public T create() {
		return this.create(0, 0);
	}
	
	public T create(float x, float y) {
		return this.create(x, y, 0, 0);
	}
		
	public T create(float x, float y, float width, float height) {
		if (isAttached) {			
			T item = this.instantiate(x, y, width, height);		
			item.setAnimationList(sharedAnimations);
			this.runFirstAnimations(item);
			return item;
		}
		else
		{
			return null;
		}
	}
	
	protected abstract T instantiate(float x, float y, float width, float height);
	
	protected abstract void runFirstAnimations(T item);
}