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
	private List<SurvivalItemLayer> items;
	private SurvivalItemLayer easyItem;
	private SurvivalItemLayer normalItem;
	private SurvivalItemLayer hardItem;
	private SurvivalItemLayer extremItem;	
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new ChooseSurvivalDifficultyLayer());
		}
		
		return scene;
	}
	
	protected ChooseSurvivalDifficultyLayer() {
		this.items = new ArrayList<SurvivalItemLayer>();
		HomeLayer.addBkgChangeDiff(this);				
		this.addChild(HomeLayer.getBackButton(this, "goBack"), Level.zTop);
		this.title = SlimeFactory.getLabel("Survival");
		this.title.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, CCDirector.sharedDirector().winSize().getHeight() - PauseLayer.PaddingY - SlimeFactory.FntSize / 2f);
		this.addChild(this.title, Level.zTop);
	}
	
	private SurvivalItemLayer addSurvivalItem(String bkgBase, int diff, float x, float y) {
		float colorHeight = MenuSprite.Height * PauseLayer.Scale + PauseLayer.PaddingY;
		CCColorLayer colorLayer = CCColorLayer.node(SlimeFactory.getColorLight(200), CCDirector.sharedDirector().winSize().width, colorHeight);
		colorLayer.setPosition(0, CCDirector.sharedDirector().winSize().height - colorHeight);
		this.addChild(colorLayer, Level.zFront);
		
		float referenceHeight = CCDirector.sharedDirector().winSize().height - colorHeight;
		SurvivalItemLayer item = new SurvivalItemLayer(bkgBase, diff, referenceHeight);
		item.setPosition(x, 0);		
		
		this.items.add(item);
		this.addChild(item);
		return item;
	}
	
	@Override
	public void onEnter() {	
		float width = CCDirector.sharedDirector().winSize().width;
		this.easyItem = this.addSurvivalItem("btn-easy", LevelDifficulty.Easy, 0, 0);
		this.normalItem = this.addSurvivalItem("btn-normal", LevelDifficulty.Normal, width / 4, 0);
		this.hardItem = this.addSurvivalItem("btn-hard", LevelDifficulty.Hard, width / 2, 0);
		this.extremItem = this.addSurvivalItem("btn-extreme", LevelDifficulty.Extrem, width * 3 / 4, 0);
		
		SlimeFactory.ContextActivity.showAndNextAd();
		float delayTime = 0f;
		int i = 0;
		for(SurvivalItemLayer item : this.items) {
			boolean bottom = (i % 2 == 1); 
			if (bottom) {
				SlimeFactory.moveToZeroYFromBottom(delayTime, item);
			} else {			
				SlimeFactory.moveToZeroY(delayTime, item);
			}
			
			i++;
			delayTime += 0.3f;
		}
		
		super.onEnter();
	}
	
	@Override
	public void onExit() {
		this.removeChild(this.easyItem, true);
		this.removeChild(this.normalItem, true);
		this.removeChild(this.hardItem, true);
		this.removeChild(this.extremItem, true);
		super.onExit();
	}

	public void goBack(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
