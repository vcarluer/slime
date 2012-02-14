package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Sounds;

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
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

public class GALogoLayer extends CCLayer {	
	private static CCScene scene;	
	// private long waitLogoSec = 2;
	// private long onEnterTime;
	private CCSprite sprite;
	private float scaleTarget;
	
	public static CCScene scene() {
		if (scene == null) {
			scene = CCScene.node();
			CCColorLayer colorLayer = CCColorLayer.node(new ccColor4B(255, 255, 255, 255), 
					CCDirector.sharedDirector().winSize().width, 
					CCDirector.sharedDirector().winSize().height);
					
			scene.addChild(colorLayer, 0);
			scene.addChild(new GALogoLayer(), 1);
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
		
		Sounds.preloadEffect(R.raw.ga);
		
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
		CCCallFunc call = CCCallFunc.action(this, "endScale");
		CCSequence seq = CCSequence.actions(scaleTo, call);
		this.sprite.runAction(seq);

		// schedule(nextCallback, waitLogoSec); doesn't work?
//		schedule(nextCallback);
//		this.onEnterTime = System.currentTimeMillis();
	}		
	
	public void endScale() {
		float soundTime = 1.0f;
		float waitTime = 2.0f;
		Sounds.playEffect(R.raw.ga);
		CCDelayTime delay = CCDelayTime.action(waitTime);
		CCScaleBy sb = CCScaleBy.action(0.1f, 1.10f);
		CCScaleTo st = CCScaleTo.action(0.1f, this.scaleTarget);		
		CCCallFunc call = CCCallFunc.action(this, "load");
		CCSequence seq = CCSequence.actions(delay, sb, st, call);
		this.sprite.runAction(seq);
	}
	
	public void load() {
		removeChild(sprite, true);
		
		CCScene nextScene = SlimeLoadingLayer.scene();
		CCDirector.sharedDirector().replaceScene(nextScene);
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
}
