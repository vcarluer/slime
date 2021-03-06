package gamers.associate.SlimeAttack.layers;

import android.annotation.SuppressLint;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.base.SpriteSheetFactory;
import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.LevelHome;
import gamers.associate.SlimeAttack.levels.generator.BlocInfoParser;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;

@SuppressLint("DefaultLocale") public class SlimeLoadingLayer extends CCLayer {		
	private static CCScene scene;
	private Level currentLevel;
	private CCSprite sprite;
	
	public static CCScene scene() {
		if (scene == null) {
			scene = CCScene.node();		
			
			// float width = CCDirector.sharedDirector().winSize().getWidth();
			// float height = CCDirector.sharedDirector().winSize().getHeight();
			
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
		float padding = 100f;
		float saW = 333 + padding;
		float w = CCDirector.sharedDirector().winSize().getWidth();
		scale = w / saW;
		if (scale > 1.5) {
			scale = 1.5f;
		}
		this.sprite.setScale(scale);
		// this.spriteSheet.addChild(this.sprite);
		this.addChild(this.sprite, 1);
		this.sprite.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				CCDirector.sharedDirector().winSize().height / 2
				));
		
		CCLabel lbl = CCLabel.makeLabel("Loading...".toUpperCase(), "fonts/Slime.ttf", 30f);
		this.addChild(lbl);
		lbl.setPosition(CGPoint.make(
				CCDirector.sharedDirector().winSize().width / 2,
				30f
				));
		
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {
		super.onEnter();
				
		this.schedule(nextCallback);
	}		
	
	private UpdateCallback nextCallback = new UpdateCallback() {
			
			public void update(float d) {
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
				
				BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorCorridor3);
				BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorRectangle2);
				BlocInfoParser.buildAll(SlimeFactory.LevelGeneratorTutorial);
				
				currentLevel = Level.get(LevelHome.Id, GamePlay.None);					
				unschedule(nextCallback);
				
//				spriteSheet.removeChild(sprite, true);						
				// removeChild(sprite, true);
				// CCTransitionScene transition = CCTurnOffTilesTransition.transition(1.0f, currentLevel.getScene());
				CCTransitionScene transition = CCFadeTransition.transition(1.0f, currentLevel.getScene());
				CCDirector.sharedDirector().replaceScene(transition);				
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
