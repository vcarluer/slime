package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.custom.MenuSprite;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class PauseLayer extends CCLayer {
	private static String scoreTxt = "MAX: ";
	private CCMenu menu;
	private CCMenuItemLabel scoreMaxLabel;
	private float scale = 0.5f;
	private float padding = 5f;
	
	public PauseLayer() {
//		CCMenuItem label = CCMenuItemLabel.item(getMenuLabel("Pause"), this, "");		
//		
//		this.scoreMaxLabel = CCMenuItemLabel.item(getMenuLabel(scoreTxt), null, "");
		
		CCSprite resumeSprite = CCSprite.sprite("control-play.png", true);
		CCMenuItemSprite resumeMenu = CCMenuItemSprite.item(resumeSprite, resumeSprite, this, "goResume");
		this.setMenuPos(resumeMenu, 1);
		
		CCSprite restartSprite = CCSprite.sprite("control-restart.png", true);
		CCMenuItemSprite restartMenu = CCMenuItemSprite.item(restartSprite, restartSprite, this, "goRestart");		
		this.setMenuPos(restartMenu, 2);
		
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite homeMenu = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goHome");
		this.setMenuPos(homeMenu, 3);
		
		this.menu = CCMenu.menu(resumeMenu, restartMenu, homeMenu);
		// this.menu.alignItemsHorizontally();				
		
		this.addChild(this.menu);
	}
	
	private void setMenuPos(CCMenuItem menuItem, int count) {
		menuItem.setScale(this.scale);
		
		float left = - CCDirector.sharedDirector().winSize().getWidth() / 2 + ((MenuSprite.Width * this.scale) + this.padding) / 2 + (MenuSprite.Width * this.scale + this.padding) * (count - 1);
		float top = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * this.scale) + 5) / 2;
		menuItem.setPosition(CGPoint.make(left, top));
	}
	
	public void setMaxScore(String text) {
		if (this.scoreMaxLabel != null) {
			this.scoreMaxLabel.setString(text.toUpperCase());
		}
	}
	
	public void setMaxScore(int scoreMax) {
		if (this.scoreMaxLabel != null) {
			String text = scoreTxt + String.valueOf(scoreMax);
			this.setMaxScore(text);
		}
	}
	
	public void goResume(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.resume();
	}
	
	public void goRestart(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.reload();
	}

	public void goHome(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.goHome();
	}
	
	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();		
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onExit()
	 */
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		super.onExit();
	}
	
	public void enable() {
		this.setIsTouchEnabled(true);
		this.setVisible(true);
		this.menu.setIsTouchEnabled(true);
	}
	
	public void disable() {
		this.setIsTouchEnabled(false);
		this.setVisible(false);
		this.menu.setIsTouchEnabled(false);
	}
	
	private static CCLabel getMenuLabel(String text) {
		return CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 60.0f);
	}
}
