package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelBuilderGenerator;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.Vibe;
import gamers.associate.Slime.levels.GamePlay;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

public class ChooseSurvivalDifficultyLayer extends CCLayer {
	private static final float menuPadding = 75f;
	private static final float iconSizeReference = 87f;
	private static float padding = 25f;
	private static float iconSize = 70f;
	private static float iconPadding = 50f;
	private static CCScene scene;
	private CCMenuItemLabel easyMenuLabel;
	CCMenuItemLabel normalMenuLabel;
	CCMenuItemLabel hardMenuLabel;
	CCMenuItemLabel extremMenuLabel;
	private CCMenu menu;
	CGPoint tmp;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new ChooseSurvivalDifficultyLayer());
		}
		
		return scene;
	}
	
	public ChooseSurvivalDifficultyLayer() {
		tmp = CGPoint.zero();
		HomeLayer.addBkgChangeDiff(this);
		
		CCMenuItemLabel title = CCMenuItemLabel.item(this.createLabel("Difficulty:"), this, ""); // generates a warning in log due to ""
		title.setAnchorPoint(0, 0.5f);
		this.easyMenuLabel = this.createMenuLabel(LevelDifficulty.Easy, "selectEasy");
		this.normalMenuLabel = this.createMenuLabel(LevelDifficulty.Normal, "selectNormal");
		this.hardMenuLabel = this.createMenuLabel(LevelDifficulty.Hard, "selectHard");
		this.extremMenuLabel = this.createMenuLabel(LevelDifficulty.Extrem, "selectExtrem");
		
		this.addChild(HomeLayer.getBackButton(this, "goBack"));
		
		this.menu = CCMenu.menu(title, this.easyMenuLabel, this.normalMenuLabel, this.hardMenuLabel, this.extremMenuLabel);
		this.menu.alignItemsVertically(padding);
		this.menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2 - menuPadding,
				CCDirector.sharedDirector().winSize().getHeight() / 2
				));
		this.addChild(this.menu);				
	}
	
	@Override
	public void onEnter() {		
		this.enableMenu(this.easyMenuLabel, LevelDifficulty.Easy);
		this.enableMenu(this.normalMenuLabel, LevelDifficulty.Normal);
		this.enableMenu(this.hardMenuLabel, LevelDifficulty.Hard);
		this.enableMenu(this.extremMenuLabel, LevelDifficulty.Extrem);
		
		SlimeFactory.ContextActivity.showAndNextAd();
		super.onEnter();
	}
	
	@Override
	public void onExit() {
		SlimeFactory.ContextActivity.hideAd();
		super.onExit();
	}

	private void enableMenu(CCMenuItemLabel label, int diffRef) {
		int diff = SlimeFactory.GameInfo.getMaxLevelDifficulty();
		boolean isEnable = diffRef <= diff;
		label.setIsEnabled(isEnable);		
		if (isEnable) {															
			label.setColor(ccColor3B.ccWHITE);
		} else {			
			label.setColor(SlimeFactory.ColorSlimeBorder);
		}
		
		tmp = CGPoint.ccpAdd(this.menu.getPosition(), label.getPosition());
		CCSprite spr = this.getLevelSprite(diffRef, isEnable);
		spr.setPosition(tmp.x - iconPadding - (iconSize / 2), tmp.y + (iconSize - 60f));
		this.addChild(spr);
	}
	
	public static CCSprite getLevelSprite(int diff, boolean isEnable) {
		String suf = "";
		if (!isEnable) {
			suf = "-grey";
		}
		String diffTxt = LevelDifficulty.getText(diff).toLowerCase();
		CCSprite spr = CCSprite.sprite("mode-" + diffTxt + suf + "-01.png");
		spr.setScale(iconSize / iconSizeReference);
		
		return spr;
	}

	private CCMenuItemLabel createMenuLabel(int diff, String selector) {
		CCMenuItemLabel label = CCMenuItemLabel.item(this.createLabel(LevelDifficulty.getText(diff)), this, selector);
		label.setAnchorPoint(0, 0.5f);
		return label;
	}
	
	private CCLabel createLabel(String text) {
		CCLabel label = CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 60.0f);
		// label.setColor(ccColor3B.ccc3(32,181,79));
		label.setColor(ccColor3B.ccWHITE);
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
	
	public void goBack(Object sender) {
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	private void selectLevel(int diff) {
		SlimeFactory.GameInfo.resetDifficulty(diff);
		SlimeFactory.LevelBuilder.resetAll();
		Level level = Level.get(LevelBuilderGenerator.defaultId, true, GamePlay.Survival);
		
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, level.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
		
		Vibe.vibrate();
		Sounds.playEffect(R.raw.menuselect, true);
		Sounds.stopMusic();

	}
}
