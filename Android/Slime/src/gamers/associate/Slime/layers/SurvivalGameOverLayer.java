package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.Sharer;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;

import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

public class SurvivalGameOverLayer extends CCLayer {
	private static CCScene scene;
	private CCLabel scoreLabel;
	private CCLabel gameOverLabel;
	private CCMenu backMenu;
	private CCLabel newHighScore;
	private CCLabel newUnlock;
	private CCMenu shareMenu;
	
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
		this.scoreLabel = CCLabel.makeLabel("Score: ".toUpperCase(), "fonts/Slime.ttf", 42 * SlimeFactory.Density);
		this.gameOverLabel = CCLabel.makeLabel("Game Over!".toUpperCase(), "fonts/Slime.ttf", 68 * SlimeFactory.Density);
		this.gameOverLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + 100 * SlimeFactory.Density);
		this.scoreLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY());
		this.newHighScore = CCLabel.makeLabel(" ", "fonts/Slime.ttf", 42f* SlimeFactory.Density);
		this.newHighScore.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() - 90 * SlimeFactory.Density);
		this.newHighScore.setRotation(-15f);
		this.newHighScore.setColor(ccColor3B.ccc3(255, 0, 0));
		
		this.newUnlock = CCLabel.makeLabel(" ", "fonts/Slime.ttf", 32f* SlimeFactory.Density);
		this.newUnlock.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + 45 * SlimeFactory.Density);
		this.newUnlock.setColor(SlimeFactory.ColorSlimeLight);
		
		this.addChild(this.gameOverLabel);
		this.addChild(this.scoreLabel);
		this.addChild(this.newHighScore);
		this.addChild(this.newUnlock);
		
		this.addChild(this.backMenu);
		this.setIsTouchEnabled(true);
	}
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		this.goBack();
		return true;
	}
	
	@Override
	public void onEnter() {
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
					1.0f, - this.scoreLabel.getContentSize().width / 2f - 25 - HomeLayer.shareSizeW / 2f, 0);
		} else {
			this.shareMenu = HomeLayer.getNewShareButton(
					"New mode unlocked in Slime Attack survival mode: " + modeText + "! " + String.valueOf(SlimeFactory.GameInfo.getCurrentScore()) + " in " + LevelDifficulty.getText(SlimeFactory.GameInfo.getDifficulty()) + " " + Sharer.twitterTag, 
					1.0f, - this.scoreLabel.getContentSize().width / 2f - 25 - HomeLayer.shareSizeW / 2f, 0);
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

	protected void goBack() {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseSurvivalDifficultyLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
