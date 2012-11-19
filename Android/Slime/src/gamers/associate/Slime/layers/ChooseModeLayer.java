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
		this.storyLabel = CCMenuItemLabel.item(this.createLabel("Story"), this, "chooseStory");
		this.survivalLabel = CCMenuItemLabel.item(this.createLabel("Survival"), this, "chooseSurvival");
		
		this.menu = CCMenu.menu(this.storyLabel, this.survivalLabel);
		this.menu.alignItemsVertically(padding);
		this.menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2
				));
		this.addChild(this.menu);		
		this.addChild(HomeLayer.getHomeMenuButton(this, "goHome"));
	}
	
	private CCLabel createLabel(String text) {
		CCLabel label = CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 90.0f);
		// label.setColor(ccColor3B.ccc3(32,181,79));
		label.setColor(SlimeFactory.ColorSlime);
		return label;
	}
	
	public void chooseStory(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, StoryWorldLayer.getScene(LevelDifficulty.Easy));
		 CCDirector.sharedDirector().replaceScene(transition);
	}
	
	public void chooseSurvival(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, ChooseSurvivalDifficultyLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	public void goHome(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level currentLevel = Level.get(LevelHome.Id, true, GamePlay.None);
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
