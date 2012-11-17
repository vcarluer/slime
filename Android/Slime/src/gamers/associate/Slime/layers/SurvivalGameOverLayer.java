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

public class SurvivalGameOverLayer extends CCLayer {
	private static CCScene scene;
	private CCLabel scoreLabel;
	private CCLabel gameOverLabel;
	private CCMenu backMenu;
	private CCLabel newHighScore;
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
		this.newHighScore.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() - 100 * SlimeFactory.Density);
		this.newHighScore.setRotation(-15f);
		this.newHighScore.setColor(ccColor3B.ccc3(255, 0, 0));
		
		this.addChild(this.gameOverLabel);
		this.addChild(this.scoreLabel);
		this.addChild(this.newHighScore);
		
		this.addChild(this.backMenu);
	}
	@Override
	public void onEnter() {
		this.scoreLabel.setString(("Score: " + String.valueOf(SlimeFactory.GameInfo.getCurrentScore())).toUpperCase());
		if (SlimeFactory.GameInfo.isLastHighScore()) {
			this.newHighScore.setString("New high score!".toUpperCase());
			this.newHighScore.setScale(10);
			CCScaleTo scaleTo = CCScaleTo.action(0.7f, 1.0f);
			this.newHighScore.runAction(scaleTo);
			
			this.shareMenu = HomeLayer.getNewShareButton(
					"New high score in Slime Attack survival mode! " + String.valueOf(SlimeFactory.GameInfo.getCurrentScore()) + " in " + LevelDifficulty.getText(SlimeFactory.GameInfo.getDifficulty()) + " " + Sharer.twitterTag, 
					1.0f, - this.scoreLabel.getContentSize().width / 2f - 25 - HomeLayer.shareSize / 2f, 0);
			this.addChild(this.shareMenu);
		} else {
			this.newHighScore.setString(" ");
		}
		
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
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseSurvivalDifficultyLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
