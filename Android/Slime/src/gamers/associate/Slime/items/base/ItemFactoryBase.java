package gamers.associate.Slime.items.base;

import gamers.associate.Slime.game.Level;

import java.util.Hashtable;

import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteSheet;

public abstract class ItemFactoryBase<T extends GameItemCocos> {	
	protected Hashtable<String, CCAnimation> sharedAnimations;
	protected CCSpriteSheet spriteSheet;
	protected int zOrder;
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
	
	protected void createAnim(String animName, int frameCount, float interval) {
		this.sharedAnimations.put(animName, GameItemCocos.createAnim(animName, frameCount, interval));
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
			this.preInit(item);
			this.initItem(item);
			this.runFirstAnimations(item);
			this.level.addItemToAdd(item);			
			return item;
		}
		else
		{
			return null;
		}
	}
	
	public T createBL(float x, float y, float width, float height) {
		return this.create(x + width / 2, y + height / 2, width, height);
	}
	
	protected void preInit(T item) {		
		item.setRootNode(this.getSpriteRootNode(item));
	}
	
	protected void initItem(T item) {
		item.initItem();
	}
	
	protected abstract T instantiate(float x, float y, float width, float height);
	
	protected abstract void runFirstAnimations(T item);
	
	protected CCNode getSpriteRootNode(T item) {
		SpriteType spriteType = item.getSpriteType();
		CCNode node = null;
		switch(spriteType) {
		case UNKNOWN:	
		case ANIM_SCALE:
		case SINGLE_SCALE:
		default:
			node = this.spriteSheet;
			break;
		case ANIM_REPEAT:
		case SINGLE_REPEAT:
		case POLYGON_REPEAT:
			node = this.rootNode;
			break;
		}
		
		return node;
	}
}