package gamers.associate.Slime.layers;

import android.annotation.SuppressLint;
import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelBuilderGenerator;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.Vibe;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.items.custom.Star;
import gamers.associate.Slime.levels.GamePlay;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

@SuppressLint("DefaultLocale") 
public class ChooseSurvivalDifficultyLayer extends CCLayer {
	private static CCScene scene;	
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new ChooseSurvivalDifficultyLayer());
		}
		
		return scene;
	}
	
	public ChooseSurvivalDifficultyLayer() {		
		HomeLayer.addBkgChangeDiff(this);				
		this.addChild(HomeLayer.getBackButton(this, "goBack"), Level.zTop);
		float width = CCDirector.sharedDirector().winSize().width;
		this.addSurvivalItem("btn-easy", LevelDifficulty.Easy, 0, 0);
		this.addSurvivalItem("btn-normal", LevelDifficulty.Normal, width / 4, 0);
		this.addSurvivalItem("btn-hard", LevelDifficulty.Hard, width / 2, 0);
		this.addSurvivalItem("btn-extreme", LevelDifficulty.Extrem, width * 3 / 4, 0);
	}
	
	private void addSurvivalItem(String bkgBase, int diff, float x, float y) {
		float colorHeight = MenuSprite.Height * PauseLayer.Scale + PauseLayer.PaddingY;
		float referenceHeight = CCDirector.sharedDirector().winSize().height - colorHeight;
		SurvivalItemLayer item = new SurvivalItemLayer(bkgBase, diff, referenceHeight);
		item.setPosition(x, y);
		this.addChild(item);
	}
	
	@Override
	public void onEnter() {						
		SlimeFactory.ContextActivity.showAndNextAd();
		super.onEnter();
	}
	
	public void goBack(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
