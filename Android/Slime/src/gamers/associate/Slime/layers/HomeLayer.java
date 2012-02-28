package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.Vibe;
import gamers.associate.Slime.items.custom.MenuSprite;
import gamers.associate.Slime.items.custom.SpawnPortal;
import gamers.associate.Slime.items.custom.Star;

import org.apache.http.conn.ClientConnectionRequest;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.camera.CCOrbitCamera;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.tile.CCFadeOutDownTiles;
import org.cocos2d.actions.tile.CCFadeOutUpTiles;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCSlideInTTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccGridSize;

import android.view.MotionEvent;

public class HomeLayer extends CCLayer {
	private static HomeLayer layer;	
	// private CCMenuItemSprite restartMenu;
	private CCLabel lblLevel;
	private CCLabel lblScore;
	private CCSprite starSprite;
	//private CCSprite arrow;
	private CCMenu menuInfo;
	//private CCSprite diffSpr;
	private SpawnPortal spawner;
	
	private static float baseShift = 150f; //100f;
	private static float shiftTitle = baseShift;
	private static float shiftMenu  = shiftTitle - 150f; // Slime height = 160 / 2 + 20
	private static float shiftInfo  = shiftMenu - 100f;
	private static float shiftScore = shiftMenu - 100f; // shiftInfo - 70f;
	private static float paddingDiff = 50f;
	
	private float shiftArrow = -60f;
	
	private CCSprite titleSprite;
	private CCMenu restartMenu;
	
	private boolean nextDoNotStopMusic;
	
	private CCLayer top;
	private static boolean firstLoadDone;
	
	public static HomeLayer get() {
		if (layer == null) {
			layer = new HomeLayer();
		}
		
		return layer;
	}
	
	protected HomeLayer() {
		super();
		
		
		
		this.setIsTouchEnabled(true);
		
		CCSprite playSprite = CCSprite.sprite("control-play.png", true);
		CCMenuItemSprite playMenu = CCMenuItemSprite.item(playSprite, playSprite, this, "selectPlay");
		/*CCSprite restartSprite = CCSprite.sprite("control-restart.png", true);
		this.restartMenu = CCMenuItemSprite.item(restartSprite, restartSprite, this, "goRestart");
		restartMenu.setScale(0.5f);*/			
		
		// this.menu = CCMenu.menu(playMenu, this.restartMenu);		
		this.menu = CCMenu.menu(playMenu);
		this.menu.alignItemsHorizontally(50);
		this.menu.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + shiftMenu
				));			
		
		this.addChild(this.menu);
					
				
		this.lblScore = CCLabel.makeLabel("0".toUpperCase(), "fonts/Slime.ttf", 60.0f);
		this.lblScore.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + shiftScore
				));
		this.addChild(this.lblScore);
				
		this.starSprite = CCSprite.sprite("star-01.png", true); // SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);		
		this.starSprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + shiftScore
				));
		this.addChild(this.starSprite);				
		
		/*this.arrow = CCSprite.sprite("arrow.png");
		float arrowSize = 50f;
		float as = arrowSize / 69;
		this.arrow.setScale(as);
		this.addChild(this.arrow);*/
	}
	
	public void changeDifficulty(Object sender) {
		this.nextDoNotStopMusic = true;
		CCTransitionScene transition = CCSlideInTTransition.transition(0.5f, ChangeDifficultyLayer.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if (this.spawner != null) {
			this.spawner.spawn();
		}
		// TODO: Go to level selection		
		// return super.ccTouchesEnded(event);
		// this.selectPlay(this);
		return true;
		//CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
		//return CCTouchDispatcher.kEventHandled;
	}
	
	private CCMenu menu;
	
	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {		
		super.onEnter();
				
		CCSprite restartSprite = CCSprite.sprite("control-restart.png", true);
		CCMenuItemSprite restartItem = CCMenuItemSprite.item(restartSprite, restartSprite, this, "changeDifficulty");
		restartItem.setScale(PauseLayer.Scale);		
		
		float left = - CCDirector.sharedDirector().winSize().getWidth() / 2 + ((MenuSprite.Width * PauseLayer.Scale) + PauseLayer.PaddingX) / 2 ;
		float top = CCDirector.sharedDirector().winSize().getHeight() / 2 - ((MenuSprite.Height * PauseLayer.Scale) + PauseLayer.PaddingY) / 2;
		restartItem.setPosition(left, top);
		this.restartMenu = CCMenu.menu(restartItem);		
		this.addChild(this.restartMenu);
		
		String diff =LevelDifficulty.getText(SlimeFactory.GameInfo.getDifficulty());		
		String lvl = String.valueOf(SlimeFactory.GameInfo.getLevelNum());
		String lvlMax = String.valueOf(SlimeFactory.GameInfo.getLevelMax());
		String info = diff + " " + lvl + " / " + lvlMax;
		this.lblLevel = CCLabel.makeLabel(info.toUpperCase().toUpperCase(), "fonts/Slime.ttf", 45f);		
		/*CCMenuItemLabel menuLevelInfo = CCMenuItemLabel.item(this.lblLevel, this, "changeDifficulty");		
		menuLevelInfo.setPosition(
				restartItem.getPosition().x + restartSprite.getContentSize().width / 2 + this.lblLevel.getContentSize().width / 2 + PauseLayer.PaddingX, 
				CCDirector.sharedDirector().winSize().getHeight() / 2 - this.lblLevel.getContentSize().height + PauseLayer.PaddingY
				);
		this.menuInfo = CCMenu.menu(menuLevelInfo);
		this.addChild(menuInfo);					*/	
		this.lblLevel.setPosition(
				CCDirector.sharedDirector().winSize().width / 2 + restartItem.getPosition().x + restartSprite.getContentSize().width / 2 + this.lblLevel.getContentSize().width / 2 + PauseLayer.PaddingX, 
				CCDirector.sharedDirector().winSize().height / 2 + CCDirector.sharedDirector().winSize().getHeight() / 2 - this.lblLevel.getContentSize().height + PauseLayer.PaddingY
				);
		this.lblLevel.setColor(SlimeFactory.ColorSlime);
		this.addChild(this.lblLevel);
		
		String score = String.valueOf(SlimeFactory.GameInfo.getTotalScore());
		this.lblScore.setString(score.toUpperCase());
		this.lblScore.setColor(SlimeFactory.ColorSlime);		
		
		float starPadding = -10f;
		float starX = this.lblScore.getPosition().x - this.lblScore.getContentSize().width / 2 - SlimeFactory.Star.getStarReferenceWidth() / 2 + starPadding;
		this.starSprite.setPosition(CGPoint.make(
				starX,
				this.starSprite.getPosition().y
				));
		
		/*this.arrow.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2 - (this.lblLevel.getContentSize().width / 2) + this.shiftArrow,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + shiftInfo
				));				
		CCMoveBy mb = CCMoveBy.action(0.2f, CGPoint.make(10, 0));
		CCSequence seq = CCSequence.actions(mb, mb.reverse());
		CCRepeatForever rep = CCRepeatForever.action(seq);
		this.arrow.runAction(rep);				
		
		this.diffSpr = ChangeDifficultyLayer.getLevelSprite(SlimeFactory.GameInfo.getDifficulty(), true);
		this.diffSpr.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().getWidth() / 2 + (133 / 2) + paddingDiff,
				CCDirector.sharedDirector().winSize().getHeight() / 2 + shiftMenu
				));
		this.addChild(this.diffSpr);*/
		
		this.starSprite.runAction(SlimeFactory.Star.getAnimation(Star.Anim_Wait));
				
//		this.titleSprite = CCSprite.sprite("slime-attack.png");
//		this.addChild(this.titleSprite);
		
//		float scale = 0.5f;
//		this.titleSprite.setScale(scale);
//		this.titleSprite.setPosition(CGPoint.make(
//				CCDirector.sharedDirector().winSize().width - (this.titleSprite.getContentSize().width / 2) * scale - PauseLayer.PaddingX,
//				CCDirector.sharedDirector().winSize().height - (this.titleSprite.getContentSize().height / 2) * scale - PauseLayer.PaddingY
//				)); // + shiftTitle
		// this.titleSprite.setAnchorPoint(0, 0);
//		this.titleSprite.setPosition(
//				CCDirector.sharedDirector().winSize().width / 2,
//				CCDirector.sharedDirector().winSize().height / 2 + shiftTitle
//				);
//		CCRotateTo r0 = CCRotateTo.action(0, 0);		
//		CCDelayTime d = CCDelayTime.action(0.5f);
//		CCRotateBy r1 = CCRotateBy.action(0.3f, 25f);
//		CCRotateBy r2 = CCRotateBy.action(0.3f, -5f);
//		CCDelayTime d1 = CCDelayTime.action(0.1f);
//		CCRotateBy r3 = CCRotateBy.action(0.3f, 5f);		
//		CCSequence seqTitle = CCSequence.actions(r0, d, r1, r2, d1, r3);
//		this.titleSprite.runAction(seqTitle);
		
		Sounds.playMusic(R.raw.menumusic, true);
		
		
		this.temp();
	}
	
	public void temp() {
		this.titleSprite = CCSprite.sprite("slime-attack.png");
		this.top = CCLayer.node();
		this.addChild(this.top, 1);
		this.top.addChild(this.titleSprite);
		if (!firstLoadDone) {					
			this.titleSprite.setPosition(CCDirector.sharedDirector().winSize().width / 2, CCDirector.sharedDirector().winSize().height / 2 + 115f);						
			this.titleSprite.setScale(10f);
			// CCDelayTime delay = CCDelayTime.action(0.5f);
			CCScaleTo sc = CCScaleTo.action(0.5f, 1f , 1f);
			CCDelayTime d2 = CCDelayTime.action(2f);
			CCCallFunc call = CCCallFunc.action(this, "endTitle");
			
			CCSequence act = CCSequence.actions(sc, d2, call);
			this.titleSprite.runAction(act);
			firstLoadDone = true;
		} else {
			float scale = 0.5f;
			this.titleSprite.setScale(scale);
			this.titleSprite.setPosition(
					CCDirector.sharedDirector().winSize().width - (this.titleSprite.getContentSize().width / 2) * scale - PauseLayer.PaddingX,
					CCDirector.sharedDirector().winSize().height - (this.titleSprite.getContentSize().height / 2) * scale - PauseLayer.PaddingY
					);
			this.actionTitle();
		}
	}
	
	public void endTitle() {
		float scale = 0.5f;
		CCMoveTo mt = CCMoveTo.action(0.2f, CGPoint.ccp(
				CCDirector.sharedDirector().winSize().width - (this.titleSprite.getContentSize().width / 2) * scale - PauseLayer.PaddingX,
				CCDirector.sharedDirector().winSize().height - (this.titleSprite.getContentSize().height / 2) * scale - PauseLayer.PaddingY
				));
		CCScaleTo sc2 = CCScaleTo.action(0.2f, scale);
		CCCallFunc call = CCCallFunc.action(this, "actionTitle");
		CCSequence seq = CCSequence.actions(sc2, call);
		this.titleSprite.runAction(mt);
		this.titleSprite.runAction(seq);
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
		// this.removeChild(this.menuInfo, true);
		this.removeChild(this.lblLevel, true);
		// this.removeChild(this.titleSprite, true);
		this.removeChild(this.restartMenu, true);		
		this.removeChild(this.top, true);
		// this.removeChild(this.diffSpr, true);
		
		if (!this.nextDoNotStopMusic) {
			Sounds.pauseMusic();
		} else {
			this.nextDoNotStopMusic = false;
		}
		
		super.onExit();
	}

	public void selectPlay(Object sender) {
		// Sounds.playEffect(R.raw.menuselect);		
		// CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
//		CCTransitionScene transition = CCMoveInRTransition.transition(0.5f, LevelSelection.get().getScene());
//		CCDirector.sharedDirector().replaceScene(transition);
		
		Vibe.vibrate();
		Sounds.playEffect(R.raw.menuselect);
		Sounds.stopMusic();
		SlimeFactory.LevelBuilder.start();
	}
	
	public void goRestart(Object sender) {
		Sounds.playEffect(R.raw.menuselect);		
		// Sounds.pauseMusic();
		SlimeFactory.LevelBuilder.resetAllAndRun();
	}

	public SpawnPortal getSpawner() {
		return spawner;
	}

	public void setSpawner(SpawnPortal spawner) {
		this.spawner = spawner;
	}
}
