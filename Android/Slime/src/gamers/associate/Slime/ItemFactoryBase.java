package gamers.associate.Slime;

import java.util.Hashtable;

import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteSheet;

public abstract class ItemFactoryBase<T extends GameItemCocos> {	
	protected Hashtable<String, CCAnimation> sharedAnimations;
	protected CCSpriteSheet spriteSheet;	
	protected boolean isInit;	
	protected boolean isAttached;
	protected CCNode rootNode;
	protected float ratio;
	protected Level level;
	
	protected ItemFactoryBase() {
		this.isInit = false;
		this.isAttached = false;
		this.ratio = 1.0f;
	}
	
	protected void initAnimation() {
		if (!this.isInit) {
			this.spriteSheet = SpriteSheetFactory.getSpriteSheet(this.getPlistPng());
			
			this.sharedAnimations = new Hashtable<String, CCAnimation>();
			this.createAnimList();						
			
			this.isInit = true;
		}
	}
	
	protected abstract void createAnimList();
	
	protected void createAnim(String animName, int frameCount) {		
		this.sharedAnimations.put(animName, GameItemCocos.createAnim(animName, frameCount));
	}
	
	protected abstract String getPlistPng();
	
	public void destroy() {
		this.isInit = false;
	}
	
	public T create() {
		return this.create(0, 0);
	}
	
	public T create(float x, float y) {
		return this.create(x, y, 0, 0);
	}
		
	public T create(float x, float y, float width, float height) {
		if (this.isAttached) {			
			T item = this.instantiate(x, y, width, height);
			item.setAnimationList(this.sharedAnimations);
			this.runFirstAnimations(item);
			this.level.addItemToAdd(item);			
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