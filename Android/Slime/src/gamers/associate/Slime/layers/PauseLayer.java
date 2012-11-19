package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.levels.GamePlay;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale") 
public class PauseLayer extends CCLayer {
	private static String scoreTxt = "MAX: ";
	private CCMenu menu;
	private CCMenuItemLabel scoreMaxLabel;
	public static float Scale = 0.7f * SlimeFactory.SGSDensity;
	public static float PaddingX = 25f;
	public static float PaddingY = 5f;
	private CCSprite arrowSpriteT;
	private CCSprite arrowSpriteR;
	private CCSprite arrowSpriteB;
	private CCSprite arrowSpriteL;
	public static final float arrowWidth = 75f;
	public static final float arrowHeight = 69f;
	
	public PauseLayer() {
//		CCMenuItem label = CCMenuItemLabel.item(getMenuLabel("Pause"), this, "");		
//		
//		this.scoreMaxLabel = CCMenuItemLabel.item(getMenuLabel(scoreTxt), null, "");
		
		CCSprite resumeSpriteN = CCSprite.sprite("control-play.png", true);
		CCSprite resumeSpriteS = CCSprite.sprite("control-play.png", true);
		CCMenuItemSprite resumeMenu = CCMenuItemSprite.item(resumeSpriteN, resumeSpriteS, this, "goResume");
		this.setMenuPos(resumeMenu, 1);
		
		CCSprite restartSpriteN = CCSprite.sprite("control-restart.png", true);
		CCSprite restartSpriteS = CCSprite.sprite("control-restart.png", true);
		CCMenuItemSprite restartMenu = CCMenuItemSprite.item(restartSpriteN, restartSpriteS, this, "goRestart");		
		this.setMenuPos(restartMenu, 2);
		
		CCSprite homeSpriteN = CCSprite.sprite("control-home.png", true);
		CCSprite homeSpriteS = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite homeMenu = CCMenuItemSprite.item(homeSpriteN, homeSpriteS, this, "goHome");
		this.setMenuPos(homeMenu, 3);
		
		this.menu = CCMenu.menu(resumeMenu, restartMenu, homeMenu);
		// this.menu.alignItemsHorizontally();				
		
		this.addChild(this.menu);
		
		float middleX = CCDirector.sharedDirector().winSize().getWidth() / 2;
		float middleY = CCDirector.sharedDirector().winSize().getHeight() / 2;
		float top = CCDirector.sharedDirector().winSize().getHeight() - arrowHeight / 2;
		float right = CCDirector.sharedDirector().winSize().getWidth() - arrowWidth / 2; 
		float bottom = arrowHeight / 2;
		float left = arrowWidth / 2;;
		this.arrowSpriteT = CCSprite.sprite("arrow.png");
		this.arrowSpriteT.setPosition(middleX, top);
		this.arrowSpriteT.setRotation(-90);
		this.arrowSpriteR = CCSprite.sprite("arrow.png");
		this.arrowSpriteR.setPosition(right, middleY);		
		this.arrowSpriteB = CCSprite.sprite("arrow.png");
		this.arrowSpriteB.setPosition(middleX, bottom);
		this.arrowSpriteB.setRotation(90);
		this.arrowSpriteL = CCSprite.sprite("arrow.png");
		this.arrowSpriteL.setPosition(left, middleY);
		this.arrowSpriteL.setRotation(180);
		
		this.addChild(this.arrowSpriteT);
		this.addChild(this.arrowSpriteR);
		this.addChild(this.arrowSpriteB);
		this.addChild(this.arrowSpriteL);
	}
	
	private void setMenuPos(CCMenuItem menuItem, int count) {
		menuItem.setScale(Scale);
		
		float left = - CCDirector.sharedDirector().winSize().getWidth() / 2 + ((MenuSprite.Width * Scale) + PaddingX) / 2 + (MenuSprite.Width * Scale + PaddingX) * (count - 1);
		float top = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * Scale) + PaddingY) / 2;
		menuItem.setPosition(CGPoint.make(left, top));
	}
	
	public void setMaxScore(String text) {
		if (this.scoreMaxLabel != null) {
			this.scoreMaxLabel.setString(text.toUpperCase());
		}
	}
	
	public void setMaxScore(int scoreMax) {
		if (this.scoreMaxLabel != null) {
			String text = scoreTxt + String.valueOf(scoreMax);
			this.setMaxScore(text);
		}
	}
	
	public void goResume(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.resume();
		SlimeFactory.ContextActivity.hideAd();
	}
	
	public void goRestart(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		Level.currentLevel.reload();
	}

	public void goHome(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		if (Level.currentLevel.getGamePlay().getType() == GamePlay.Survival) {
			CCTransitionScene transition = CCFadeTransition.transition(0.5f, SurvivalGameOverLayer.getScene());
			CCDirector.sharedDirector().replaceScene(transition);
		} else {
//			Level.currentLevel.goHome();
			CCTransitionScene transition = CCFadeTransition.transition(0.5f, StoryWorldLayer.getScene(SlimeFactory.GameInfo.getDifficulty()));
			CCDirector.sharedDirector().replaceScene(transition);
		}		
	}
	
	public void enable() {
		this.setIsTouchEnabled(true);
		this.setVisible(true);
		this.menu.setIsTouchEnabled(true);
		
		this.setArrowAction(this.arrowSpriteT);
		this.setArrowAction(this.arrowSpriteR);
		this.setArrowAction(this.arrowSpriteB);
		this.setArrowAction(this.arrowSpriteL);
	}
	
	private void setArrowAction(CCSprite arrow) {
		CCFadeOut fo = CCFadeOut.action(1f);
		CCFadeIn fi = CCFadeIn.action(1f);
		CCSequence sequence = CCSequence.actions(fo, fi);
		CCRepeatForever repeat = CCRepeatForever.action(sequence);
		arrow.runAction(repeat);
	}
	
	public void disable() {		
		this.arrowSpriteT.stopAllActions();
		this.arrowSpriteR.stopAllActions();
		this.arrowSpriteB.stopAllActions();
		this.arrowSpriteL.stopAllActions();
		
		this.setIsTouchEnabled(false);
		this.setVisible(false);
		this.menu.setIsTouchEnabled(false);
	}
}
