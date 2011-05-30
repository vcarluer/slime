package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.SpriteSheetFactory;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;

public class LevelSelectionLayer extends CCLayer {
	
	public LevelSelectionLayer() {
		
		CCSpriteSheet menuSheet = SpriteSheetFactory.getSpriteSheet("controls");
		this.addChild(menuSheet);
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);		
		
		CCMenuItemSprite goBackMenu = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goBack");
				
		CCMenuItem testMenu1 = CCMenuItemLabel.item(getMenuLabel("Level 1"), this, "doTest");		
		CCMenuItem testMenu2 = CCMenuItemLabel.item(getMenuLabel("Level 2"), this, "doTest2");
		CCMenuItem testMenu3 = CCMenuItemLabel.item(getMenuLabel("Level 3"), this, "doTest");				
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
	
	private static CCLabel getMenuLabel(String text) {
		return CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 30.0f);
	}
}
