package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelSelection;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

public class PauseLayer extends CCLayer {

	private CCMenu menu;
	
	public PauseLayer() {
		CCMenuItem label = CCMenuItemLabel.item(getMenuLabel("Pause"), this, "");		
		
		CCSprite resumeSprite = CCSprite.sprite("control-play.png", true);
		CCMenuItemSprite resumeMenu = CCMenuItemSprite.item(resumeSprite, resumeSprite, this, "goResume");
		
		CCSprite restartSprite = CCSprite.sprite("control-restart.png", true);
		CCMenuItemSprite restartMenu = CCMenuItemSprite.item(restartSprite, restartSprite, this, "goRestart");
		
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite homeMenu = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goHome");
		
		this.menu = CCMenu.menu(label, resumeMenu, restartMenu, homeMenu);
		this.menu.alignItemsVertically();
		
		this.addChild(this.menu);
	}
	
	public void goResume(Object sender) {
		Level.currentLevel.resume();
	}
	
	public void goRestart(Object sender) {
		Level.currentLevel.reload();
	}

	public void goHome(Object sender) {
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
