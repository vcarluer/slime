package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.LevelHome;

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
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new EndDifficultyGameLayer());
		}
		
		return scene;
	}
	
	public EndDifficultyGameLayer() {
		int originalW = 1467;		
		int originalH = 800;
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
		
		CCLabel label = CCLabel.makeLabel("Game Over", "fonts/Slime.ttf", 60f);
		label.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2
				));	
		this.addChild(label);
		
		CCSprite homeSprite = CCSprite.sprite("control-home.png", true);
		CCMenuItemSprite goHome = CCMenuItemSprite.item(homeSprite, homeSprite, this, "goHome");
		CCMenu menu = CCMenu.menu(goHome);
		menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 - 150f
				));	
		this.addChild(menu);
	}
	
	public void goHome(Object sender) {
		Level currentLevel = Level.get(LevelHome.Id, true);								
		CCTransitionScene transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}