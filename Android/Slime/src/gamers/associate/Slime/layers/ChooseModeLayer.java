package gamers.associate.Slime.layers;

import android.annotation.SuppressLint;
import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
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
		
		this.addChild(HomeLayer.getHomeMenuButton(this, "goHome"));
	}
	
	@Override
	public void onEnter() {
		this.story.setPosition(0, CCDirector.sharedDirector().winSize().height);
		this.moveToZeroY(0f, this.story);
		this.survival.setPosition(CCDirector.sharedDirector().winSize().width / 2f, CCDirector.sharedDirector().winSize().height);
		this.moveToZeroY(0.3f, this.survival);
		super.onEnter();
	}

	private void moveToZeroY(float delayTime, CCLayer layer) {
		CCDelayTime delay = CCDelayTime.action(0.4f + delayTime);
		CCMoveTo moveTo = CCMoveTo.action(0.5f, CGPoint.make(layer.getPosition().x, 0));
		CCSequence seq = CCSequence.actions(delay, moveTo);
		layer.runAction(seq);
	}

	public void goHome(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level currentLevel = Level.get(LevelHome.Id, true, GamePlay.None);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
