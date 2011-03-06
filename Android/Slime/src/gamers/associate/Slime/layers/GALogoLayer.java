package gamers.associate.Slime.layers;

import gamers.associate.Slime.SpriteSheetFactory;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;

public class GALogoLayer extends CCLayer {
	public static boolean isInit;
	private static CCScene scene;	
	private long waitLogoSec = 2;
	private long onEnterTime;	
	private CCSpriteSheet spriteSheet;
	private CCSprite sprite;
	
	public static CCScene scene() {
		if (scene == null) {
			scene = CCScene.node();		
			scene.addChild(new GALogoLayer());
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
		
		// Do not construct again if screen is rotated
		if (!isInit) {
			this.spriteSheet = SpriteSheetFactory.getSpriteSheet("logo");
			this.addChild(this.spriteSheet);
			CCSpriteFrame spriteFrame = CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("JulenGarciaGA.png");
			this.sprite = CCSprite.sprite(spriteFrame);
			this.spriteSheet.addChild(this.sprite);		
			this.sprite.setPosition(CGPoint.make(
					CCDirector.sharedDirector().winSize().width / 2,
					CCDirector.sharedDirector().winSize().height / 2
					));
			
			// Scaling to screen height
			float originalImageHeight = 566f;
			float targetHeight = CCDirector.sharedDirector().winSize().getHeight();			
			float scale = targetHeight / originalImageHeight;
			this.sprite.setScale(scale);
			isInit = true;
		}
		
		// schedule(nextCallback, waitLogoSec); doesn't work?
		schedule(nextCallback);
		this.onEnterTime = System.currentTimeMillis();
	}		
	
	private UpdateCallback nextCallback = new UpdateCallback() {
			
			@Override
			public void update(float d) {		
				long elapsed = (System.currentTimeMillis() - onEnterTime) / 1000;
				if (elapsed > waitLogoSec) {
					unschedule(nextCallback);
					spriteSheet.removeChild(sprite, true);
					
					CCScene nextScene = SlimeLoadingLayer.scene();
					CCDirector.sharedDirector().replaceScene(nextScene);
				}
			}
		};
}
