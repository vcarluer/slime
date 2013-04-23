package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.Sharer;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.ccColor3B;

import android.annotation.SuppressLint;
import android.view.MotionEvent;

@SuppressLint("DefaultLocale") public class LiteTimeAttackGameOverLayer extends CCLayer implements IBackableLayer {
	private static final float SharePadding = 25f * SlimeFactory.getWidthRatio();
	private static CCScene scene;	
	private CCLabel congratLabel;
	private CCLabel endLabel;
	private CCLabel gameOverLabel;
	private CCMenu backMenu;	
	private CCMenu shareMenu;
	private CCMenu buyTheGameMenu;
	private CCMenuItemLabel buyTheGameButton;
	private CCMenuItemSprite buyTheGameImage;
	private float yEndRelative;
	
	public static CCScene getScene() {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new LiteTimeAttackGameOverLayer());
		}
		
		return scene;
	}
	
	public LiteTimeAttackGameOverLayer() {
		HomeLayer.addBkg(this, 800, 480, "game-over.png");
		this.backMenu = HomeLayer.getBackButton(this, "goBack");
		this.congratLabel = CCLabel.makeLabel("Congratulations!!!".toUpperCase(), "fonts/Slime.ttf", 42 * SlimeFactory.getWidthRatio());
		this.endLabel = CCLabel.makeLabel("World".toUpperCase(), "fonts/Slime.ttf", 28 * SlimeFactory.getWidthRatio());
		this.gameOverLabel = CCLabel.makeLabel("Game Over!".toUpperCase(), "fonts/Slime.ttf", 68 * SlimeFactory.getWidthRatio());
		this.gameOverLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + 100 * SlimeFactory.getWidthRatio());
		this.congratLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY());
		this.yEndRelative = - (42 + 11) * SlimeFactory.getWidthRatio();
		this.endLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + this.yEndRelative);
		this.endLabel.setColor(SlimeFactory.ColorSlimeGold);		
		CCLabel label = CCLabel.makeLabel("Buy the full game to continue!".toUpperCase(), "fonts/Slime.ttf", 32f* SlimeFactory.getWidthRatio());
		this.buyTheGameButton = CCMenuItemLabel.item(label, this, "buyFullGame");		
		this.buyTheGameButton.setColor(ccColor3B.ccWHITE);	
		CCSprite spriteN = CCSprite.sprite("playstore.png");
		CCSprite spriteS = CCSprite.sprite("playstore.png");
		this.buyTheGameImage = CCMenuItemSprite.item(spriteN, spriteS, this, "buyFullGame");
		this.buyTheGameImage.setScale(SlimeFactory.getWidthRatio());		
		this.buyTheGameMenu = CCMenu.menu(this.buyTheGameImage, this.buyTheGameButton);
		this.buyTheGameMenu.alignItemsHorizontally(5 * SlimeFactory.getWidthRatio());
		this.buyTheGameMenu.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() - 150 * SlimeFactory.getWidthRatio());		
		
		this.addChild(this.gameOverLabel);
		this.addChild(this.congratLabel);
		this.addChild(this.endLabel);
		this.addChild(this.buyTheGameMenu);
		
		this.addChild(this.backMenu);
		this.setIsTouchEnabled(true);
	}
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		//this.goBack();
		return true;
	}
	
	@Override
	public void onEnter() {
		SlimeFactory.playMenuMusic();
		this.endLabel.setString(("Lite World " + SlimeFactory.PackageManager.getCurrentPackage().getName() + " finished").toUpperCase());
				
		this.shareMenu = HomeLayer.getNewShareButton(
				"Lite World " + SlimeFactory.PackageManager.getCurrentPackage().getName() + " finished in story mode !"+  " " + Sharer.twitterTag, 
				1.0f, - this.endLabel.getContentSizeRef().width / 2f - SharePadding - (HomeLayer.shareSizeW * HomeLayer.getShareScale(1.0f)) / 2f, this.endLabel.getPositionRef().y - CCDirector.sharedDirector().winSize().height / 2f);
		
		this.addChild(this.shareMenu);
		
		super.onEnter();
	}
	
	@Override
	public void onExit() {
		if (this.shareMenu != null) {
			this.removeChild(this.shareMenu, true);
			this.shareMenu = null;
		}
		super.onExit();
	}

	public void goBack(Object sender) {
		goBack();
	}
	
	public void buyFullGame(Object sender) {
		SlimeFactory.ContextActivity.buyGame();
	}

	public void goBack() {
		Sounds.playEffect(R.raw.menuselect, true);
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
}
