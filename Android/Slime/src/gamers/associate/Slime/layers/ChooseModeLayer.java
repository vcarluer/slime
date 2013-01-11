package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale") 
public class ChooseModeLayer extends CCLayer {
	private static CCScene scene;
	private AchievementItemLayer achievementItem;
	
	private StoryModeItemLayer story;
	private SurvivalModeItemLayer survival;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new ChooseModeLayer());
		}
		
		return scene;
	}
	
	public ChooseModeLayer() {
		HomeLayer.addBkgSplash(this);
		this.story = new StoryModeItemLayer();
		this.addChild(this.story);
		this.survival = new SurvivalModeItemLayer();
		this.addChild(this.survival);
		this.achievementItem = new AchievementItemLayer();
		this.addChild(this.achievementItem);
		
		this.addChild(HomeLayer.getHomeMenuButton(this, "goHome"));
	}
	
	@Override
	public void onEnter() {
		this.story.setPosition(0, 0);
		SlimeFactory.moveToZeroY(0f, this.story);
		this.survival.setPosition(CCDirector.sharedDirector().winSize().width / 2f, 0);
		SlimeFactory.moveToZeroY(0.3f, this.survival);
		super.onEnter();
	}	

	public void goHome(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level currentLevel = Level.get(LevelHome.Id, true, GamePlay.None);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
