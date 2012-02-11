package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelSelection;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.TitleGenerator;
import gamers.associate.Slime.items.custom.MenuSprite;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
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
	
	private CCLabel title;
	
	public HudLayer() {
		
		float pauseScale = PauseLayer.Scale;
		CCSprite pauseSprite = CCSprite.sprite("control-pause.png", true);
		CCMenuItemSprite pauseMenu = CCMenuItemSprite.item(pauseSprite, pauseSprite, this, "goPause");
		pauseMenu.setScale(pauseScale);
		
		float left = - CCDirector.sharedDirector().winSize().getWidth() / 2 + ((MenuSprite.Width * pauseScale) + PauseLayer.PaddingX) / 2 ;
		float top = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * pauseScale) + PauseLayer.PaddingY) / 2;
		pauseMenu.setPosition(CGPoint.make(left, top));
		
		
		this.menu = CCMenu.menu(pauseMenu);		
		this.addChild(this.menu);
		
		this.countLabel = getMenuLabel(Count_Text);		
		this.countLabel.setAnchorPoint(0, 0);
		this.addChild(this.countLabel);
		this.countLabel.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() - 15, 
				CCDirector.sharedDirector().winSize().getHeight() - 65));
				
		this.hideSlimyCount();
		
		this.title = getMenuLabel(TitleGenerator.generateNewTitle(), 45f);
		this.title.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() / 2));
		this.addChild(this.title);
	}
	
	private static CCLabel getMenuLabel(String text) {
		return getMenuLabel(text, 60f);
	}
	
	private static CCLabel getMenuLabel(String text, float size) {
		return CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", size);
	}
	
	@Override
	public void onEnter() {		
		super.onEnter();
		this.title.setVisible(true);
		this.title.setString(TitleGenerator.generateNewTitle());
		CCFadeIn in = CCFadeIn.action(0f);
		CCDelayTime delay = CCDelayTime.action(3f);
		CCCallFunc call = CCCallFunc.action(this, "fadeTitle");
		CCSequence seq = CCSequence.actions(in, delay, call);
		this.title.runAction(seq);
	}
	
	public void fadeTitle() {
		CCFadeOut fade = CCFadeOut.action(1f);
		this.title.runAction(fade);
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
	
	public void setHudStartText(String text) {
		this.countLabel.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() - 15 - this.countLabel.getContentSize().width, 
				CCDirector.sharedDirector().winSize().getHeight() - 65));		
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
