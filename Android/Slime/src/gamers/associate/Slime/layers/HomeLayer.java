package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class HomeLayer extends CCLayer {
	private static HomeLayer layer;	
	private CCMenuItemSprite restartMenu;
	
	public static HomeLayer get() {
		if (layer == null) {
			layer = new HomeLayer();
		}
		
		return layer;
	}
	
	protected HomeLayer() {
		super();
		
		// temp
		// ((LevelBuilderGenerator)SlimeFactory.LevelBuilder).getParser().resetStorage();
		
		this.setIsTouchEnabled(true);
//		this.spriteSheet = SpriteSheetFactory.getSpriteSheet("logo", true);
//		this.addChild(this.spriteSheet);
		
//		CCSprite sprite = CCSprite.sprite("slime-attack.png", true);		
		CCSprite sprite = CCSprite.sprite("slime-attack.png");
//		this.spriteSheet.addChild(sprite);
		this.addChild(sprite);
		sprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));
		
		float shiftMenu = - 150f; // Slime height = 160 / 2 + 20
		CCSprite playSprite = CCSprite.sprite("control-play.png", true);
		CCMenuItemSprite playMenu = CCMenuItemSprite.item(playSprite, playSprite, this, "selectPlay");
		CCSprite restartSprite = CCSprite.sprite("control-restart.png", true);
		this.restartMenu = CCMenuItemSprite.item(restartSprite, restartSprite, this, "goRestart");
		restartMenu.setScale(0.5f);									
		
		this.menu = CCMenu.menu(playMenu, this.restartMenu);		
		this.menu.alignItemsHorizontally(50);
		this.menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + shiftMenu
				));			
		
		this.addChild(this.menu);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// TODO: Go to level selection		
		// return super.ccTouchesEnded(event);
		// this.selectPlay(this);
		return true;
		//CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
		//return CCTouchDispatcher.kEventHandled;
	}
	
	private CCMenu menu;
	
	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {		
		super.onEnter();
		this.restartMenu.setVisible(SlimeFactory.LevelBuilder.hasBegun());
		this.restartMenu.setIsEnabled(SlimeFactory.LevelBuilder.hasBegun());
	}
	
	@Override
	public void onExit() {		
		super.onExit();
	}

	public void selectPlay(Object sender) {
		// Sounds.playEffect(R.raw.menuselect);		
		// CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
//		CCTransitionScene transition = CCMoveInRTransition.transition(0.5f, LevelSelection.get().getScene());
//		CCDirector.sharedDirector().replaceScene(transition);
		
		Sounds.playEffect(R.raw.menuselect);
		Sounds.pauseMusic();
		SlimeFactory.LevelBuilder.start();
	}
	
	public void goRestart(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Sounds.pauseMusic();
		SlimeFactory.LevelBuilder.resetAll();
	}
}
