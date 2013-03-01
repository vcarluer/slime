package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.LevelDifficulty;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.custom.MenuSprite;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale") 
public class ChooseSurvivalDifficultyLayer extends CCLayer implements IBackableLayer {
	private static CCScene scene;
	private CCLabel title;
	private List<SurvivalItemLayer> items;	
	private float colorHeight;
	
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
		
		this.colorHeight = MenuSprite.Height * PauseLayer.Scale + PauseLayer.PaddingY;
		CCColorLayer colorLayer = CCColorLayer.node(SlimeFactory.getColorLight(200), CCDirector.sharedDirector().winSize().width, colorHeight);
		colorLayer.setPosition(0, CCDirector.sharedDirector().winSize().height - colorHeight);
		this.addChild(colorLayer, Level.zFront);
		
		this.title = SlimeFactory.getLabel("Survival");
		this.title.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, CCDirector.sharedDirector().winSize().getHeight() - PauseLayer.PaddingY - SlimeFactory.FntSize / 2f);
		this.addChild(this.title, Level.zTop);
		
		float width = CCDirector.sharedDirector().winSize().width;
		this.addSurvivalItem("btn-easy", LevelDifficulty.Easy, 0, 0);
		this.addSurvivalItem("btn-normal", LevelDifficulty.Normal, width / 4, 0);
		this.addSurvivalItem("btn-hard", LevelDifficulty.Hard, width / 2, 0);
		this.addSurvivalItem("btn-extreme", LevelDifficulty.Extrem, width * 3 / 4, 0);
		
		CCMenu muteMenu = HomeLayer.getMuteButton(this, "toggleMute");
		this.addChild(muteMenu, Level.zTop);
	}
	
	public void toggleMute(Object sender) {
		SlimeFactory.toggleMute();
	}
	
	private SurvivalItemLayer addSurvivalItem(String bkgBase, int diff, float x, float y) {
		float referenceHeight = CCDirector.sharedDirector().winSize().height - this.colorHeight;
		SurvivalItemLayer item = new SurvivalItemLayer(bkgBase, diff, referenceHeight);
		item.setPosition(x, 0);		
		
		this.items.add(item);
		this.addChild(item);
		return item;
	}
	
	@Override
	public void onEnter() {					
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
		
		SlimeFactory.playMenuMusic();
		
		super.onEnter();
	}
	
	@Override
	public void onExit() {
		super.onExit();
	}

	public void goBack(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}

	@Override
	public void goBack() {
		this.goBack(this);
	}
}
