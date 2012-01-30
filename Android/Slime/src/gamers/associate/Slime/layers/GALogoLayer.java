package gamers.associate.Slime.layers;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class GALogoLayer extends CCLayer {	
	private static CCScene scene;	
	private long waitLogoSec = 2;
	private long onEnterTime;
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
		this.sprite.setScale(scale);

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
