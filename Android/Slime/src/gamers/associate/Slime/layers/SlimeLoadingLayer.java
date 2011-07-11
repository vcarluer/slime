package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.levels.LevelHome;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

public class SlimeLoadingLayer extends CCLayer {		
	private static CCScene scene;
	private Level currentLevel;		
	private CCSpriteSheet spriteSheet;
	private CCSprite sprite;
	
	public static CCScene scene() {
		if (scene == null) {
			scene = CCScene.node();		
			
			float width = CCDirector.sharedDirector().winSize().getWidth();
			float height = CCDirector.sharedDirector().winSize().getHeight();
			
			/*CCColorLayer colorLayer = CCColorLayer.node(new ccColor4B(0, 0, 0, 255), width, height);
			
			scene.addChild(colorLayer);*/
			scene.addChild(new SlimeLoadingLayer());
		}
		
		return scene;
	}
	
	protected SlimeLoadingLayer() {
		int originalW = 800;		
		CCSprite spriteBg = CCSprite.sprite("splash-level.png");
		spriteBg.setAnchorPoint(0, 0);
		spriteBg.setPosition(0, 0);
		float sW = CCDirector.sharedDirector().winSize().width;
		// Scale for full width, no deformation
		float scale = sW / originalW;
		spriteBg.setScale(scale);
		this.addChild(spriteBg, 0);
		
		//this.spriteSheet = SpriteSheetFactory.getSpriteSheet("logo", true);
		//this.addChild(this.spriteSheet);
		
		// this.sprite = CCSprite.sprite("slime-attack.png", true);
		this.sprite = CCSprite.sprite("slime-attack.png");
		this.sprite.setScale(1.0f);
		// this.spriteSheet.addChild(this.sprite);
		this.addChild(this.sprite, 1);
		this.sprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();
				
		this.schedule(nextCallback);
	}		
	
	private UpdateCallback nextCallback = new UpdateCallback() {
			
			@Override
			public void update(float d) {
				SpriteSheetFactory.add("controls", true, SpriteSheetFactory.zDefault);
				// SpriteSheetFactory.add("logo", true, SpriteSheetFactory.zDefault);
				SpriteSheetFactory.add("decor", true, SpriteSheetFactory.zDefault);
				SpriteSheetFactory.add("items", true, SpriteSheetFactory.zDefault);				
				
				SpriteSheetFactory.add("labo", Level.zMid);
				SpriteSheetFactory.add("slime", Level.zFront);
				SpriteSheetFactory.add("slimydbz", Level.zFront);
				
				Sounds.preload();
				
				currentLevel = Level.get(LevelHome.Id);					
				unschedule(nextCallback);
				
//				spriteSheet.removeChild(sprite, true);						
				// removeChild(sprite, true);
				// CCTransitionScene transition = CCTurnOffTilesTransition.transition(1.0f, currentLevel.getScene());
				CCTransitionScene transition = CCFadeTransition.transition(1.0f, currentLevel.getScene());
				CCDirector.sharedDirector().replaceScene(transition);
				Sounds.playMusic(R.raw.menumusic, true);
			}
		};

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onExit()
	 */
	@Override
	public void onExit() {		
		super.onExit();
	}
}
