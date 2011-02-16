package gamers.associate.Slime;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;

public class GALogoLayer extends CCLayer {
	
	private int waitLogoSec = 2;
	
	public static CCScene scene() {
		CCScene scene = CCScene.node();		
		scene.addChild(new GALogoLayer());
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
		
		CCSpriteSheet spriteSheet = SpriteSheetFactory.getSpriteSheet("logo");
		this.addChild(spriteSheet);
		CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("JulenGarciaGA.png"));
		spriteSheet.addChild(sprite);		
		sprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));
		
		schedule(nextCallback, waitLogoSec);
	}
	
	private UpdateCallback nextCallback = new UpdateCallback() {
			
			@Override
			public void update(float d) {
				unschedule(nextCallback);
				CCScene nextScene = SlimeLoadingLayer.scene();
				CCDirector.sharedDirector().replaceScene(nextScene);
			}
		};
}
