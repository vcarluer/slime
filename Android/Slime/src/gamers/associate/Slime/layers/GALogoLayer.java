package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.levels.LevelHome;
import gamers.associate.Slime.levels.generator.BlocInfoParser;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

public class GALogoLayer extends CCLayer {	
	private static CCScene scene;	
	// private long waitLogoSec = 2;
	// private long onEnterTime;
	private CCSprite sprite;
	private float scaleTarget;
	private Level currentLevel;
	private static GALogoLayer logoLayer;
	private static boolean loaded;
	private final Object lock = new Object();
	
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
		
		// schedule(nextCallback, waitLogoSec); doesn't work?
//		schedule(nextCallback);
//		this.onEnterTime = System.currentTimeMillis();
	}		
	
	public void endScale() {
		/*float soundTime = 1.0f;
		float waitTime = 2.0f;*/
		Sounds.playEffect(R.raw.ga);
		// CCDelayTime delay = CCDelayTime.action(waitTime);
		CCDelayTime delay2 = CCDelayTime.action(1f);
		CCScaleBy sb = CCScaleBy.action(0.1f, 1.10f);
		CCScaleTo st = CCScaleTo.action(0.1f, this.scaleTarget);
		CCCallFunc ps = CCCallFunc.action(this, "heartbeat");
		// CCCallFunc call = CCCallFunc.action(this, "load");
		CCSequence seq = CCSequence.actions(delay2, sb, st, ps);
		CCRepeatForever rep = CCRepeatForever.action(seq);
		this.sprite.runAction(rep);
		this.schedule(nextCallback);
		LoadThread th = new LoadThread();
		th.start();
	}
	
	public void heartbeat() {
		Sounds.playEffect(R.raw.heartbeat);
	}
	
	public void load() {
		removeChild(sprite, true);
		Sounds.playMusic(R.raw.menumusic, true);
		/* CCScene nextScene = SlimeLoadingLayer.scene();
		CCDirector.sharedDirector().replaceScene(nextScene);*/ 
		CCTransitionScene transition = CCFadeTransition.transition(1.0f, currentLevel.getScene());
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
			
			synchronized(lock) {
				if (loaded == true) {
					currentLevel = Level.get(LevelHome.Id);					
					unschedule(nextCallback);
					load();				
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
	
	public class LoadThread extends Thread {		  		  
		  public LoadThread() {		    
		  }
		  
		  public void run() {
			  	SpriteSheetFactory.add("controls", true, SpriteSheetFactory.zDefault);
				// SpriteSheetFactory.add("logo", true, SpriteSheetFactory.zDefault);
				SpriteSheetFactory.add("decor", true, SpriteSheetFactory.zDefault);
				
				SpriteSheetFactory.add("items", Level.zFront);								
				SpriteSheetFactory.add("slime", Level.zTop);
				SpriteSheetFactory.add("red", Level.zTop);
				SpriteSheetFactory.add("slimydbz", Level.zFront);
				SpriteSheetFactory.add("glasswork", Level.zMid);
				SpriteSheetFactory.add("tank", Level.zFront);
				SpriteSheetFactory.add("worlds-items", Level.zMid);
				
				Sounds.preload();
				
				BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorCorridor);
				BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorRectangle);
				BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorRectangle2);
				
				synchronized(lock) {
					loaded = true;
				}
		  }
		}
}
