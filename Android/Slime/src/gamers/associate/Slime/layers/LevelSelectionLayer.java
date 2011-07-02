package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.HardCodedLevelBuilder;
import gamers.associate.Slime.game.Level;
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

public class LevelSelectionLayer extends CCLayer {
	private CCMenu menu;
	private CCMenu menuCommand;
	
	public LevelSelectionLayer() {
		menu = CCMenu.menu();
		menuCommand = CCMenu.menu();
		
		menuCommand.setPosition((127 + 5) / 2, (91 + 5) / 2);
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite goBackMenu = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goBack");
		
		menuCommand.addChild(goBackMenu);		
		
		for(LevelDefinition levelDef : HardCodedLevelBuilder.getNormalLevels()) {
			CCMenuItem levelItem = CCMenuItemLabel.item(getMenuLabel(levelDef.getId()), this, "selectLevel");
			levelItem.setUserData(levelDef.getId());
			menu.addChild(levelItem);
		}
				
		menu.alignItemsInColumns(new int[] { 7, 7 });
		
		this.addChild(menuCommand);
		this.addChild(menu);
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
		Level level = Level.get(LevelHome.id, true);		
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}
	
	private static CCLabel getMenuLabel(String text) {
		return CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 30.0f);
	}
}
