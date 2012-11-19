package gamers.associate.Slime.layers;

import android.annotation.SuppressLint;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.SlimySuccess;
import gamers.associate.Slime.items.custom.Star;
import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;

@SuppressLint("DefaultLocale") public class EndDifficultyGameLayer extends CCLayer {
	private static CCScene scene;
	private CCLabel lblScore;
	private CCSprite starSprite;
	private CCLabel unlock;
	private CCSprite spriteBg;
	private static int BgOriginalWidth = 480;
	private static int BgOriginalHeight = 360;
	private static int zBg = 0;
	private static int zNormal = 1;
	private CCSprite successSprite;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new EndDifficultyGameLayer());
		}
		
		return scene;
	}
	
	public EndDifficultyGameLayer() {	
		CCLabel label = CCLabel.makeLabel("Game Over".toUpperCase(), "fonts/Slime.ttf", 60f);
		label.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + 150f
				));	
		this.addChild(label, zNormal);
		
		this.lblScore = CCLabel.makeLabel("0".toUpperCase(), "fonts/Slime.ttf", 60.0f);
		// this.lblScore.setColor(SlimeFactory.ColorSlime);
		this.lblScore.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + 75f
				));
		this.addChild(this.lblScore, zNormal);
		
		this.starSprite = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);		
		this.starSprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + 75f
				));
		this.addChild(this.starSprite, zNormal);
		
						
		String unlockLvl = LevelDifficulty.getText(SlimeFactory.GameInfo.getDifficulty());
		String unlockTxt = "You have unlock: " + unlockLvl + " mode";
		this.unlock = CCLabel.makeLabel(unlockTxt.toUpperCase(), "fonts/Slime.ttf", 45f);
		// this.unlock.setColor(SlimeFactory.ColorSlime);
		this.unlock.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2
				));									
		
		this.addChild(this.unlock, zNormal);		
				
		CCSprite homeSpriteN = CCSprite.sprite("control-home.png", true);
		CCSprite homeSpriteS = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite goHome = CCMenuItemSprite.item(homeSpriteN, homeSpriteS, this, "goHome");
		CCMenu menu = CCMenu.menu(goHome);
		menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 - 100f
				));	
		this.addChild(menu, zNormal);
	}

	@Override
	public void onEnter() {
		int endedDifficulty = SlimeFactory.GameInfo.getPreviousDifficulty();
		this.spriteBg = LevelDifficulty.getSpriteBkg(endedDifficulty);
		this.addSpriteBg(this.spriteBg);
		
		String unlockTxt = "";
		if (SlimeFactory.GameInfo.getPreviousDifficulty() != LevelDifficulty.Extrem) {
			String unlockLvl = LevelDifficulty.getText(SlimeFactory.GameInfo.getDifficulty());
			unlockTxt = "You have unlock: " + unlockLvl + " mode";			
		} else {
			unlockTxt = "Congratulations!!!!";
		}
		
		this.unlock.setString(unlockTxt.toUpperCase());
		this.unlock.setScale(10.0f);
		CCScaleTo scale = CCScaleTo.action(0.5f, 1.0f);
		CCCallFunc call = CCCallFunc.action(this, "endTextScale");
		CCSequence seq = CCSequence.actions(scale, call);
		this.unlock.runAction(seq);
		
		String score = String.valueOf(SlimeFactory.GameInfo.getPreviousTotalScore());
		this.lblScore.setString(score.toUpperCase());
		float starPadding = -10f;
		float starX = this.lblScore.getPosition().x - this.lblScore.getContentSize().width / 2 - SlimeFactory.Star.getStarReferenceWidth() / 2 + starPadding;
		this.starSprite.setPosition(CGPoint.make(
				starX,
				this.starSprite.getPosition().y
				));
		
		SlimeFactory.ContextActivity.showAndNextAd();
		
		this.successSprite = SlimeFactory.SlimySuccess.getAnimatedSprite(SlimySuccess.getAnimationName(SlimeFactory.GameInfo.getPreviousDifficulty()));
		this.successSprite.setAnchorPoint(0.5f, 0f);
		this.successSprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2 + ((this.bgWidth / 2) * 1 / 3),
				CCDirector.sharedDirector().winSize().getHeight() / 2 - ((this.bgHeight / 2) * 1 / 2)
				));	
		this.successSprite.setOpacity(0);
		this.addChild(successSprite, zNormal);
		
		super.onEnter();
	}
	
	public void endTextScale()	{
		CCFadeIn fade = CCFadeIn.action(1.0f);
		this.successSprite.runAction(fade);
	}

	private void addSpriteBg(CCSprite sprite) {
		//sprite.setAnchorPoint(0, 0);
		sprite.setPosition(CCDirector.sharedDirector().winSize().width / 2f, CCDirector.sharedDirector().winSize().height / 2f);
		float sW = CCDirector.sharedDirector().winSize().width;
		float sH = CCDirector.sharedDirector().winSize().height;
		// Scale for full width or height visibble no deformation, black bands possible
		float scaleW = sW / BgOriginalWidth;
		float scaleH = sH / BgOriginalHeight;
		float scale = Math.min(scaleW, scaleH);
		this.bgWidth = BgOriginalWidth * scale;
		this.bgHeight = BgOriginalHeight * scale;
		sprite.setScale(scale);
		this.addChild(sprite, zBg);
	}
	
	private float bgWidth;
	private float bgHeight;

	@Override
	public void onExit() {
		SlimeFactory.ContextActivity.hideAd();
		this.removeChild(this.successSprite, true);
		this.removeChild(this.spriteBg, true);
		super.onExit();
	}

	public void goHome(Object sender) {
		Level currentLevel = Level.get(LevelHome.Id, true, GamePlay.None);								
		CCTransitionScene transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}