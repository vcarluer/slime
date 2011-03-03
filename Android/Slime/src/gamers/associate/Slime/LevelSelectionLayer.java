package gamers.associate.Slime;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;

public class LevelSelectionLayer extends CCLayer {
	
	public LevelSelectionLayer() {
		CCMenuItem goBackMenu = CCMenuItemLabel.item("Back", this, "goBack");
		CCMenuItem testMenu1 = CCMenuItemLabel.item("Level 1", this, "doTest");		
		CCMenuItem testMenu2 = CCMenuItemLabel.item("Level 2", this, "doTest2");
		CCMenuItem testMenu3 = CCMenuItemLabel.item("Level 3", this, "doTest");				
		CCMenu menu = CCMenu.menu(goBackMenu, testMenu3, testMenu2, testMenu1);
		menu.alignItemsInColumns(new int[] { 1, 3 });
		this.addChild(menu);
	}
	
	public void doTest(Object sender) {
		Level level = Level.get(Level.LEVEL_1, true);
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}
	
	public void doTest2(Object sender) {
		Level level = Level.get(Level.LEVEL_2, true);
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}
	
	public void goBack(Object sender) {
		Level level = Level.get(Level.LEVEL_HOME, true);		
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}
}
