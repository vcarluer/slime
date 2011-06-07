package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.LevelSelection;
import gamers.associate.Slime.items.base.SpriteSheetFactory;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class HomeLayer extends CCLayer {
	private static HomeLayer layer;	
	
	public static HomeLayer get() {
		if (layer == null) {
			layer = new HomeLayer();
		}
		
		return layer;
	}
	
	protected HomeLayer() {
		super();		
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// TODO: Go to level selection		
		return super.ccTouchesEnded(event);
		//CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
		//return CCTouchDispatcher.kEventHandled;
	}
	
	private CCMenu menu;
	
	private CCSpriteSheet spriteSheet;
	
	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {		
		super.onEnter();
		
		//this.setIsTouchEnabled(true);
		this.spriteSheet = SpriteSheetFactory.getSpriteSheet("logo", true);
		this.addChild(this.spriteSheet);
		
		CCSprite sprite = CCSprite.sprite("SlimeTitle.png", true);		
		this.spriteSheet.addChild(sprite);
		sprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));
		
		float shiftMenu = - 100f; // Slime height = 160 / 2 + 20
		CCSprite playSprite = CCSprite.sprite("control-play.png", true);
		CCMenuItemSprite playMenu = CCMenuItemSprite.item(playSprite, playSprite, this, "selectPlay");			
		
		playMenu.setPosition(CGPoint.make(
				0,
				shiftMenu
				));
		
		this.menu = CCMenu.menu(playMenu);
		
		this.addChild(this.menu);										
	}
	
	@Override
	public void onExit() {		
		super.onExit();
	}

	public void selectPlay(Object sender) {
		CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
	}
}
