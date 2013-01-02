package gamers.associate.Slime.layers;

import android.annotation.SuppressLint;
import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

@SuppressLint("DefaultLocale") 
public class ChooseModeLayer extends CCLayer {
	private static CCScene scene;
	private CCMenuItemLabel storyLabel;
	private CCMenuItemLabel survivalLabel;
	private CCMenu menu;
	
	private static float padding = 50f;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new ChooseModeLayer());
		}
		
		return scene;
	}
	
	public ChooseModeLayer() {
		HomeLayer.addBkgSplash(this);
		StoryModeItemLayer story = new StoryModeItemLayer();
		story.setPosition(0, 0);
		this.addChild(story);
		SurvivalModeItemLayer survival = new SurvivalModeItemLayer();
		survival.setPosition(CCDirector.sharedDirector().winSize().width / 2f, 0);
		this.addChild(survival);
		
		this.addChild(HomeLayer.getHomeMenuButton(this, "goHome"));
	}
	
	public void goHome(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level currentLevel = Level.get(LevelHome.Id, true, GamePlay.None);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
