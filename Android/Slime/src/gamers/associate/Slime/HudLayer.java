package gamers.associate.Slime;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

public class HudLayer extends CCLayer {
	
	public HudLayer() {
		CCMenuItem itemBack = CCMenuItemLabel.item("Back", this, "goBack");		
		CCMenuItem itemPause = CCMenuItemLabel.item("Pause", this, "goPause");
		itemBack.setPosition(CGPoint.make(-50, CCDirector.sharedDirector().winSize().getHeight() / 2 - 20));
		itemPause.setPosition(CGPoint.make(50, CCDirector.sharedDirector().winSize().getHeight() / 2 - 20));
		
		
		CCMenu menu = CCMenu.menu(itemBack, itemPause);		
		this.addChild(menu);
	}
	
	public void goBack(Object sender) {
		CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
	}
	
	public void goPause(Object sender) {
		Level.currentLevel.togglePause();
	}
}
