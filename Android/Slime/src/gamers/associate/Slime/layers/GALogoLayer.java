package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LoadingQuoteGenerator;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.levels.LevelHome;
import gamers.associate.Slime.levels.generator.BlocInfoParser;
import gamers.associate.Slime.levels.generator.hardcoded.BlocHardInit;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeDownTransition;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCShrinkGrowTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

public class GALogoLayer extends CCLayer {	
	private static CCScene scene;	
	// private long waitLogoSec = 2;
	// private long onEnterTime;
	private CCSprite sprite;
	private float scaleTarget;
	private static Level currentLevel;
	private static GALogoLayer logoLayer;
	private static boolean loaded;
	private final Object lock = new Object();
	private CCLabel loadLabel;
	private long nextBeat;
	private int step;
	private static boolean heartBeating;
	
	public static CCScene scene() {
		if (scene == null) {
			scene = CCScene.node();
			CCColorLayer colorLayer = CCColorLayer.node(new ccColor4B(255, 255, 255, 255), 
					CCDirector.sharedDirector().winSize().width, 
					CCDirector.sharedDirector().winSize().height);
					
			scene.addChild(colorLayer, 0);
			logoLayer = new GALogoLayer();
			scene.addChild(logoLayer, 1);
		}
		
		return scene;
	}
	
	protected GALogoLayer() {		
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {		
		super.onEnter();
				
//		this.spriteSheet = SpriteSheetFactory.getSpriteSheet("logo", true);
//		this.addChild(this.spriteSheet);
//		CCSpriteFrame spriteFrame = CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("JulenGarciaGA.png");
//		this.sprite = CCSprite.sprite(spriteFrame);
//		this.spriteSheet.addChild(this.sprite);
		
		loaded = false;
		heartBeating = false;
		Sounds.preloadEffect(R.raw.ga);
		Sounds.preloadEffect(R.raw.heartbeat);
		
		this.sprite = CCSprite.sprite("gamers associate.png");
		this.addChild(this.sprite);
		this.sprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));				
		
		// Scaling to screen height
		float originalImageHeight = 640f;
		float targetHeight = CCDirector.sharedDirector().winSize().getHeight();			
		scaleTarget = targetHeight / originalImageHeight;
		this.sprite.setScale(10.0f);
		CCScaleTo scaleTo = CCScaleTo.action(0.3f, scaleTarget);
		CCDelayTime delay = CCDelayTime.action(0.2f);
		CCCallFunc call = CCCallFunc.action(this, "endScale");
		CCSequence seq = CCSequence.actions(scaleTo, delay,  call);
		this.sprite.runAction(seq);
		
		this.loadLabel = CCLabel.makeLabel(LoadingQuoteGenerator.NewQuote().toUpperCase(), "fonts/Slime.ttf", 15f);		
		this.loadLabel.setPosition(CCDirector.sharedDirector().winSize().width - this.loadLabel.getContentSize().width - PauseLayer.PaddingX, PauseLayer.PaddingY);
		this.loadLabel.setAnchorPoint(0, 0);
		this.loadLabel.setColor(ccColor3B.ccBLACK);
		this.loadLabel.setVisible(false);
		this.addChild(this.loadLabel);
		
		// schedule(nextCallback, waitLogoSec); doesn't work?
//		schedule(nextCallback);
//		this.onEnterTime = System.currentTimeMillis();
	}		
	
	@Override
	public void onExit() {
		this.removeChild(this.sprite, true);
		this.removeChild(this.loadLabel, true);
		super.onExit();
	}

	public void endScale() {
		/*float soundTime = 1.0f;
		float waitTime = 2.0f;*/
		Sounds.playEffect(R.raw.ga);		
		
		// CCDelayTime delay = CCDelayTime.action(waitTime);
//		CCDelayTime delay2 = CCDelayTime.action(1f);
//		CCScaleBy sb = CCScaleBy.action(0.1f, 1.10f);
//		CCScaleTo st = CCScaleTo.action(0.1f, this.scaleTarget);
//		CCCallFunc ps = CCCallFunc.action(this, "heartbeat");
//		// CCCallFunc call = CCCallFunc.action(this, "load");
//		CCSequence seq = CCSequence.actions(delay2, sb, st, ps);
//		CCRepeatForever rep = CCRepeatForever.action(seq);
//		this.sprite.runAction(rep);
		this.step = 0;
		this.nextBeat = System.currentTimeMillis() + 1000;
		this.schedule(nextCallback);		
//		LoadThread th = new LoadThread();
//		th.start();
	}
	
	public void heartbeat() {		
		if (!loaded) {
			this.loadLabel.setVisible(true);
			this.loadLabel.setString(LoadingQuoteGenerator.NewQuote().toUpperCase());
			this.loadLabel.setPosition(CCDirector.sharedDirector().winSize().width - this.loadLabel.getContentSize().width - PauseLayer.PaddingX, PauseLayer.PaddingY);
			Sounds.playEffect(R.raw.heartbeat);
			
			heartBeating = true;
			CCScaleBy sb = CCScaleBy.action(0.1f, 1.10f);
			CCScaleTo st = CCScaleTo.action(0.1f, this.scaleTarget);
			CCCallFunc ps = CCCallFunc.action(this, "heartbeatEnd");
			CCSequence seq = CCSequence.actions(sb, st, ps);
			this.sprite.runAction(seq);
		}
	}
	
	public void heartbeatEnd() {
		heartBeating = false;
		this.nextBeat = System.currentTimeMillis() + 1000;
	}
	
	public void load() {		
		// Sounds.playMusic(R.raw.menumusic, true);
		/* CCScene nextScene = SlimeLoadingLayer.scene();
		CCDirector.sharedDirector().replaceScene(nextScene);*/ 
//		 CCTransitionScene transition = CCFadeTransition.transition(1.0f, currentLevel.getScene());		
//		 CCDirector.sharedDirector().replaceScene(transition); 
		loaded = true;
		// CCDirector.sharedDirector().replaceScene(currentLevel.getScene());
		
		// CCJumpZoomTransition transition = CCJumpZoomTransition.transition(1.0f, currentLevel.getScene());		
		CCTransitionScene transition = CCFadeTransition.transition(0.5f, currentLevel.getScene());
		CCDirector.sharedDirector().replaceScene(transition);
	}
	
//	private UpdateCallback nextCallback = new UpdateCallback() {
//			
//			public void update(float d) {		
//				long elapsed = (System.currentTimeMillis() - onEnterTime) / 1000;
//				if (elapsed > waitLogoSec) {
//					unschedule(nextCallback);
//					// spriteSheet.removeChild(sprite, true);
//					removeChild(sprite, true);
//					
//					CCScene nextScene = SlimeLoadingLayer.scene();
//					CCDirector.sharedDirector().replaceScene(nextScene);
//				}
//			}
//		};
	private UpdateCallback nextCallback = new UpdateCallback() {
		
		public void update(float d) {
			
			if (!loaded && System.currentTimeMillis() > nextBeat && !heartBeating) {
				heartbeat();
			} else {
				if (!heartBeating) {
					switch (step) {
					case 0:
						SpriteSheetFactory.add("controls", true, SpriteSheetFactory.zDefault);
						break;
					case 1:
						SpriteSheetFactory.add("decor", true, SpriteSheetFactory.zDefault);
						break;
					case 2:					
						SpriteSheetFactory.add("items", Level.zFront);
						break;
					case 3:
						SpriteSheetFactory.add("slime", Level.zTop);
						break;
					case 4:
						SpriteSheetFactory.add("red", Level.zTop);
						break;
					case 5:
						SpriteSheetFactory.add("slimydbz", Level.zFront);
						break;
					case 6:
						SpriteSheetFactory.add("glasswork", Level.zMid);
						break;
					case 7:
						SpriteSheetFactory.add("tank", Level.zFront);
						break;
					case 8:
						SpriteSheetFactory.add("worlds-items", Level.zMid);
						break;
					case 9:
						SpriteSheetFactory.add("arrows", Level.zMid);
						break;
					case 10:	
						Sounds.preload();
						break;
					case 11:
						BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorCorridor2);
						break;
					case 12:
						BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorRectangle2);
						break;
					case 13:
						BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorTutorial);
						break;
					case 14:
						BlocHardInit.InitHardCoded();
						break;
					case 15:
						SpriteSheetFactory.add("control-stars", true, Level.zFront);
						break;
					case 16:
						SpriteSheetFactory.add("success", true, Level.zTop);
						break;
					case 17:						
						currentLevel = Level.get(LevelHome.Id);
						unschedule(nextCallback);
						load();											
						break;
					}
		
					step++;
				}
			}
//			spriteSheet.removeChild(sprite, true);						
			// removeChild(sprite, true);
			// CCTransitionScene transition = CCTurnOffTilesTransition.transition(1.0f, currentLevel.getScene());
			
			/*CCScaleBy sb = CCScaleBy.action(0.1f, 1.10f);
			CCScaleTo st = CCScaleTo.action(0.1f, scaleTarget);
			CCCallFunc call = CCCallFunc.action(logoLayer, "load");
			CCSequence seq = CCSequence.actions(sb, st, call);
			sprite.runAction(seq);*/
			
			/*removeChild(sprite, true);
			CCTransitionScene transition = CCFadeTransition.transition(1.0f, currentLevel.getScene());
			CCDirector.sharedDirector().replaceScene(transition);*/			
		}
	};
	
//	public class LoadThread extends Thread {		  		  
//		  public LoadThread() {		    
//		  }
//		  
//		  public void run() {
//			  	SpriteSheetFactory.add("controls", true, SpriteSheetFactory.zDefault);
//				// SpriteSheetFactory.add("logo", true, SpriteSheetFactory.zDefault);
//				SpriteSheetFactory.add("decor", true, SpriteSheetFactory.zDefault);
//				
//				SpriteSheetFactory.add("items", Level.zFront);								
//				SpriteSheetFactory.add("slime", Level.zTop);
//				SpriteSheetFactory.add("red", Level.zTop);
//				SpriteSheetFactory.add("slimydbz", Level.zFront);
//				SpriteSheetFactory.add("glasswork", Level.zMid);
//				SpriteSheetFactory.add("tank", Level.zFront);
//				SpriteSheetFactory.add("worlds-items", Level.zMid);
//				
//				Sounds.preload();
//				
//				BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorCorridor);
//				BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorRectangle);
//				BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorRectangle2);
//				
//				synchronized(lock) {
//					loaded = true;
//				}
//		  }
//		}
	
//	public class LoadThread extends Thread {		  		  
//	  public LoadThread() {		    
//	  }
//	  
//	  public void run() {
//		while (loaded != true || heartBeating == true) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		CCDirector.sharedDirector().replaceScene(currentLevel.getScene());
//	  }
//	}
}
