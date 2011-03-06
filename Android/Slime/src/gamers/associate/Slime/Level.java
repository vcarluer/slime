package gamers.associate.Slime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.UUID;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;
import org.cocos2d.utils.javolution.MathLib;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.GameItem"
 */
public class Level {	
	public static boolean isInit;
	public static String LEVEL_HOME = "Home";
	public static String LEVEL_1 = "1";
	public static String LEVEL_2 = "2";
	
	public static Level currentLevel; 
	
	protected World world;
	protected Vector2 gravity;
	protected float worldRatio = 32f;
	protected Hashtable<UUID, GameItem> items;
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
	protected SpawnCannon spawnCannon;
	protected GoalPortal goalPortal;
	
	protected CCScene scene;
	protected LevelLayer levelLayer;
	protected HudLayer hudLayer;
	protected BackgoundLayer backgroundLayer;
	protected CCLayer gameLayer;
	protected CCLayer customOverLayer;
	protected int customZ = 2;
	protected int hudZ = 1;
	protected boolean isHudEnabled;
	
	protected CCLabel label;
	
	protected float levelWidth;
	protected float levelHeight;
	protected CGPoint levelOrigin;
	
	protected CameraManager cameraManager;
	
	protected String currentLevelName;
	
	protected ArrayList<GameItem> itemsToRemove;
	
	protected HomeLevelHandler homeLevelHandler;
	
	protected boolean isPaused;
	
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
		this.isHudEnabled = true;
		this.scene.addChild(this.hudLayer, this.hudZ);
				
		this.items = new Hashtable<UUID, GameItem>();				
		
		this.cameraManager = new CameraManager(this.gameLayer);							
		
		this.itemsToRemove = new ArrayList<GameItem>();
		this.homeLevelHandler = new HomeLevelHandler();
		
		this.init();
		
		isInit = true;
	}
	
	public static Level get(String levelName) {
		return get(levelName, false);
	}
	
	public static Level get(String levelName, boolean forceReload) {
		// Level singleton  (for box2d and texture performances
		if (currentLevel == null || isInit == false) {
			currentLevel = new Level();
		}					
		
		// Resume existing level if exists, either reload one
		if (forceReload || currentLevel.getCurrentLevelName() != levelName) {
			currentLevel.loadLevel(levelName);
		}
		
		// Set camera right based on screen size
		currentLevel.attachLevelToCamera();	
		
		return currentLevel;
	}
	
	protected void attachToFactory() {
		SlimeFactory.attachAll(this, this.levelLayer, this.world, this.worldRatio);
	}
	
	public void reload() {
		currentLevel.loadLevel(this.currentLevelName);
		// Set camera right based on screen size
		currentLevel.getCameraManager().setCameraView();		
	}
	
	public void attachLevelToCamera() {
		this.cameraManager.attachLevel(this.levelWidth, this.levelHeight, this.levelOrigin);
		this.cameraManager.setCameraView();
		this.getCameraManager().zoomCameraTo(0f);
	}
	
	public String getCurrentLevelName() {
		return this.currentLevelName;
	}
	
	// Must be call before running scene with CCDirector
	public void loadLevel(String levelName) {
		this.resetLevel();									
		
		// Hard coded for now
		HardCodedLevelBuilder.build(this, levelName);													
		
		this.currentLevelName = levelName;
		
		if (this.currentLevelName == Level.LEVEL_HOME) {
			this.homeLevelHandler.startHomeLevel();
		}
		
		this.isPaused = false;
	}
			
	public void resetLevel() {		
		if (this.currentLevelName == Level.LEVEL_HOME) {
			this.homeLevelHandler.stopHomeLevel();
		}
		
		this.currentLevelName = "";
		
		for (GameItem item : this.items.values()) {
			item.destroy();
		}
		
		this.items.clear();
		
		this.spawnPortal = null;
		this.goalPortal = null;		
		
		this.removeCustomOverLayer();
		this.setIsTouchEnabled(true);
		this.setIsHudEnabled(true);
	}
	
	public CCScene getScene() {		
		return this.scene;
	}
	
	public CameraManager getCameraManager() {
		return this.cameraManager;
	}
	
	protected void init()
	{		
		// Box2D world
		this.gravity = new Vector2(0, -10);
		this.world = new World(this.gravity, true);
		this.contactManager = new ContactManager();
		this.world.setContactListener(this.contactManager);
		// Main game item spritesheet
		SpriteSheetFactory.add("labo");
		// Background
		CCSpriteSheet spriteSheet = SpriteSheetFactory.getSpriteSheet("decor");		
		// Sprite too big for VM in UbuntuRox		
		this.backgroundLayer.addChild(spriteSheet);
		//this.backgroundLayer.setRotation(-90f);				
		//this.backgroundLayer.setScale(2.0f);
		this.backgroundSprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("decor.png"));		
		this.backgroundSprite.setAnchorPoint(0, 0);
		spriteSheet.addChild(this.backgroundSprite);				
		
		// hud
		this.label = CCLabel.makeLabel("Hud !", "DroidSans", 16);		
		this.hudLayer.addChild(this.label);
		label.setPosition(
				CGPoint.ccp(20, 
				CCDirector.sharedDirector().winSize().getHeight() - 20));
		
		this.attachToFactory();
	}
	
	protected void tick(float delta) {
		if (!isPaused) {
			this.homeLevelHandler.tick();
			// TODO: physic step must be fix!
			synchronized (world) {
	    		world.step(delta, 6, 2);
	    	}						
						
			for(GameItem item : this.items.values()) {
				item.render(delta);
			}
			
			for(GameItem item : this.itemsToRemove) {
				this.removeGameItem(item);
			}
			
			this.cameraManager.tick(delta);
		}
	}
			
	public void addItemToRemove(GameItem item) {
		this.itemsToRemove.add(item);
	}
		
	public void setPause(boolean value) {		
		if (value) {			
			if (!this.isPaused) {
				this.levelLayer.pauseSchedulerAndActions();
				for(GameItem item : this.items.values()) {
					item.getSprite().pauseSchedulerAndActions();
				}
			}
		}
		else
		{
			if (this.isPaused) {
				this.levelLayer.resumeSchedulerAndActions();
				for(GameItem item : this.items.values()) {
					item.getSprite().resumeSchedulerAndActions();
				}
			}
		}
		
		this.isPaused = value;
		this.setIsTouchEnabled(!this.isPaused);
		this.homeLevelHandler.setPause(this.isPaused);
	}
	
	public void togglePause() {
		this.setPause(!this.isPaused);
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
	
	public void spawnSlime() {
		this.spawnSlime(CGPoint.getZero());		
	}
	
	public void spawnSlime(CGPoint screenTarget) {								
		if (this.spawnCannon != null) {			
			CGPoint gameTarget = this.cameraManager.getGamePoint(screenTarget);
			this.spawnCannon.spawnSlime(gameTarget);			
		}
		else
		{
			this.spawnPortal.spawn();
		}
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
	}
	
	public void addGameItem(GameItem item) {
		this.items.put(item.getId(), item);
	}
	
	protected void removeGameItem(GameItem item) {
		if (item != null) {
			if (this.items.containsKey(item.getId())) {
				item.destroy();
				this.items.remove(item.getId());
			}
		}
	}
	
	public SpawnPortal getSpawnPortal() {
		return this.spawnPortal;
	}
	
	public void setLevelSize(float width, float height) {
		this.levelWidth = width;
		this.levelHeight = height;
	}
	
	public void setIsTouchEnabled(boolean value) {
		this.levelLayer.setIsTouchEnabled(value);
	}
	
	public void addCustomOverLayer(CCLayer layer) {
		this.customOverLayer = layer;
		this.scene.addChild(this.customOverLayer, this.customZ);
	}
	
	public void removeCustomOverLayer() {
		if (this.customOverLayer != null) {
			this.scene.removeChild(this.customOverLayer, true);
			this.customOverLayer = null;
		}
	}
	
	public void setIsHudEnabled(boolean value) {
		if (!value) {
			if (this.isHudEnabled) {
				this.scene.removeChild(this.hudLayer,  false);
			}
		}
		else {
			if (!this.isHudEnabled) {
				this.scene.addChild(this.hudLayer, this.hudZ);
			}
		}
		
		this.isHudEnabled = value;
	}
	
	public void setSpawnCannon(SpawnCannon cannon) {
		this.spawnCannon = cannon;		
	}
	
	public SpawnCannon getSpawnCannon() {
		return this.spawnCannon;
	}
	
	public void setSpawnPortal(SpawnPortal portal) {
		this.spawnPortal = portal;
	}
	
	public void draw(GL10 gl) {
		for(GameItem item : this.items.values()) {
			item.draw(gl);
		}
	}
}