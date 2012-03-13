package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.items.custom.Gate;
import gamers.associate.Slime.items.custom.GateFactory;
import gamers.associate.Slime.items.custom.Star;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.actions.interval.CCScaleTo;
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

public class EndDifficultyGameLayer extends CCLayer {
	private static CCScene scene;
	private CCLabel lblScore;
	private CCSprite starSprite;
	private CCLabel unlock;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new EndDifficultyGameLayer());
		}
		
		return scene;
	}
	
	public EndDifficultyGameLayer() {
		int originalW = 800;		
		int originalH = 480;
		CCSprite spriteBg = CCSprite.sprite("game-over.png");
		spriteBg.setAnchorPoint(0, 0);
		spriteBg.setPosition(0, 0);
		float sW = CCDirector.sharedDirector().winSize().width;
		float sH = CCDirector.sharedDirector().winSize().height;
		// Scale for full width, no deformation
		float scaleW = sW / originalW;
		float scaleH = sH / originalH;
		spriteBg.setScale(Math.max(scaleW, scaleH));
		this.addChild(spriteBg, 0);		
		
		CCLabel label = CCLabel.makeLabel("Game Over".toUpperCase(), "fonts/Slime.ttf", 60f);
		label.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + 150f
				));	
		this.addChild(label);
		
		this.lblScore = CCLabel.makeLabel("0".toUpperCase(), "fonts/Slime.ttf", 60.0f);
		// this.lblScore.setColor(SlimeFactory.ColorSlime);
		this.lblScore.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + 75f
				));
		this.addChild(this.lblScore);
		
		this.starSprite = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);		
		this.starSprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + 75f
				));
		this.addChild(this.starSprite);
		
						
		String unlockLvl = LevelDifficulty.getText(SlimeFactory.GameInfo.getDifficulty());
		String unlockTxt = "You have unlock: " + unlockLvl + " mode";
		this.unlock = CCLabel.makeLabel(unlockTxt.toUpperCase(), "fonts/Slime.ttf", 45f);
		// this.unlock.setColor(SlimeFactory.ColorSlime);
		this.unlock.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2
				));									
		
		this.addChild(this.unlock);		
				
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite goHome = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goHome");
		CCMenu menu = CCMenu.menu(goHome);
		menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 - 100f
				));	
		this.addChild(menu);
	}

	@Override
	public void onEnter() {		
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
		this.unlock.runAction(scale);
		
		String score = String.valueOf(SlimeFactory.GameInfo.getPreviousTotalScore());
		this.lblScore.setString(score.toUpperCase());
		float starPadding = -10f;
		float starX = this.lblScore.getPosition().x - this.lblScore.getContentSize().width / 2 - SlimeFactory.Star.getStarReferenceWidth() / 2 + starPadding;
		this.starSprite.setPosition(CGPoint.make(
				starX,
				this.starSprite.getPosition().y
				));
		
		SlimeFactory.ContextActivity.showAndNextAd();
		super.onEnter();
	}

	@Override
	public void onExit() {
		SlimeFactory.ContextActivity.hideAd();
		super.onExit();
	}

	public void goHome(Object sender) {
		Level currentLevel = Level.get(LevelHome.Id, true);								
		CCTransitionScene transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}