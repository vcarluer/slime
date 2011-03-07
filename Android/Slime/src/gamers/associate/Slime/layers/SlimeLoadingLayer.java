package gamers.associate.Slime.layers;

import gamers.associate.Slime.Level;
import gamers.associate.Slime.SpriteSheetFactory;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

public class SlimeLoadingLayer extends CCLayer {	
	public static boolean isInit;
	private static CCScene scene;
	private Level currentLevel;	
	private Object syncObj;
	private CCSpriteSheet spriteSheet;
	private CCSprite sprite;
	
	public static CCScene scene() {
		if (scene == null) {
			scene = CCScene.node();		
			
			float width = CCDirector.sharedDirector().winSize().getWidth();
			float height = CCDirector.sharedDirector().winSize().getHeight();
			
			CCColorLayer colorLayer = CCColorLayer.node(new ccColor4B(0, 0, 0, 255), width, height);
			
			scene.addChild(colorLayer);
			scene.addChild(new SlimeLoadingLayer());
		}
		
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
		
		if (isInit == false) {
			this.spriteSheet = SpriteSheetFactory.getSpriteSheet("logo", true);
			this.addChild(this.spriteSheet);
			
			this.sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("SlimeTitle.png"));
			this.spriteSheet.addChild(sprite);		
			this.sprite.setPosition(CGPoint.make(
					CCDirector.sharedDirector().winSize().width / 2,
					CCDirector.sharedDirector().winSize().height / 2
					));
						
			InitThread initThread = new InitThread();
			initThread.start();
		}
		else {
			// Initialize camera view
			this.currentLevel.getCameraManager().setCameraView();
		}					
		
		schedule(nextCallback);
	}		
	
	private UpdateCallback nextCallback = new UpdateCallback() {
			
			@Override
			public void update(float d) {				
				synchronized (syncObj) {					
					if (isInit) {
						unschedule(nextCallback);
						
						spriteSheet.removeChild(sprite, true);						
						// CCTransitionScene transition = CCTurnOffTilesTransition.transition(1.0f, currentLevel.getScene());
						// CCTransitionScene transition = CCFadeTransition.transition(1.0f, currentLevel.getScene());
						CCDirector.sharedDirector().replaceScene(currentLevel.getScene());
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
			// First call to get is long: init physic world and resources			
			
			currentLevel = Level.get(Level.LEVEL_HOME);
			synchronized (syncObj) {
				isInit = true;
			}			
		}
		
	}
}
