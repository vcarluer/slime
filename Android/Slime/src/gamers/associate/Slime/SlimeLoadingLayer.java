package gamers.associate.Slime;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;

public class SlimeLoadingLayer extends CCLayer {
	
	private Level levelHome;
	private boolean isInit;
	private Object syncObj;
	
	public static CCScene scene() {
		CCScene scene = CCScene.node();		
		scene.addChild(new SlimeLoadingLayer());
		return scene;
	}
	
	protected SlimeLoadingLayer() {
		this.syncObj = new Object();
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();
		
		CCSpriteSheet spriteSheet = SpriteSheetFactory.getSpriteSheet("logo");
		this.addChild(spriteSheet);
		CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("TitleLoading.png"));
		spriteSheet.addChild(sprite);		
		sprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));
		
		InitThread initThread = new InitThread();
		initThread.start();
		schedule(nextCallback);
	}		
	
	private UpdateCallback nextCallback = new UpdateCallback() {
			
			@Override
			public void update(float d) {				
				synchronized (syncObj)
				{					
					if (isInit) {
						unschedule(nextCallback);						
						CCDirector.sharedDirector().replaceScene(levelHome.getScene());
					}					
				}
			}
		};
	
	private class InitThread extends Thread {

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			levelHome = LevelFactory.GetLevel("Home");
			isInit = true;
		}
		
	}
}
