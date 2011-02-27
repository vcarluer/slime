package gamers.associate.Slime;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

public class HudLayer extends CCLayer {
	
	public HudLayer() {
		CCMenuItem itemBack = CCMenuItemLabel.item("back", this, "goBack");
		itemBack.setPosition(CGPoint.make(0, CCDirector.sharedDirector().winSize().getHeight() / 2 - 20));
		CCMenu menu = CCMenu.menu(itemBack);
		this.addChild(menu);
	}
	
	public void goBack(Object sender) {
		CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
	}
}
