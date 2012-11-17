package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;

public class SurvivalGameOverLayer extends CCLayer {
	private static CCScene scene;
	private CCLabel scoreLabel;
	private CCLabel gameOverLabel;
	private CCMenu backMenu;
	
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
		this.scoreLabel = CCLabel.makeLabel("Score: ", "fonts/Slime.ttf", 42 * SlimeFactory.Density);
		this.gameOverLabel = CCLabel.makeLabel("GameOver!", "fonts/Slime.ttf", 84 * SlimeFactory.Density);
		this.gameOverLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + 100);
		this.scoreLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY());
		
		this.addChild(this.gameOverLabel);
		this.addChild(this.scoreLabel);
		
		this.addChild(this.backMenu);
	}
	@Override
	public void onEnter() {
		this.scoreLabel.setString("Score: " + String.valueOf(Level.currentLevel.getGamePlay().getScore()));
		
		super.onEnter();
	}
	
	public void goBack(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
