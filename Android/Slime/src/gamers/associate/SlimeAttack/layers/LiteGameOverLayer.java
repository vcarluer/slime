package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
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

@SuppressLint("DefaultLocale") public class LiteGameOverLayer extends CCLayer implements IBackableLayer {
	private static CCScene scene;	
	private static int basePaddingMark = 65;
	private static int basePaddingCongrat = 100;
	private CCLabel gameOverLabel;
	private CCLabel congratLabel;
	private CCLabel mark1;
	private CCLabel mark2;
	
	private CCMenu backMenu;	
	private CCMenu buyTheGameMenu;
	private CCMenuItemLabel buyTheGameButton;
	private CCMenuItemSprite buyTheGameImage;
	
	public static CCScene getScene(String mode) {
		if (scene == null) {
			scene = CCScene.node();
			scene.addChild(new LiteGameOverLayer(mode));
		}
		
		return scene;
	}
	
	public LiteGameOverLayer(String mode) {
		HomeLayer.addBkg(this, 800, 480, "game-over.png");
		this.backMenu = HomeLayer.getBackButton(this, "goBack");
		
		String goTitle = " ";
		if (mode.equals(SlimeFactory.LiteSurvival)) {
			goTitle = "Unlock " + mode + " mode";
		} else {
			goTitle = "Lite " + mode + " Over";
		}
		
		this.gameOverLabel = CCLabel.makeLabel(goTitle.toUpperCase(), "fonts/Slime.ttf", 52 * SlimeFactory.getWidthRatio());
		this.gameOverLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + (basePaddingCongrat + 52) * SlimeFactory.getWidthRatio());
		
		String congLabel = " ";
		if (!mode.equals(SlimeFactory.LiteSurvival)) {			
			congLabel = "Congratulations!";
		}
		
		this.congratLabel = CCLabel.makeLabel(congLabel.toUpperCase(), "fonts/Slime.ttf", 42 * SlimeFactory.getWidthRatio());		
		this.congratLabel.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() + basePaddingCongrat* SlimeFactory.getWidthRatio());
		
		CCLabel label = CCLabel.makeLabel("Buy the full game".toUpperCase(), "fonts/Slime.ttf", 42f* SlimeFactory.getWidthRatio());
		this.buyTheGameButton = CCMenuItemLabel.item(label, this, "buyFullGame");		
		this.buyTheGameButton.setColor(ccColor3B.ccWHITE);	
		CCSprite spriteN = CCSprite.sprite("playstore.png");
		CCSprite spriteS = CCSprite.sprite("playstore.png");
		this.buyTheGameImage = CCMenuItemSprite.item(spriteN, spriteS, this, "buyFullGame");
		this.buyTheGameImage.setScale(SlimeFactory.getWidthRatio());		
		this.buyTheGameMenu = CCMenu.menu(this.buyTheGameImage, this.buyTheGameButton);
		this.buyTheGameMenu.alignItemsHorizontally(10 * SlimeFactory.getWidthRatio());
		this.buyTheGameMenu.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY());
		
		this.mark1 = CCLabel.makeLabel("80 fun levels".toUpperCase(), "fonts/Slime.ttf", 37f * SlimeFactory.getWidthRatio());		
		this.mark1.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() - (basePaddingMark + 47)* SlimeFactory.getWidthRatio());
		this.mark1.setColor(SlimeFactory.ColorSlimeGold);
		
		this.mark2 = CCLabel.makeLabel("Survival mode challenge".toUpperCase(), "fonts/Slime.ttf", 37f * SlimeFactory.getWidthRatio());		
		this.mark2.setPosition(SlimeFactory.getScreenMidX(), SlimeFactory.getScreenMidY() - (basePaddingMark + 94)* SlimeFactory.getWidthRatio());
		this.mark2.setColor(SlimeFactory.ColorSlimeGold);	
				
		this.addChild(this.gameOverLabel);
		this.addChild(this.congratLabel);		
		this.addChild(this.buyTheGameMenu);
		this.addChild(this.mark1);
		this.addChild(this.mark2);
		
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
		float baseScale = this.buyTheGameButton.getScale();
		float moveScale = baseScale * 1.1f;
		CCScaleBy sb = CCScaleBy.action(0.3f, moveScale);
		CCScaleTo st = CCScaleTo.action(0.3f, baseScale);
		CCDelayTime delay = CCDelayTime.action(4f);
		CCSequence seq = CCSequence.actions(sb, st, delay);
		CCRepeatForever rep = CCRepeatForever.action(seq);
		this.buyTheGameButton.runAction(rep);
		
		super.onEnter();
	}
	
	@Override
	public void onExit() {		
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
