package gamers.associate.Slime.layers;

import android.annotation.SuppressLint;
import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelBuilderGenerator;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.Vibe;
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
	private static final float menuPadding = 320f;
	private static final float iconSizeReference = 87f;
	private static float padding = 150f;
	private static float iconSize = 70f;
	private static float iconPadding = 50f;
	private static CCScene scene;
	private CCMenuItemLabel easyMenuLabel;
	CCMenuItemLabel normalMenuLabel;
	CCMenuItemLabel hardMenuLabel;
	CCMenuItemLabel extremMenuLabel;
	private CCMenu menu;
	CGPoint tmp;
	CGPoint tmp2;
	List<CCNode> toDestroy;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new ChooseSurvivalDifficultyLayer());
		}
		
		return scene;
	}
	
	public ChooseSurvivalDifficultyLayer() {
		tmp = CGPoint.zero();
		tmp2 = CGPoint.zero();
		this.toDestroy = new ArrayList<CCNode>();
		
		HomeLayer.addBkgChangeDiff(this);
		
		CCMenuItemLabel title = CCMenuItemLabel.item(this.createLabel("Survival".toUpperCase()), this, ""); // generates a warning in log due to ""
		title.setAnchorPoint(0, 0.5f);
		this.easyMenuLabel = this.createMenuLabel(LevelDifficulty.Easy, "selectEasy");
		this.normalMenuLabel = this.createMenuLabel(LevelDifficulty.Normal, "selectNormal");
		this.hardMenuLabel = this.createMenuLabel(LevelDifficulty.Hard, "selectHard");
		this.extremMenuLabel = this.createMenuLabel(LevelDifficulty.Extrem, "selectExtrem");
		
		this.addChild(HomeLayer.getBackButton(this, "goBack"));
		
		this.menu = CCMenu.menu(this.easyMenuLabel, this.normalMenuLabel, this.hardMenuLabel, this.extremMenuLabel);
		//this.menu = CCMenu.menu(this.easyMenuLabel, this.normalMenuLabel);
		//this.menu.alignItemsVertically(padding);
		this.menu.alignItemsHorizontally(padding);
		this.menu.setPosition(CGPoint.make(
				(CCDirector.sharedDirector().winSize().getWidth() / 4) + menuPadding ,
				(CCDirector.sharedDirector().winSize().getHeight() / 10 )*7
				));
		this.addChild(this.menu);				
	}
	
	@Override
	public void onEnter() {		
		this.enableMenu(this.easyMenuLabel, LevelDifficulty.Easy);
		this.enableMenu(this.normalMenuLabel, LevelDifficulty.Normal);
		this.enableMenu(this.hardMenuLabel, LevelDifficulty.Hard);
		this.enableMenu(this.extremMenuLabel, LevelDifficulty.Extrem);
		
		SlimeFactory.ContextActivity.showAndNextAd();
		super.onEnter();
	}
	
	@Override
	public void onExit() {
		SlimeFactory.ContextActivity.hideAd();
		for(CCNode node : this.toDestroy) {
			this.removeChild(node, true);
		}
		
		this.toDestroy.clear();
		super.onExit();
	}

	private void enableMenu(CCMenuItemLabel label, int diffRef) {
		int diff = SlimeFactory.GameInfo.getMaxLevelDifficulty();
		boolean isEnable = diffRef <= diff;
		label.setIsEnabled(isEnable);		
		if (isEnable) {															
			label.setColor(ccColor3B.ccWHITE);
		} else {			
			label.setColor(SlimeFactory.ColorSlimeBorder);
		}
		
		tmp = CGPoint.ccpAdd(this.menu.getPosition(), label.getPosition()); // Use Normal as reference not label
		tmp2 = CGPoint.ccpAdd(this.menu.getPosition(), this.normalMenuLabel.getPosition()); // Use Normal as reference not label
		CCSprite spr = ChooseSurvivalDifficultyLayer.getLevelSprite(diffRef, isEnable);
		spr.setPosition(tmp.x - iconPadding - (iconSize / 2), tmp.y + (iconSize - 60f));
		
		if (isEnable) {
			CCSprite star = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);
			//star.setPosition(tmp2.x + this.normalMenuLabel.getContentSize().width + padding + (Star.Default_Width / 2), tmp.y);
			star.setPosition(tmp.x - iconPadding - (iconSize / 2), tmp.y - iconSize); // Use Normal x as reference not label
			int score = SlimeFactory.GameInfo.getScore(diffRef); 
			CCLabel scoreLabel = CCLabel.makeLabel(String.valueOf(score).toUpperCase(), "fonts/Slime.ttf", 60.0f);
			scoreLabel.setPosition(tmp.x, tmp.y - iconSize); // Use Normal x as reference not label
			scoreLabel.setAnchorPoint(0, 0.5f);
			CCLabel levelLabel = CCLabel.makeLabel("10 level in a row!", "fonts/Slime.ttf", 30.0f);
			levelLabel.setPosition(tmp.x - iconPadding - (iconSize / 2), tmp.y - scoreLabel.getContentSize().height - iconSize); // Use Normal x as reference not label
			levelLabel.setAnchorPoint(0, 0.5f);			
			this.addChild(star);
			this.addChild(scoreLabel);
			this.addChild(levelLabel);
			
			this.toDestroy.add(star);
			this.toDestroy.add(scoreLabel);
			this.toDestroy.add(levelLabel);
		}
		
		this.addChild(spr);
		this.toDestroy.add(spr);
	}
	
	public static CCSprite getLevelSprite(int diff, boolean isEnable) {
		String suf = "";
		if (!isEnable) {
			suf = "-grey";
		}
		String diffTxt = LevelDifficulty.getText(diff).toLowerCase();
		CCSprite spr = CCSprite.sprite("mode-" + diffTxt + suf + "-01.png");
		spr.setScale(iconSize / iconSizeReference);
		
		return spr;
	}

	private CCMenuItemLabel createMenuLabel(int diff, String selector) {
		CCMenuItemLabel label = CCMenuItemLabel.item(this.createLabel(LevelDifficulty.getText(diff)), this, selector);
		label.setAnchorPoint(0, 0.5f);
		
		return label;
	}
	
	private CCLabel createLabel(String text) {
		CCLabel label = CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", 40.0f);
		// label.setColor(ccColor3B.ccc3(32,181,79));
		label.setColor(ccColor3B.ccWHITE);
		return label;
	}
	
	public void selectEasy(Object sender) {
		this.selectLevel(LevelDifficulty.Easy);
	}
	
	public void selectNormal(Object sender) {
		this.selectLevel(LevelDifficulty.Normal);
	}
	
	public void selectHard(Object sender) {
		this.selectLevel(LevelDifficulty.Hard);
	}
	
	public void selectExtrem(Object sender) {
		this.selectLevel(LevelDifficulty.Extrem);
	}
	
	public void goBack(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	private void selectLevel(int diff) {
		SlimeFactory.GameInfo.resetDifficulty(diff);
//		SlimeFactory.LevelBuilder.resetAll();
		Level level = Level.get(LevelBuilderGenerator.defaultId, true, GamePlay.Survival);
		
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, level.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
		
		Vibe.vibrate();
		Sounds.playEffect(R.raw.menuselect, true);
		Sounds.stopMusic();

	}
}
