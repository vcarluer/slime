package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.LevelDifficulty;
import gamers.associate.SlimeAttack.game.Sharer;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.custom.MenuSprite;

import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;

import android.annotation.SuppressLint;
import android.view.MotionEvent;

@SuppressLint("DefaultLocale") public class SurvivalGameOverLayer extends CCLayer implements IBackableLayer {
	private static final float SharePadding = 25f * SlimeFactory.getWidthRatio(); 
	private static CCScene scene;
	private CCLabel scoreLabel;
	private CCLabel gameOverLabel;
	private CCMenu backMenu;
	private CCLabel newHighScore;
	private CCLabel newUnlock;
	private CCMenu shareMenu;
	private CCMenu restartMenu;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new SurvivalGameOverLayer());
		}
		
		return scene;
	}
	
	public SurvivalGameOverLayer() {
		HomeLayer.addBkg(this, 800, 480, "game-over.png");
		this.backMenu = HomeLayer.getBackButton(this, "goBack");
		this.scoreLabel = CCLabel.makeLabel("Score: ".toUpperCase(), "fonts/Slime.ttf", 42 * SlimeFactory.getWidthRatio());
		this.gameOverLabel = CCLabel.makeLabel("Game Over!".toUpperCase(), "fonts/Slime.ttf", 68 * SlimeFactory.getWidthRatio());
		this.gameOverLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + 100 * SlimeFactory.getWidthRatio());
		this.scoreLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY());
		this.newHighScore = CCLabel.makeLabel(" ", "fonts/Slime.ttf", 42f* SlimeFactory.getWidthRatio());
		this.newHighScore.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() - 90 * SlimeFactory.getWidthRatio());
		this.newHighScore.setRotation(-15f);
		this.newHighScore.setColor(SlimeFactory.ColorSlimeGold);
		
		this.newUnlock = CCLabel.makeLabel(" ", "fonts/Slime.ttf", 32f* SlimeFactory.getWidthRatio());
		this.newUnlock.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + 45 * SlimeFactory.getWidthRatio());
		this.newUnlock.setColor(SlimeFactory.ColorSlimeLight);
		
		float left = - CCDirector.sharedDirector().winSize().getWidth() / 2 + ((MenuSprite.Width * PauseLayer.Scale) + PauseLayer.PaddingX) + ((MenuSprite.Width * PauseLayer.Scale) + PauseLayer.PaddingX) / 2 ;
		float top = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * PauseLayer.Scale) + PauseLayer.PaddingY) / 2;
		this.restartMenu = HomeLayer.getMenuButton(
				"control-restart.png", 
				left, 
				top, 
				this, 
				"restart");
		// this.restartMenu.setScale(2.0f);		
		
		this.addChild(this.gameOverLabel);
		this.addChild(this.scoreLabel);
		this.addChild(this.newHighScore);
		this.addChild(this.newUnlock);
		this.addChild(this.restartMenu);
		
		this.addChild(this.backMenu);
		this.setIsTouchEnabled(true);
	}
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// this.goBack();
		return true;
	}
	
	@Override
	public void onEnter() {
		SlimeFactory.playMenuMusic();
		this.scoreLabel.setString(("Score: " + String.valueOf(SlimeFactory.GameInfo.getCurrentScore())).toUpperCase());
		if (SlimeFactory.GameInfo.isLastHighScore()) {
			this.newHighScore.setString("New high score!".toUpperCase());
			this.newHighScore.setScale(10);
			CCScaleTo scaleTo = CCScaleTo.action(0.7f, 1.0f);
			this.newHighScore.runAction(scaleTo);
		} else {
			this.newHighScore.setString(" ");
		}
		
		boolean newMode = false;
		String modeText = "";
		if (SlimeFactory.GameInfo.consumeNewUnlockSurvival()) {
			newMode = true;
			modeText = LevelDifficulty.getText(SlimeFactory.GameInfo.getUnlockedSurvival());
			this.newUnlock.setString(("New mode unlocked! " + modeText).toUpperCase());
			this.newUnlock.setScale(10);
			CCScaleTo scaleTo = CCScaleTo.action(0.3f, 1.0f);
			this.newUnlock.runAction(scaleTo);
		} else {
			this.newUnlock.setString(" ");
		}
		
		if (!newMode) {
			this.shareMenu = HomeLayer.getNewShareButton(
					"New high score in Slime Attack survival mode! " + String.valueOf(SlimeFactory.GameInfo.getCurrentScore()) + " in " + LevelDifficulty.getText(SlimeFactory.GameInfo.getDifficulty()) + " " + Sharer.twitterTag, 
					1.0f, - this.scoreLabel.getContentSizeRef().width / 2f - SharePadding - (HomeLayer.shareSizeW * HomeLayer.getShareScale(1.0f)) / 2f, 0);
		} else {
			this.shareMenu = HomeLayer.getNewShareButton(
					"New mode unlocked in Slime Attack survival mode: " + modeText + "! " + String.valueOf(SlimeFactory.GameInfo.getCurrentScore()) + " in " + LevelDifficulty.getText(SlimeFactory.GameInfo.getDifficulty()) + " " + Sharer.twitterTag, 
					1.0f, - this.scoreLabel.getContentSizeRef().width / 2f - SharePadding - (HomeLayer.shareSizeW * HomeLayer.getShareScale(1.0f)) / 2f, 0);
		}
		
		this.addChild(this.shareMenu);
		
		super.onEnter();
	}
	
	@Override
	public void onExit() {
		if (this.shareMenu != null) {
			this.removeChild(this.shareMenu, true);
			this.shareMenu = null;
		}
		super.onExit();
	}

	public void goBack(Object sender) {
		goBack();
	}

	public void goBack() {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseSurvivalDifficultyLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	public void restart(Object sender) {
		if (SlimeFactory.GameInfo.getCurrentSurvival() != null) {
			SlimeFactory.GameInfo.getCurrentSurvival().select();
		}
	}
}
