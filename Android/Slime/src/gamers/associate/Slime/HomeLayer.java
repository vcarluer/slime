package gamers.associate.Slime;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
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
		
		//this.setIsTouchEnabled(true);
		CCSpriteSheet spriteSheet = SpriteSheetFactory.getSpriteSheet("logo");
		this.addChild(spriteSheet);
		
		CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("SlimeTitle.png"));
		spriteSheet.addChild(sprite);		
		sprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));
		
		float shiftMenu = - 100f; // Slime height = 160 / 2 + 20
		CCMenuItem playMenu = CCMenuItemLabel.item("Play", this, "selectPlay");
		playMenu.setPosition(CGPoint.make(
				0,
				shiftMenu
				));
		
		CCMenu menu = CCMenu.menu(playMenu);
		
		this.addChild(menu);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// TODO: Go to level selection		
		return super.ccTouchesEnded(event);
		//CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
		//return CCTouchDispatcher.kEventHandled;
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();
	}		
	
	public void selectPlay(Object sender) {
		CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
	}
}
