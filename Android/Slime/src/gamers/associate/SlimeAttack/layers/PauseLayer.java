package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.custom.MenuSprite;
import gamers.associate.SlimeAttack.levels.GamePlay;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.menus.CCMenuItemToggle;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

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
	private CCMenuItemSprite restartMenu;
	private CCMenuItemSprite homeMenu;
	private CCMenuItemSprite resumeMenu;
	private CCMenuItemToggle muteMenu;	
	private CCBitmapFontAtlas title;
	private String currentTitle;
	private CGPoint tmp = CGPoint.zero();
	
	public PauseLayer() {
//		CCMenuItem label = CCMenuItemLabel.item(getMenuLabel("Pause"), this, "");		
//		
//		this.scoreMaxLabel = CCMenuItemLabel.item(getMenuLabel(scoreTxt), null, "");
		
		CCSprite resumeSpriteN = CCSprite.sprite("control-play.png", true);
		CCSprite resumeSpriteS = CCSprite.sprite("control-play.png", true);
		this.resumeMenu = CCMenuItemSprite.item(resumeSpriteN, resumeSpriteS, this, "goResume");
		this.setMenuPos(this.resumeMenu, 1);
		
		CCSprite restartSpriteN = CCSprite.sprite("control-restart.png", true);
		CCSprite restartSpriteS = CCSprite.sprite("control-restart.png", true);
		restartMenu = CCMenuItemSprite.item(restartSpriteN, restartSpriteS, this, "goRestart");		
		this.setMenuPos(restartMenu, 2);
		
		CCSprite homeSpriteN = CCSprite.sprite("control-home.png", true);
		CCSprite homeSpriteS = CCSprite.sprite("control-home.png", true);
		homeMenu = CCMenuItemSprite.item(homeSpriteN, homeSpriteS, this, "goHome");		
		this.setMenuPos(homeMenu, 3);
		
		CCSprite muteOnSpriteN = CCSprite.sprite("control-sound-on.png");
		CCSprite muteOnSpriteS = CCSprite.sprite("control-sound-on.png");
		CCMenuItemSprite muteOn = CCMenuItemSprite.item(muteOnSpriteN, muteOnSpriteS);
		CCSprite muteOffSpriteN = CCSprite.sprite("control-sound-off.png");
		CCSprite muteOffSpriteS = CCSprite.sprite("control-sound-off.png");
		CCMenuItemSprite muteOff = CCMenuItemSprite.item(muteOffSpriteN, muteOffSpriteS);
		
		muteMenu = HomeLayer.getMuteItemButton(this, "toggleMute");		
		this.setMenuPosRight(muteMenu, 1);
				
		this.menu = CCMenu.menu(resumeMenu, restartMenu, homeMenu, muteMenu);
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
		
		this.title = getMenuLabel(" ", 45f, SlimeFactory.ColorSlimeBorder);
		this.title.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() / 2));
		this.addChild(this.title);	
	}
	
	private static CCBitmapFontAtlas getMenuLabel(String text, float size, ccColor3B color) {
		CCBitmapFontAtlas label =  CCBitmapFontAtlas.bitmapFontAtlas(text, "SlimeFont.fnt");
		label.setScale(size / SlimeFactory.FntSize);
		label.setColor(color);
		return label;
	}
	
	public void fadeTitle() {
		CCFadeOut fade = CCFadeOut.action(1f);
		this.title.runAction(fade);
		this.currentTitle = null;
	}
	
	public void gameBegin() {
		this.title.stopAllActions();
		this.title.setVisible(false);
		this.currentTitle = null;
	}
	
	@Override
	public void onExit() {
		this.currentTitle = null;
		super.onExit();
	}

	public void setTitle(String titleText) {
		if (titleText == null) {
			this.currentTitle = null;
		} else {
			if (this.currentTitle == null) {
				if (Level.currentLevel.getLevelDefinition().getGamePlay() == GamePlay.TimeAttack) {
					this.currentTitle = String.valueOf(Level.currentLevel.getLevelDefinition().getNumber()) + ". " + titleText;
				} else {
					this.currentTitle = String.valueOf(SlimeFactory.GameInfo.getLevelNum()) + ". " + titleText;
				}
				
				this.title.stopAllActions();
				this.title.setVisible(true);
				this.title.setString(this.currentTitle.toUpperCase());
				// double padding
				float dPadding = 250f;
				float scaleRatio = CCDirector.sharedDirector().winSize().width / (this.title.getContentSize().width + dPadding);
				this.title.setScale(scaleRatio);
				this.title.setPosition(
						CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
						CCDirector.sharedDirector().winSize().getHeight() / 2));
				this.title.setOpacity(255);
				float moveDistance =  CCDirector.sharedDirector().winSize().getWidth() / 2 - (this.title.getContentSize().width * scaleRatio) / 2f;
				float time = 3f;
				CCDelayTime delay = CCDelayTime.action(time);
				CCCallFunc call = CCCallFunc.action(this, "fadeTitle");
				CCSequence seq = CCSequence.actions(delay, call);
				this.title.runAction(seq);
				tmp.set(-moveDistance, 0);
				CCMoveBy move = CCMoveBy.action(time, tmp);
				this.title.runAction(move);
			}
		}
	}
	
	private void setMenuPos(CCMenuItem menuItem, int count) {
		menuItem.setScale(Scale);
		
		float left = - CCDirector.sharedDirector().winSize().getWidth() / 2 + ((MenuSprite.Width * Scale) + PaddingX) / 2 + (MenuSprite.Width * Scale + PaddingX) * (count - 1);
		float top = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * Scale) + PaddingY) / 2;
		menuItem.setPosition(CGPoint.make(left, top));
	}
	
	private void setMenuPosRight(CCMenuItem menuItem, int count) {
		menuItem.setScale(Scale);
		
		float left = CCDirector.sharedDirector().winSize().getWidth() / 2 - ((MenuSprite.Width * Scale) + PaddingX) / 2 - (MenuSprite.Width * Scale + PaddingX) * (count - 1);
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
		Level.currentLevel.getCameraManager().cancelActions();
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
			CCTransitionScene transition = CCFadeTransition.transition(0.5f, ChooseSurvivalDifficultyLayer.getScene());
			CCDirector.sharedDirector().replaceScene(transition);
		} else {
//			Level.currentLevel.goHome();
			CCTransitionScene transition = CCFadeTransition.transition(0.5f, StoryWorldLayer.getScene(SlimeFactory.PackageManager.getCurrentPackage().getOrder()));
			CCDirector.sharedDirector().replaceScene(transition);
		}
	}
	
	public void toggleMute(Object sender) {
		SlimeFactory.toggleMute();
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
	
	@Override
	public void onEnter() {		
		super.onEnter();
		if (Level.currentLevel.getLevelDefinition().getGamePlay() == GamePlay.Survival) {
			this.restartMenu.setIsEnabled(false);
			this.restartMenu.setVisible(false);
			this.homeMenu.setIsEnabled(true);
			this.homeMenu.setVisible(true);
		} else {
			this.restartMenu.setIsEnabled(true);
			this.restartMenu.setVisible(true);
			this.homeMenu.setIsEnabled(true);
			this.homeMenu.setVisible(true);
		}
		
		if (Sounds.isMute()) {
			this.muteMenu.setSelectedIndex(1);
		}
	}
	
	public void gameStarted(boolean started) {
		if (Level.currentLevel.getLevelDefinition().getGamePlay() == GamePlay.Survival) {			
			this.homeMenu.setIsEnabled(!started);
			this.homeMenu.setVisible(!started);			
		} else {			
			this.homeMenu.setIsEnabled(true);
			this.homeMenu.setVisible(true);
		}
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

	public void animatePlay(boolean isAnimated) {
		this.resumeMenu.stopAllActions();
		float baseScale = Scale;
		this.resumeMenu.setScale(baseScale);
		if (isAnimated) {						
			
			CCScaleBy scaleBy = CCScaleBy.action(0.3f, 0.3f);
			CCScaleTo scaleTo = CCScaleTo.action(0.3f, baseScale);
			CCDelayTime delay = CCDelayTime.action(2.0f);
			CCSequence seq = CCSequence.actions(scaleBy, scaleTo, delay);
			CCRepeatForever rep = CCRepeatForever.action(seq);
			this.resumeMenu.runAction(rep);
		}
	}
}
