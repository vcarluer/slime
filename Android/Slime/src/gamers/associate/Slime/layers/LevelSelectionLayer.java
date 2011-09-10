package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelSelection;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.ccColor3B;

public class LevelSelectionLayer extends CCLayer {
	private CCMenu menu;
	private CCMenu menuCommand;
	
	private static float bgButtonScale = 2;
	private static float originalMenuBgCenter = 82.5f;
	private LevelSelection levelSelection;
	
	public LevelSelectionLayer(LevelSelection selection) {
		this.levelSelection = selection;
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
		CCMenuItemSprite goBackMenu = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goBackEvent");
		
		menuCommand.addChild(goBackMenu);		
		
		for(LevelDefinition levelDef : SlimeFactory.LevelBuilder.getNormalLevels()) {
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
	
	public void goBack() {
		this.goBackEvent(this);
	}
	
	@Override
	public void onEnter() {		
		super.onEnter();
		
		this.levelSelection.activate();
	}

	@Override
	public void onExit() {		
		super.onExit();
		this.levelSelection.desactivate();
	}
	
	public void selectLevel(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCMenuItem item = (CCMenuItem)sender;		
		String levelName = (String)item.getUserData();
		Level level = Level.get(levelName, true);
		Sounds.pauseMusic();
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}

	public void goBackEvent(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level level = Level.get(LevelHome.Id, true);		
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}
	
	private static CCLabel getMenuLabel(String text) {
		CCLabel label = CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 30.0f * bgButtonScale);
		label.setColor(ccColor3B.ccBLACK);
		return label;
	}
}
