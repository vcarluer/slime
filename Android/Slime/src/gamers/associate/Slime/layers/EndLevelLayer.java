package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.custom.RankFactory;
import gamers.associate.Slime.items.custom.Slimy;
import gamers.associate.Slime.items.custom.SlimySuccess;
import gamers.associate.Slime.items.custom.Star;
import gamers.associate.Slime.levels.GamePlay;

import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCTintTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.annotation.SuppressLint;
import android.view.MotionEvent;

@SuppressLint("DefaultLocale") public class EndLevelLayer extends CCLayer {		
	private static final int MARGIN_RANK = 11;
	private static final float SLIMY_SCALE = 2f;
	private static final int scorePosition = 0;	
	private static final int totalScorePosition = - 75;
	private static final int MenuPosition = scorePosition - 150;
	private static final int StarCountPosition = scorePosition + 60;
	private static final int SlimyPosition = StarCountPosition + 100;
	private static final int labelSize = 60;	
	private static final int stepScore = 10000;
	private static final float STAR_SCALE = 1.5f;	
	private CCSprite slime;
	private CCSprite star;
	private CCBitmapFontAtlas starCountLabel;
	private CCBitmapFontAtlas scoreLabel;
	private CCBitmapFontAtlas totalScoreLabel;
	private CCMenu menu;	
	private CCMenuItemSprite nextMenu;
	private CCMenuItemSprite restartMenu;
	private CCMenuItemSprite homeMenu;
	private int previousTarget;
	private CCSprite rankStar;
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if (!scoreCountEnd) {
			this.setScore(this.lastScore);
			this.setStars(this.targetStars);
			this.setTotalScore(true);
			this.scoreCountEnd = true;
		}
		
		return true;
	}

	public EndLevelLayer() {								
		
		this.starCountLabel = getMenuLabel("0 / 0");
		this.starCountLabel.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2 + StarCountPosition
				));
		
		this.scoreLabel = getMenuLabel("0");
		this.scoreLabel.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2 + scorePosition
				));
		
		this.totalScoreLabel = getMenuLabel("0");
		this.totalScoreLabel.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2, 
				CCDirector.sharedDirector().winSize().height / 2 + totalScorePosition
				));
		
		CCSprite nextSpriteN = CCSprite.sprite("control-fastforward.png", true);
		CCSprite nextSpriteS = CCSprite.sprite("control-fastforward.png", true);
		this.nextMenu = CCMenuItemSprite.item(nextSpriteN, nextSpriteS, this, "goNext");
		
		CCSprite restartSpriteN = CCSprite.sprite("control-restart.png", true);
		CCSprite restartSpriteS = CCSprite.sprite("control-restart.png", true);
		this.restartMenu = CCMenuItemSprite.item(restartSpriteN, restartSpriteS, this, "goRestart");
		this.restartMenu.setScale(0.5f);
		
		CCSprite homeSpriteN = CCSprite.sprite("control-home.png", true);
		CCSprite homeSpriteS = CCSprite.sprite("control-home.png", true);
		this.homeMenu = CCMenuItemSprite.item(homeSpriteN, homeSpriteS, this, "goHome");
		this.homeMenu.setScale(0.5f);
		
		this.menu = CCMenu.menu(this.homeMenu, this.nextMenu, this.restartMenu);
		this.menu.alignItemsHorizontally(50);
		this.menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2 + MenuPosition
				));				

		this.addChild(this.starCountLabel);
		this.addChild(this.scoreLabel);
		this.addChild(this.totalScoreLabel);
		this.addChild(this.menu);		
	}
	
	public void goNext(Object sender) {
		Sounds.playEffect(R.raw.menuselect);
		
		if (Level.currentLevel.getGamePlay().getType() == GamePlay.Survival && SlimeFactory.LevelBuilder.isBoss()) {
			CCTransitionScene transition = CCFadeTransition.transition(0.5f, SurvivalGameOverLayer.getScene());
			CCDirector.sharedDirector().replaceScene(transition);
		} else {
			Level.currentLevel.goNext();
		}
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
			CCTransitionScene transition = CCFadeTransition.transition(0.5f, StoryWorldLayer.getScene(SlimeFactory.PackageManager.getCurrentPackage().getOrder()));
			CCDirector.sharedDirector().replaceScene(transition);
		}
	}
	
	public void setScore(String text) {
		this.scoreLabel.setString(text.toUpperCase());		
	}
	
	public void setScore(int score) {
		String text =String.valueOf(score);
		this.setScore(text);
	}
	
	private void setStars(int currentStars) {
		String text =String.valueOf(currentStars) + " / " + String.valueOf(this.totalStars);		
		this.starCountLabel.setString(text.toUpperCase());
		
		float starPadding = -10f;
		float starX = this.starCountLabel.getPosition().x - this.starCountLabel.getContentSize().width / 2 - SlimeFactory.Star.getStarReferenceWidth() / 2 + starPadding;
		this.star.setPosition(CGPoint.make(
				starX,
				this.star.getPosition().y
				));
	}
		
	public void enable() {
		CCMenu currentMenu = null;		
		currentMenu = this.menu;
		
		this.setIsTouchEnabled(true);
		currentMenu.setIsTouchEnabled(true);
		currentMenu.setVisible(true);
		
		this.scoreLabel.setVisible(true);
		this.setVisible(true);
	}
	
	public void disable() {
		this.setIsTouchEnabled(false);
		if (this.slime != null) {
			this.removeChild(this.slime, true);
			this.slime = null;
		}
		
		if (this.star != null) {
			this.removeChild(this.star, true);
			this.star = null;
		}
		
		this.scoreLabel.setVisible(false);
		this.menu.setIsTouchEnabled(false);
		this.menu.setVisible(false);
		this.setVisible(false);
	}
	
	private static CCBitmapFontAtlas getMenuLabel(String text) {
		return getMenuLabel(text, SlimeFactory.FntSize);
	}
	
	private static CCBitmapFontAtlas getMenuLabel(String text, float scale) {
		CCBitmapFontAtlas label = CCBitmapFontAtlas.bitmapFontAtlas(text.toUpperCase(), "SlimeFont.fnt");
		label.setScale(labelSize / scale);
		label.setColor(SlimeFactory.ColorSlime);
		return label;
	}
	
	public void setNextEnabled(boolean value) {
		this.nextMenu.setIsEnabled(value);
		this.nextMenu.setVisible(value);
	}	
	
	public void setHomeEnabled(boolean value) {
		this.homeMenu.setIsEnabled(value);
		this.homeMenu.setVisible(value);
	}
	
	private int lastScore;
	private int targetScore;
	private int currentScore;
	private int totalStars;
	private int currentStars;
	private int targetStars;
	
	public void setVictory(int score) {
		this.scoreCountEnd = false;
		this.lastScore = 0;
		this.targetScore = 0;
		this.currentScore = 0;

		this.initStar();
		// this.setScore(score);
		this.lastScore = score;		
		this.targetScore = Level.currentLevel.getGamePlay().getBaseScore();
		this.totalStars = SlimeFactory.LevelBuilder.getTotalStar();
		this.targetStars = Level.currentLevel.getGamePlay().bonusCount();		
		this.currentStars = 0;
		this.previousTarget = 0;
		this.setScore(0);
		this.setStars(0);
		this.setPreviousTotalScore(false);
		
		// Slimy.Anim_Success not used anymore
		this.initSlime(Slimy.Anim_Success, 0f, true);
		
		if (this.rankStar != null) {
			this.rankStar.stopAllActions();
			this.removeChild(this.rankStar, true);				
		}
		
		Sounds.playEffect(R.raw.star);
	}
	
	public void setLose() {
		this.initStar();
		this.setScore(0);
		this.setTotalScore(true);
		this.initSlime(Slimy.Anim_LastDeath, 2f, false);
	}
	
	private void initStar() {
		if (this.star == null) {
			this.star = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);
			this.addChild(this.star);
		}
				
		this.star.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2 + StarCountPosition
				));					
	}
	
	private void initSlime(String animation, float waitTime, boolean win) {
		if (win) {
			this.slime = SlimeFactory.SlimySuccess.getAnimatedSprite(SlimySuccess.getAnimationName(SlimeFactory.GameInfo.getDifficulty()));			
		} else {
			this.slime = SlimeFactory.Slimy.getAnimatedSprite(animation, waitTime);
			this.slime.setScale(SLIMY_SCALE);
		}				
		
		this.addChild(this.slime);
		this.slimyPos = CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2 + SlimyPosition
				);
		this.slime.setPosition(this.slimyPos);
	}
	
	private CGPoint slimyPos;
	
	private boolean scoreCountEnd;
	
	public void tick(float deltaBase) {
		if (!scoreCountEnd) {
			if (this.currentScore < this.targetScore) {
				this.currentScore += (stepScore * deltaBase);
				if (this.currentScore > this.targetScore) this.currentScore = this.targetScore;
			}
			
			if (this.currentScore == this.targetScore) {
				if (this.currentStars < this.targetStars) {
					this.currentStars++;
					this.targetScore += Level.currentLevel.getGamePlay().getBonusScore(this.currentStars);					
					Sounds.playEffect(R.raw.star);
					CCTintTo tint = CCTintTo.action(0.2f, ccColor3B.ccc3(243, 225, 102));
					CCTintTo tintBack = CCTintTo.action(0.1f, SlimeFactory.ColorSlime);
					CCSequence seq = CCSequence.actions(tint, tintBack);
					this.scoreLabel.runAction(seq);
				} else {
					this.currentScore = this.lastScore;
					this.scoreCountEnd = true;
					this.setTotalScore(true);
				}
			}
			
			this.setScore(this.currentScore);
			this.setStars(this.currentStars);
		}		
	}

	private void setTotalScore(boolean animate) {
		if (Level.currentLevel.getGamePlay().getType() == GamePlay.Survival) {
			this.setTotalScore(SlimeFactory.GameInfo.getCurrentScore(), animate);
		}
		
		if (Level.currentLevel.getGamePlay().getType() == GamePlay.TimeAttack) {
			this.setTotalScore(Level.currentLevel.getLevelDefinition().getMaxScore(), animate);
		}
	}
	
	private void setPreviousTotalScore(boolean animate) {
		int score = 0;
		if (Level.currentLevel.getGamePlay().getType() == GamePlay.Survival) {
			score = SlimeFactory.GameInfo.getPreviousTotalCurrent();
		}
		
		if (Level.currentLevel.getGamePlay().getType() == GamePlay.TimeAttack) {
			score = Level.currentLevel.getLevelDefinition().getPreviousMaxScore();
		}
		
		if (score > 0) {
			this.setTotalScore(score, animate);
		} else {
			this.totalScoreLabel.setVisible(false);
		}
		
		this.previousTarget = score;
	}
	
	private void setTotalScore(int score, boolean animate) {
		String prefix = "";
		if (Level.currentLevel.getGamePlay().getType() == GamePlay.Survival) {
			prefix = "Total: ";
		}
		
		if (Level.currentLevel.getGamePlay().getType() == GamePlay.TimeAttack) {
			prefix = "Highest: ";
		}
		
		String text = prefix + String.valueOf(score);
		if (animate) {
			this.totalScoreLabel.stopAllActions();
			this.totalScoreLabel.setScale(1);			
			
			this.rankStar = RankFactory.getSprite(Level.currentLevel.getLevelDefinition().getRank());
			this.rankStar.setScale(10);
			CCScaleTo scaleTo = CCScaleTo.action(0.3f, STAR_SCALE);
			this.rankStar.runAction(scaleTo);
			
			CGPoint pos = CGPoint.make(this.slimyPos.x + (Slimy.AnimSuccess_Width * SLIMY_SCALE) / 2 + MARGIN_RANK + (Star.Default_Width * STAR_SCALE) / 2, this.slimyPos.y);
			this.rankStar.setPosition(pos);
			this.addChild(this.rankStar);
		}

		if (!this.totalScoreLabel.getVisible()) {
			this.totalScoreLabel.setVisible(true);
			if (animate) {
				this.totalScoreLabel.setScale(10);
				CCScaleTo scale = CCScaleTo.action(0.3f, 1.0f);
				this.totalScoreLabel.runAction(scale);
			}
		} else {
			if (score > this.previousTarget && animate) {
				CCScaleBy scaleBy = CCScaleBy.action(0.3f, 1.5f);
				CCSequence seq = CCSequence.actions(scaleBy, scaleBy.reverse());
				this.totalScoreLabel.runAction(seq);
			}
		}
		
		this.totalScoreLabel.setString(text.toUpperCase());
	}	
}