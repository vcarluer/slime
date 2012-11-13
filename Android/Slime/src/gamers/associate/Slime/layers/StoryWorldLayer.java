package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.SlimeFactory;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;

public class StoryWorldLayer extends CCLayer {
	private static final int fontSize = 60;
	private static CCScene scene;
	private CCLabel title;
	private CCMenu menuToRight;
	private CCMenu menuToLeft;
	
	private static int paddingTitle = fontSize + 5;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new StoryWorldLayer());
		}
		
		return scene;
	}
	
	public StoryWorldLayer() {
		HomeLayer.addBkgSplash(this);
		this.addChild(HomeLayer.getBackButton(this, "goBack"));
		
		this.title = CCLabel.makeLabel("To mexico!".toUpperCase(), "fonts/Slime.ttf", fontSize);
		this.title.setColor(SlimeFactory.ColorSlime);
		this.title.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2, CCDirector.sharedDirector().winSize().getHeight() - paddingTitle);
		this.addChild(this.title);
		
		CCSprite spriteN = CCSprite.sprite("arrow.png");
		CCSprite spriteS = CCSprite.sprite("arrow.png");
		spriteN.setRotation(180f);
		spriteS.setRotation(180f);
		CCMenuItemSprite leftArrow = CCMenuItemSprite.item(spriteN, spriteS, this, "toLeft");
		leftArrow.setScale(PauseLayer.Scale);
		this.menuToLeft = CCMenu.menu(leftArrow);
		this.menuToLeft.setPosition(- CCDirector.sharedDirector().winSize().getWidth() / 2 + PauseLayer.arrowWidth / 2, 0);
		this.addChild(this.menuToLeft);		
		
		CCSprite spriteRN = CCSprite.sprite("arrow.png");
		CCSprite spriteRS = CCSprite.sprite("arrow.png");
		CCMenuItemSprite rightArrow = CCMenuItemSprite.item(spriteRN, spriteRS, this, "toRight");
		rightArrow.setScale(PauseLayer.Scale);
		this.menuToRight = CCMenu.menu(rightArrow);
		this.menuToRight.setPosition(CCDirector.sharedDirector().winSize().getWidth() / 2 - PauseLayer.arrowWidth / 2, 0);
		this.addChild(this.menuToRight);
	}
	
	public void goBack(Object sender) {
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
