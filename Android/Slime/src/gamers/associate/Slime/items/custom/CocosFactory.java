package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.GameItemCocosFactory;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.items.base.SpriteType;
import gamers.associate.Slime.items.base.TextureAnimation;

import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;

public class CocosFactory extends GameItemCocosFactory<GameItemCocos> {
	private String currentPlistName;
	private String currentFrameName;
	private int currentFrameCount;
	private SpriteAction spriteAction;
	private CCAnimation animation;
	
	@Override
	protected void initItem(GameItemCocos item) {
		item.setpList(this.currentPlistName);
		item.setFrameName(this.currentFrameName);
		item.setFrameCount(this.currentFrameCount);
		item.setSpriteAction(this.spriteAction);
		super.initItem(item);
	}

	@Override
	protected void createAnimList() {		
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected GameItemCocos instantiate(float x, float y, float width,
			float height) {
		return new GameItemCocos(x, y, width, height, Level.zTop);
	}

	@Override
	protected void runFirstAnimations(GameItemCocos item) {
		if (this.animation != null) {
			item.getSprite().runAction(CCAnimate.action(this.animation));
		}
		
		if (this.spriteAction != null) {
			this.spriteAction.apply(item.getSprite());
		}
	}
	
	public GameItemCocos create(String name, float x, float y, float width, float height, String plist, String frameName, int frameCount) {		
		this.currentFrameName = frameName;
		this.currentPlistName = plist;
		this.currentFrameCount = frameCount;
		this.createDynamicAnimList();
		return this.create(name, x, y, width, height);
	}
	
	public GameItemCocos createBL(String name, float x, float y, float width, float height, String plist, String frameName, int frameCount) {		
		this.currentFrameName = frameName;
		this.currentPlistName = plist;
		this.currentFrameCount = frameCount;
		this.createDynamicAnimList();
		return this.create(name, x + width / 2, y + height / 2, width, height);
	}
	
	private void createDynamicAnimList() {
		this.spriteAction = null;
		this.animation = null; 		
		if (this.currentPlistName.length() == 0) {
			this.spriteSheet = null;
			TextureAnimation.createFramesFromFiles(this.currentFrameName, this.currentFrameCount);
		} else {
			this.spriteSheet = SpriteSheetFactory.getSpriteSheet(this.currentPlistName);
		}
		
		if (currentFrameCount > 1) {			
			this.animation = this.createAnim(this.currentFrameName, this.currentFrameCount);
		}
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.ItemFactoryBase#preInit(gamers.associate.Slime.items.base.GameItemCocos)
	 */
	@Override
	protected void preInit(GameItemCocos item) {		
		super.preInit(item);
		item.setReferenceAnimationName(this.currentFrameName);
		item.setSpriteType(SpriteType.SINGLE_SCALE);
	}

	public GameItem createBL(String name, float x, float y, float width, float height,
			String plist, String frame, int count, SpriteAction action) {
		this.currentFrameName = frame;
		this.currentPlistName = plist;
		this.currentFrameCount = count;
		this.createDynamicAnimList();
		
		this.spriteAction = action;		
		return this.create(name, x + width / 2, y + height / 2, width, height);
	}
}
