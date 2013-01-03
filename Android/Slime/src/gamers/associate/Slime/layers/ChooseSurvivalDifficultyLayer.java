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

import org.cocos2d.layers.CCColorLayer;
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
	private CCLabel title;
	
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
		this.title = SlimeFactory.getLabel("Survival");
		this.title.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, CCDirector.sharedDirector().winSize().getHeight() - PauseLayer.PaddingY - SlimeFactory.FntSize / 2f);
		this.addChild(this.title, Level.zTop);

		float width = CCDirector.sharedDirector().winSize().width;
		this.addSurvivalItem("btn-easy", LevelDifficulty.Easy, 0, 0, 0, false);
		this.addSurvivalItem("btn-normal", LevelDifficulty.Normal, width / 4, 0, 0.3f, true);
		this.addSurvivalItem("btn-hard", LevelDifficulty.Hard, width / 2, 0, 0.6f, false);
		this.addSurvivalItem("btn-extreme", LevelDifficulty.Extrem, width * 3 / 4, 0, 0.9f, true);
	}
	
	private void addSurvivalItem(String bkgBase, int diff, float x, float y, float delayTime, boolean bottom) {
		float colorHeight = MenuSprite.Height * PauseLayer.Scale + PauseLayer.PaddingY;
		CCColorLayer colorLayer = CCColorLayer.node(SlimeFactory.getColorLight(200), CCDirector.sharedDirector().winSize().width, colorHeight);
		colorLayer.setPosition(0, CCDirector.sharedDirector().winSize().height - colorHeight);
		this.addChild(colorLayer, Level.zFront);
		
		float referenceHeight = CCDirector.sharedDirector().winSize().height - colorHeight;
		SurvivalItemLayer item = new SurvivalItemLayer(bkgBase, diff, referenceHeight);
		item.setPosition(x, 0);
		if (bottom) {
			SlimeFactory.moveToZeroYFromBottom(delayTime, item);
		} else {			
			SlimeFactory.moveToZeroY(delayTime, item);
		}
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
