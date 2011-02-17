package gamers.associate.Slime;

import java.util.ArrayList;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.GameItem"
 */
public class Level {	
	public static String LEVEL_HOME = "Home";
	
	protected static Level level; 
	
	protected World world;
	protected Vector2 gravity;
	protected float worldRatio = 32f;
	protected ArrayList<GameItem> items;
	/**
	 * @uml.property  name="slimyFactory"
	 * @uml.associationEnd  
	 */	
	protected CCSprite backgroundSprite;
	/**
	 * @uml.property  name="contactManager"
	 * @uml.associationEnd  
	 */
	protected ContactManager contactManager;
	protected SpawnPortal spawnPortal;
	protected GoalPortal goalPortal;
	
	protected CCScene scene;
	protected LevelLayer levelLayer;
	protected HudLayer hudLayer;
	protected BackgoundLayer backgroundLayer;
	protected CCLayer gameLayer;
	
	protected CCLabel label;
	
	protected float levelWidth;
	protected float levelHeight;
	protected CGPoint levelOrigin;
	
	protected CameraManager cameraManager;
	
	protected String currentLevelName;
	
	protected Level() {
		this.scene = CCScene.node();
		this.levelLayer = new LevelLayer(this);
		this.hudLayer = new HudLayer();
		this.backgroundLayer = new BackgoundLayer();
		this.gameLayer = CCLayer.node();
		
		this.gameLayer.addChild(this.backgroundLayer, 0);
		this.gameLayer.addChild(this.levelLayer, 1);
		this.levelOrigin = CGPoint.make(0, 0);
		this.gameLayer.setAnchorPoint(this.levelOrigin);
		
		this.scene.addChild(this.gameLayer, 0);
		this.scene.addChild(this.hudLayer, 1);
				
		this.items = new ArrayList<GameItem>();
		
		this.levelWidth = CCDirector.sharedDirector().winSize().getWidth() * 2;
		this.levelHeight = CCDirector.sharedDirector().winSize().getHeight() * 2;
		
		this.cameraManager = new CameraManager(this.gameLayer, this.levelWidth, this.levelHeight, this.levelOrigin);
		
		this.init();
	}
	
	public static Level get(String levelName) {
		// Level singleton  (for box2d and texture performances
		if (level == null) {
			level = new Level();
		}
		
		// In case of screen rotation
		if (!SlimeFactory.isAttached) {
			level.attachToFactory();
		}
		
		level.getCameraManager().setCameraView();
		
		// Resume existing level, either reload one (miss resuming of cocos animations!)
		if (level.getCurrentLevelName() != levelName) {
			level.loadLevel(levelName);
		}
		
		return level;
	}
	
	protected void attachToFactory() {
		SlimeFactory.attachAll(this.levelLayer, this.world, this.worldRatio);
	}
	
	public String getCurrentLevelName() {
		return this.currentLevelName;
	}
	
	// Must be call before running scene with CCDirector
	public void loadLevel(String levelName) {
		this.resetLevel();		
		this.spawnPortal = SlimeFactory.SpawnPortal.createAndMove(
				this.levelWidth / 2, 
				this.levelHeight - 32,
				this.levelWidth / 2,
				5);
		
		this.items.add(this.spawnPortal);
		
		this.label = CCLabel.makeLabel("Hud !", "DroidSans", 16);		
		this.hudLayer.addChild(this.label, 0);
		label.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() - 20));
		
		// Hard coded for now
		HardCodedLevelBuilder.build(this, levelName);
		
		this.currentLevelName = levelName;
	}
	
	private void resetLevel() {		
		for (GameItem item : this.items) {
			item.destroy();
		}
		
		this.items.clear();
		
		this.spawnPortal = null;
		this.goalPortal = null;
	}
	
	public CCScene getScene() {		
		return this.scene;
	}
	
	public CameraManager getCameraManager() {
		return this.cameraManager;
	}
	
	protected void init()
	{		
		this.gravity = new Vector2(0, -10);
		this.world = new World(this.gravity, true);
		this.contactManager = new ContactManager();
		this.world.setContactListener(this.contactManager);
		
		// Sprite too big for VM in UbuntuRox
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("decor.plist");
		CCSpriteSheet spriteSheet = CCSpriteSheet.spriteSheet("decor.png");
		this.backgroundLayer.addChild(spriteSheet);
		this.backgroundLayer.setRotation(-90f);				
		this.backgroundLayer.setScale(2.0f);
		this.backgroundSprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("decor.png"));		
		this.backgroundSprite.setAnchorPoint(0, 0);
		spriteSheet.addChild(this.backgroundSprite);
		
		SpriteSheetFactory.add("labo");							
	}
	
	protected void tick(float delta) {
		// TODO: physic step must be fix!
		synchronized (world) {
    		world.step(delta, 6, 2);
    	}
		
		for(GameItem item : this.items) {
			item.render(delta);
		}
		
		this.cameraManager.tick(delta);
	}
	
		// Test
		/*if (this.goalPortal.isWon()) {						
			if (!wonLaunched) {
				this.label.setString("You win!");
				CCScaleBy scale = CCScaleBy.action(1, 3);
				
				this.label.runAction(CCSequence.actions(scale, scale.reverse(), CCCallFuncN.action(this.levelLayer, "resetWon")));				
				wonLaunched = true;
			}
		}
	}
	
	public void resetWon() {
		this.goalPortal.setWon(false);
		this.wonLaunched = false;
		this.label.setString("Try again");
	}
	
	private Boolean wonLaunched = false;
	*/
	// Fin test
	
	public void SpawnSlime() {				
		GameItem gi = this.spawnPortal.spawn();
		this.items.add(gi);			
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public float getWorlRatio () {
		return this.worldRatio;
	}
	
	public float getLevelWidth() {
		return this.levelWidth;
	}
	
	public float getLevelHeight() {
		return this.levelHeight;
	}
	
	public void setGoalPortal(GoalPortal portal) {
		this.goalPortal = portal;
		this.items.add(this.goalPortal);
	}
	
	public void addGameItem(GameItem item) {
		this.items.add(item);
	}
	
	public SpawnPortal getSpawnPortal() {
		return this.spawnPortal;
	}
}
