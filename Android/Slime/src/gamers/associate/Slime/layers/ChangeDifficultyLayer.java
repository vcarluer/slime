package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.ccColor3B;

public class ChangeDifficultyLayer extends CCLayer {
	private static float padding = 25f;
	private static CCScene scene;
	private CCMenuItemLabel easyMenuLabel;
	CCMenuItemLabel normalMenuLabel;
	CCMenuItemLabel hardMenuLabel;
	CCMenuItemLabel extremMenuLabel;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new ChangeDifficultyLayer());
		}
		
		return scene;
	}
	
	public ChangeDifficultyLayer() {
		int originalW = 1467;		
		int originalH = 800;
		CCSprite spriteBg = CCSprite.sprite("change-difficulty.png");
		spriteBg.setAnchorPoint(0, 0);
		spriteBg.setPosition(0, 0);
		float sW = CCDirector.sharedDirector().winSize().width;
		float sH = CCDirector.sharedDirector().winSize().height;
		// Scale for full width, no deformation
		float scaleW = sW / originalW;
		float scaleH = sH / originalH;
		spriteBg.setScale(Math.max(scaleW, scaleH));
		this.addChild(spriteBg, 0);
		
		this.easyMenuLabel = this.createMenuLabel(LevelDifficulty.Easy, "selectEasy");
		this.normalMenuLabel = this.createMenuLabel(LevelDifficulty.Normal, "selectNormal");
		this.hardMenuLabel = this.createMenuLabel(LevelDifficulty.Hard, "selectHard");
		this.extremMenuLabel = this.createMenuLabel(LevelDifficulty.Extrem, "selectExtrem");
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite goHome = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goHome");
		
		CCMenu menu = CCMenu.menu(this.easyMenuLabel, this.normalMenuLabel, this.hardMenuLabel, this.extremMenuLabel, goHome);
		menu.alignItemsVertically(padding);
		this.addChild(menu);
	}
	
	@Override
	public void onEnter() {		
		this.enableMenu(this.easyMenuLabel, LevelDifficulty.Easy);
		this.enableMenu(this.normalMenuLabel, LevelDifficulty.Normal);
		this.enableMenu(this.hardMenuLabel, LevelDifficulty.Hard);
		this.enableMenu(this.extremMenuLabel, LevelDifficulty.Extrem);
		
		super.onEnter();
	}
	
	private void enableMenu(CCMenuItemLabel label, int diffRef) {
		int diff = SlimeFactory.GameInfo.getMaxLevelDifficulty();
		boolean isEnable = diffRef <= diff;
		label.setIsEnabled(isEnable);
		if (isEnable) {
			label.setColor(ccColor3B.ccc3(255, 255, 255));
		} else {
			label.setColor(ccColor3B.ccc3(125, 125, 125));
		}
	}

	private CCMenuItemLabel createMenuLabel(int diff, String selector) {
		return CCMenuItemLabel.item(this.createLabel(LevelDifficulty.getText(diff)), this, selector);
	}
	
	private CCLabel createLabel(String text) {
		CCLabel label = CCLabel.makeLabel(text, "fonts/Slime.ttf", 60.0f);
		// label.setColor(ccColor3B.ccc3(32,181,79));
		label.setColor(ccColor3B.ccc3(255,255,255));
		return label;
	}
	
	public void selectEasy(Object sender) {
		this.selectLevel(LevelDifficulty.Easy);
	}
	
	public void selectNormal(Object sender) {
		this.selectLevel(LevelDifficulty.Normal);
	}
	
	public void selectHard(Object sender) {
		this.selectLevel(LevelDifficulty.Hard);
	}
	
	public void selectExtrem(Object sender) {
		this.selectLevel(LevelDifficulty.Extrem);
	}
	
	public void goHome(Object sender) {
		Level currentLevel = Level.get(LevelHome.Id, true);								
		CCTransitionScene transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	private void selectLevel(int diff) {
		SlimeFactory.GameInfo.resetDifficulty(diff);
		SlimeFactory.LevelBuilder.resetAll();
		this.goHome(this);
	}
}
