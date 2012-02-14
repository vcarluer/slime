package gamers.associate.Slime.layers;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

public class GALogoLayer extends CCLayer {	
	private static CCScene scene;	
	private long waitLogoSec = 2;
	private long onEnterTime;
	private CCSprite sprite;
	
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
		
		this.sprite = CCSprite.sprite("gamers associate.png");
		this.addChild(this.sprite);
		this.sprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));				
		
		// Scaling to screen height
		float originalImageHeight = 640f;
		float targetHeight = CCDirector.sharedDirector().winSize().getHeight();			
		float scale = targetHeight / originalImageHeight;
		this.sprite.setScale(10.0f);
		CCScaleTo scaleTo = CCScaleTo.action(0.3f, scale);
		this.sprite.runAction(scaleTo);

		// schedule(nextCallback, waitLogoSec); doesn't work?
		schedule(nextCallback);
		this.onEnterTime = System.currentTimeMillis();
	}		
	
	private UpdateCallback nextCallback = new UpdateCallback() {
			
			public void update(float d) {		
				long elapsed = (System.currentTimeMillis() - onEnterTime) / 1000;
				if (elapsed > waitLogoSec) {
					unschedule(nextCallback);
					// spriteSheet.removeChild(sprite, true);
					removeChild(sprite, true);
					
					CCScene nextScene = SlimeLoadingLayer.scene();
					CCDirector.sharedDirector().replaceScene(nextScene);
				}
			}
		};
}
