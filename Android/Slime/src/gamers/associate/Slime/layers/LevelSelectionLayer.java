package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.HardCodedLevelBuilder;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelHome;

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
import org.cocos2d.types.ccColor3B;

public class LevelSelectionLayer extends CCLayer {
	private CCMenu menu;
	private CCMenu menuCommand;
	
	private static float bgButtonScale = 2;
	private static float originalMenuBgCenter = 82.5f;
	
	public LevelSelectionLayer() {
		int originalW = 800;		
		CCSprite spriteBg = CCSprite.sprite("splash-level.png");
		spriteBg.setAnchorPoint(0, 0);
		spriteBg.setPosition(0, 0);
		float sW = CCDirector.sharedDirector().winSize().width;
		// Scale for full width, no deformation
		float scale = sW / originalW;
		spriteBg.setScale(scale);
		this.addChild(spriteBg, 0);
		
		menu = CCMenu.menu();
		menuCommand = CCMenu.menu();
		
		menuCommand.setPosition((MenuSprite.Width + 5) / 2, (MenuSprite.Height + 5) / 2);
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite goBackMenu = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goBack");
		
		menuCommand.addChild(goBackMenu);		
		
		for(LevelDefinition levelDef : HardCodedLevelBuilder.getNormalLevels()) {
			CCSprite menuSprite = CCSprite.sprite("control-square-screen.png", true);			
			CCMenuItemSprite menuItemSprite = CCMenuItemSprite.item(menuSprite, menuSprite, this, "selectLevel");
			menuItemSprite.setScale(1 / bgButtonScale);
			menuItemSprite.setUserData(levelDef.getId());
			menuItemSprite.setAnchorPoint(0.5f, 0.5f);
			// CCMenuItemLabel levelItem = CCMenuItemLabel.item(getMenuLabel(levelDef.getId()), this, "selectLevel");			
			CCLabel levelItem = getMenuLabel(levelDef.getId());
			levelItem.setAnchorPoint(0.5f, 0.5f);
			levelItem.setPosition(originalMenuBgCenter, originalMenuBgCenter);
			menuItemSprite.addChild(levelItem);
			// levelItem.setUserData(levelDef.getId());
			
			menu.addChild(menuItemSprite);
		}
				
		menu.alignItemsInColumns(new int[] { 7, 7 });
		
		this.addChild(menuCommand, 1);
		this.addChild(menu, 1);
	}		
	
	@Override
	public void onEnter() {		
		super.onEnter();
	}

	@Override
	public void onExit() {		
		super.onExit();
	}
	
	public void selectLevel(Object sender) {
		CCMenuItem item = (CCMenuItem)sender;		
		String levelName = (String)item.getUserData();
		Level level = Level.get(levelName, true);
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}

	public void goBack(Object sender) {
		Level level = Level.get(LevelHome.Id, true);		
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}
	
	private static CCLabel getMenuLabel(String text) {
		CCLabel label = CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 30.0f * bgButtonScale);
		label.setColor(ccColor3B.ccBLACK);
		return label;
	}
}
