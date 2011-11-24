package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

public class EndLevelLayer extends CCLayer {
	private static String scoreTxt = "SCORE: "; 
	private static String endPackTxt = "END OF PACK!";
	private CCMenuItemLabel textLabel;
	private CCMenuItemLabel scoreLabel;
	private CCMenuItemLabel endPackLabel;
	private CCMenu menu;
	private CCMenu menuEndPack;
	private CCMenuItemSprite nextMenu;
	private CCMenuItemSprite homeMenu;
	
	public EndLevelLayer() {		
		this.textLabel = CCMenuItemLabel.item(getMenuLabel("EMPTY"), null, "");
		this.scoreLabel = CCMenuItemLabel.item(getMenuLabel(scoreTxt), null, "");
		this.endPackLabel = CCMenuItemLabel.item(getMenuLabel(endPackTxt), null, "");
		
		CCSprite nextSprite = CCSprite.sprite("control-fastforward.png", true);
		this.nextMenu = CCMenuItemSprite.item(nextSprite, nextSprite, this, "goNext");
		
		CCSprite restartSprite = CCSprite.sprite("control-restart.png", true);
		CCMenuItemSprite restartMenu = CCMenuItemSprite.item(restartSprite, restartSprite, this, "goRestart");
		
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);
		this.homeMenu = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goHome");
		
		this.menu = CCMenu.menu(this.textLabel, this.scoreLabel, this.nextMenu, restartMenu, this.homeMenu);		
		this.menu.alignItemsVertically();
		
		this.menuEndPack = CCMenu.menu(this.textLabel, this.scoreLabel, this.endPackLabel, restartMenu, this.homeMenu);		
		this.menuEndPack.alignItemsVertically();
		
		this.addChild(this.menu);		
		this.addChild(this.menuEndPack);
	}
	
	public void goNext(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.goNext();
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
	
	public void setText(String text) {
		this.textLabel.setString(text.toUpperCase());
	}
	
	public void setScore(String text) {
		this.scoreLabel.setString(text.toUpperCase());
	}
	
	public void setScore(int score) {
		String text = scoreTxt + String.valueOf(score);
		this.setScore(text);
	}
		
	public void enable() {
		CCMenu currentMenu = null;
		Boolean hasNext = Level.currentLevel.hasNext();
		if (hasNext) {
			currentMenu = this.menu;
		}
		else {
			currentMenu = this.menuEndPack;
		}
		
		this.setIsTouchEnabled(true);
		currentMenu.setIsTouchEnabled(true);
		currentMenu.setVisible(true);
		this.setVisible(true);

//		CCAction action = CCFadeIn.action(1.0f);
//		this.menu.runAction(action);
	}
	
	public void disable() {
		this.setIsTouchEnabled(false);
		this.menu.setIsTouchEnabled(false);
		this.menuEndPack.setIsTouchEnabled(false);
		this.menu.setVisible(false);
		this.menuEndPack.setVisible(false);
		this.setVisible(false);
	}
	
	private static CCLabel getMenuLabel(String text) {
		return CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 60.0f);
	}
	
	public void setNextEnabled(boolean value) {
		this.nextMenu.setIsEnabled(value);
		this.nextMenu.setVisible(value);
	}	
	
	public void setHomeEnabled(boolean value) {
		this.homeMenu.setIsEnabled(value);
		this.homeMenu.setVisible(value);
	}
}