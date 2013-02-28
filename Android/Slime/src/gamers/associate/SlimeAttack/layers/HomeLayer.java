package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.game.Sharer;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.custom.MenuSprite;
import gamers.associate.SlimeAttack.items.custom.SpawnPortal;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.annotation.SuppressLint;
import android.view.MotionEvent;

@SuppressLint("DefaultLocale") 
public class HomeLayer extends CCLayer {
	public static final int shareSizeW = 127;
	public static final int shareSizeH = 70;
	public static float shareScale = 0.7f;
	private static HomeLayer layer;	
	
	private SpawnPortal spawner;
	
	private static float baseShift = 150f; //100f;
	private static float shiftTitle = baseShift;
	private static float shiftMenu  = shiftTitle - 150f; // Slime height = 160 / 2 + 20
	private static float shiftBeta = shiftMenu - 150f;
	
	private CCSprite titleSprite;
	private CCMenu shareMenu;
	
	private CCLayer top;
	
	private CCMenu creditMenu;
	
	private CCLabel betaLabel;
	
	public static HomeLayer get() {
		if (layer == null) {
			layer = new HomeLayer();
		}
		
		return layer;
	}
	
	protected HomeLayer() {
		super();		
		
		this.setIsTouchEnabled(true);
		
		CCSprite playSpriteNorm = CCSprite.sprite("control-play.png", true);
		CCSprite playSpriteSel = CCSprite.sprite("control-play.png", true);
		CCMenuItemSprite playMenu = CCMenuItemSprite.item(playSpriteNorm, playSpriteSel, this, "selectPlay");
		
		this.menu = CCMenu.menu(playMenu);
		this.menu.alignItemsHorizontally(50);
		this.menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + shiftMenu
				));			
				
		this.addChild(this.menu);
		
		CCSprite creditNorm = CCSprite.sprite("ga128.png");
		CCSprite creditSel = CCSprite.sprite("ga128.png");
		CCMenuItemSprite creditSprite = CCMenuItemSprite.item(creditNorm, creditSel, this, "showCredit");
		float creditScale = 0.5f;
		creditSprite.setScale(creditScale);
		this.creditMenu = CCMenu.menu(creditSprite);
		
		this.creditMenu.setPosition(CCDirector.sharedDirector().winSize().getWidth() - (64 * creditScale) - PauseLayer.PaddingX, CCDirector.sharedDirector().winSize().getHeight() - (64 * creditScale) - PauseLayer.PaddingY);
		this.addChild(this.creditMenu);
		
		if (SlimeFactory.isBeta) {
			this.betaLabel = SlimeFactory.getLabel("BETA 1302", 42f);
			this.betaLabel.setColor(ccColor3B.ccRED);
			this.betaLabel.setPosition(CGPoint.make(
					CCDirector.sharedDirector().winSize().getWidth() / 2,
					CCDirector.sharedDirector().winSize().getHeight() / 2 + shiftBeta
					));	
			this.addChild(this.betaLabel);
		}		
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if (this.spawner != null) {
			this.spawner.spawn();
		}
		
		return true;		
	}
	
	private CCMenu menu;
	
	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {		
		super.onEnter();
		Sounds.setEffectsDisable(true);
		
		// share button		
		float shareX = - CCDirector.sharedDirector().winSize().getWidth() / 2 +((shareSizeW * shareScale * SlimeFactory.Density) + PauseLayer.PaddingX) / 2 ;
		float shareY = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((shareSizeH * shareScale * SlimeFactory.Density) + PauseLayer.PaddingY) / 2;
		
		this.shareMenu = HomeLayer.getNewShareButton(null, 1.0f, shareX, shareY);
		this.addChild(this.shareMenu);
		
		SlimeFactory.playMenuMusic();
		
		this.temp();
		
		SlimeFactory.ContextActivity.hideAd();
	}
	
	public void temp() {
		this.titleSprite = CCSprite.sprite("slime-attack.png");
		this.top = CCLayer.node();
		this.addChild(this.top, 1);
		this.top.addChild(this.titleSprite);

		this.titleSprite.setPosition(CCDirector.sharedDirector().winSize().width / 2, CCDirector.sharedDirector().winSize().height / 2 + 150f);						
		this.titleSprite.setScale(10f * SlimeFactory.SGSDensity);
		CCScaleTo sc = CCScaleTo.action(0.5f, 1f , 1f);
		this.titleSprite.runAction(sc);
		
		this.actionTitle();
	}
	
	public void endTitle() {
		float scale = 0.5f  * SlimeFactory.SGSDensity;
		CCScaleTo sc2 = CCScaleTo.action(0.2f, scale);
		this.titleSprite.runAction(sc2);
	}
	
	public void actionTitle() {
		CCRotateTo r0 = CCRotateTo.action(0, 0);		
		CCDelayTime d = CCDelayTime.action(0.5f);
		CCRotateBy r1 = CCRotateBy.action(0.3f, 25f);
		CCRotateBy r2 = CCRotateBy.action(0.3f, -5f);
		CCDelayTime d1 = CCDelayTime.action(0.1f);
		CCRotateBy r3 = CCRotateBy.action(0.3f, 5f);		
		CCSequence seqTitle = CCSequence.actions(r0, d, r1, r2, d1, r3);
		this.titleSprite.runAction(seqTitle);
	}
	
	@Override
	public void onExit() {
		this.removeChild(this.shareMenu, true);
		this.removeChild(this.top, true);
		Sounds.setEffectsDisable(false);
		super.onExit();
	}

	public void selectPlay(Object sender) {		
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, ChooseModeLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
	public void showCredit(Object sender) {
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, CreditLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}

	public SpawnPortal getSpawner() {
		return spawner;
	}

	public void setSpawner(SpawnPortal spawner) {
		this.spawner = spawner;
	}
	
	public static CCMenu getHomeMenuButton(CCNode target, String targetMethod) {
		return getMenuButton("control-home.png", target, targetMethod);		
	}
	
	public static CCMenu getBackButton(CCNode target, String targetMethod) {
		return getMenuButton("control-back.png", target, targetMethod);
	}
	
	public static CCMenu getPauseButton(CCNode target, String targetMethod) {
		return getMenuButton("control-pause-look.png", target, targetMethod, false);
	}
	
	public static CCMenu getMuteOnButton(CCNode target, String targetMethod) {
		return getMenuButton("control-sound-on.png", target, targetMethod, false);
	}
	
	public static CCMenu getMuteOffButton(CCNode target, String targetMethod) {
		return getMenuButton("control-sound-off.png", target, targetMethod, false);
	}
	
	public static CCMenu getMenuButton(String spriteName, CCNode target, String targetMethod) {
		return getMenuButton(spriteName, target, targetMethod, true);
	}
	
	public static CCMenu getMenuButton(String spriteName, CCNode target, String targetMethod, boolean isFrame) {
		float left = - CCDirector.sharedDirector().winSize().getWidth() / 2 + ((MenuSprite.Width * PauseLayer.Scale) + PauseLayer.PaddingX) / 2 ;
		float top = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * PauseLayer.Scale) + PauseLayer.PaddingY) / 2;
		return getMenuButton(spriteName, left, top, target, targetMethod, isFrame);
	}
	
	public static CCMenu getMenuButton(String spriteName, float x, float y, CCNode target, String targetMethod) {
		return getMenuButton(spriteName, x, y, target, targetMethod, true);
	}
	
	public static CCMenu getMenuButton(String spriteName, float x, float y, CCNode target, String targetMethod, boolean isFrame) {
		CCSprite spriteN = null;
		CCSprite spriteS = null;
		if (!isFrame) {
			spriteN = CCSprite.sprite(spriteName);
			spriteS = CCSprite.sprite(spriteName);
		} else {
			spriteN = CCSprite.sprite(spriteName, true);
			spriteS = CCSprite.sprite(spriteName, true);
		}
		
		CCMenuItemSprite go = CCMenuItemSprite.item(spriteN, spriteS, target, targetMethod);
		go.setScale(PauseLayer.Scale);
		
		go.setPosition(x, y);
		CCMenu menu = CCMenu.menu(go);
		return menu;
	}
	
	public static CCSprite addBkgChangeDiff(CCNode node) {
		return addBkg(node, 1467, 800, "change-difficulty.png");		
	}
	
	public static CCSprite addBkgSplash(CCNode node) {
		return addBkg(node, 800, 480, "splash-level.png");
	}
	
	public static CCSprite addBkgGameOver(CCNode node) {
		return addBkg(node, 800, 480, "game-over.png");
	}
	
	public static CCSprite addBkg(CCNode node, int w, int h, String sprite) {
		int originalW = w;		
		int originalH = h;
		CCSprite spriteBg = CCSprite.sprite(sprite);
		spriteBg.setAnchorPoint(0, 0);
		spriteBg.setPosition(0, 0);
		float sW = CCDirector.sharedDirector().winSize().width;
		float sH = CCDirector.sharedDirector().winSize().height;
		// Scale for full width, no deformation
		float scaleW = sW / originalW;
		float scaleH = sH / originalH;
		spriteBg.setScale(Math.max(scaleW, scaleH));
		node.addChild(spriteBg, -1);
		return spriteBg;
	}
	
	public static CCMenu getNewShareButton(String shareMessage, float scale, float shareX, float shareY) {
		// share button
		Sharer sharer = new Sharer();
		if (shareMessage != null && shareMessage != "" ) {
			sharer.setShareMessage(shareMessage);
		}
		
		CCSprite shareN = CCSprite.sprite("control-share.png");
		CCSprite shareS = CCSprite.sprite("control-share.png");
//		shareN.setColor(SlimeFactory.ColorSlimeBorder);
//		shareS.setColor(SlimeFactory.ColorSlimeBorder);
		CCMenuItemSprite shareItem = CCMenuItemSprite.item(shareN, shareS, sharer, "shareApp");
		
		shareItem.setPosition(shareX, shareY);
		float baseScale = scale * shareScale * SlimeFactory.Density;
		shareItem.setScale(baseScale);
		
		CCScaleBy scaleBy = CCScaleBy.action(0.3f, 0.3f);
		CCScaleTo scaleTo = CCScaleTo.action(0.3f, baseScale);
		CCDelayTime delay = CCDelayTime.action(2.0f);
		CCSequence seq = CCSequence.actions(scaleBy, scaleTo, delay);
		CCRepeatForever rep = CCRepeatForever.action(seq);
		shareItem.runAction(rep);
		
		return CCMenu.menu(shareItem);
	}
}
