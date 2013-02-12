package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.LevelSelection;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.custom.MenuSprite;
import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.LevelDefinition;
import gamers.associate.SlimeAttack.levels.LevelHome;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale") public class LevelSelectionLayer extends CCLayer {
	private CCMenu menu;
	private CCMenu menuCommand;
	
	private static float bgButtonScale = 2;
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
		
		menuCommand.setPosition((MenuSprite.Width + 5) / 2, CCDirector.sharedDirector().winSize().height - (MenuSprite.Height + 5) / 2);
		CCSprite homeSpriteN = CCSprite.sprite("control-home.png", true);
		CCSprite homeSpriteS = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite goBackMenu = CCMenuItemSprite.item(homeSpriteN, homeSpriteS, this, "goBackEvent");
		
		menuCommand.addChild(goBackMenu);		
		
		for(LevelDefinition levelDef : SlimeFactory.LevelBuilder.getNormalLevels()) {			
			StoryMenuItem menuItem = StoryMenuItem.item(levelDef, null);
			menuItem.setScale(1 / bgButtonScale);
			menuItem.setUserData(levelDef.getId());
			menuItem.setAnchorPoint(0.5f, 0.5f);
			menu.addChild(menuItem);
		}
		
		menu.alignItemsHorizontally(2f);
		
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
		// levelName should be fixed here
		Level level = Level.get(levelName, true, GamePlay.TimeAttack);
		Sounds.pauseMusic();
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}

	public void goBackEvent(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level level = Level.get(LevelHome.Id, true, GamePlay.None);		
		CCDirector.sharedDirector().replaceScene(level.getScene());
	}
}
