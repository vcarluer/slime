package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelSelection;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.custom.MenuSprite;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class HudLayer extends CCLayer {
	private static String Count_Text = "Hud";
	
	private CCLabel countLabel;
	
	private CCMenu menu;
	
	public HudLayer() {
		
		float pauseScale = 0.5f;
		CCSprite pauseSprite = CCSprite.sprite("control-pause.png", true);
		CCMenuItemSprite pauseMenu = CCMenuItemSprite.item(pauseSprite, pauseSprite, this, "goPause");
		pauseMenu.setScale(pauseScale);
		
		float left = - CCDirector.sharedDirector().winSize().getWidth() / 2 + ((MenuSprite.Width * pauseScale) + 5) / 2 ;
		float top = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * pauseScale) + 5) / 2;
		pauseMenu.setPosition(CGPoint.make(left, top));
		
		
		this.menu = CCMenu.menu(pauseMenu);		
		this.addChild(this.menu);
		
		this.countLabel = getMenuLabel(Count_Text);		
		this.countLabel.setAnchorPoint(0, 0);
		this.addChild(this.countLabel);
		this.countLabel.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() - 65, 
				CCDirector.sharedDirector().winSize().getHeight() - 65));
		this.hideSlimyCount();
	}
	
	private static CCLabel getMenuLabel(String text) {
		return CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 60.0f);
	}
	
	@Override
	public void onEnter() {		
		super.onEnter();				
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		super.onExit();
	}

	public void goBack(Object sender) {
		CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
	}
	
	public void goPause(Object sender) {
		Sounds.playEffect(R.raw.menuselect);		
		Level.currentLevel.pause();
	}
	
	public void setSlimyCount(int count) {
		this.countLabel.setVisible(true);
		this.countLabel.setString(Count_Text + String.valueOf(count));
	}
	
	public void setHudText(String text) {
		this.countLabel.setVisible(true);
		this.countLabel.setString(text);		
	}
	
	public void hideHudText() {
		if (this.countLabel != null) {
			this.countLabel.setVisible(false);
		}
	}
	
	public CCLabel getLabel() {
		return this.countLabel;
	}
	
	public void hideSlimyCount() {
		this.countLabel.setVisible(false);
	}
	
	public CCMenu getMenu() {
		return this.menu;
	}
}
