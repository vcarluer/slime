package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelSelection;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGPoint;

public class HudLayer extends CCLayer {
	private static String Count_Text = "Slimy: ";
	
	private CCLabel countLabel;
	
	public HudLayer() {	
		CCMenuItem itemBack = CCMenuItemLabel.item("Back", this, "goBack");		
		CCMenuItem itemPause = CCMenuItemLabel.item("Pause", this, "goPause");
		itemBack.setPosition(CGPoint.make(-50, CCDirector.sharedDirector().winSize().getHeight() / 2 - 20));
		itemPause.setPosition(CGPoint.make(50, CCDirector.sharedDirector().winSize().getHeight() / 2 - 20));
		
		
		CCMenu menu = CCMenu.menu(itemBack, itemPause);		
		this.addChild(menu);
		
		this.countLabel = CCLabel.makeLabel(Count_Text, "DroidSans", 24);		
		this.addChild(this.countLabel);
		this.countLabel.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() - 100, 
				CCDirector.sharedDirector().winSize().getHeight() - 20));
		this.hideSlimyCount();
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
		Level.currentLevel.togglePause();
	}
	
	public void setSlimyCount(int count) {
		this.countLabel.setVisible(true);
		this.countLabel.setString(Count_Text + String.valueOf(count));
	}
	
	public void hideSlimyCount() {
		this.countLabel.setVisible(false);
	}
}
