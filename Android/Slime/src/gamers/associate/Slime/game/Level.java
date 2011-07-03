package gamers.associate.Slime.game;

import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.items.custom.Thumbnail;
import gamers.associate.Slime.layers.BackgoundLayer;
import gamers.associate.Slime.layers.EndLevelLayer;
import gamers.associate.Slime.layers.HudLayer;
import gamers.associate.Slime.layers.LevelLayer;
import gamers.associate.Slime.layers.PauseLayer;

import java.util.ArrayList;
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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.GameItem"
 */
public class Level {	
	public static boolean DebugMode = true;
	public static boolean isInit;	
	public static float Gravity = -10;
	
	public static Level currentLevel; 
	
	public static int zBack = 0;
	public static int zMid = 1;
	public static int zFront = 2;
	public static int zTop = 3;
	
	private static String winTxt = "VICTORY";
	private static String gameOverTxt = "GAME OVER";
	
	protected World world;
	protected Vector2 gravity;
	protected float worldRatio = 32f;
	protected Hashtable<UUID, GameItem> items;
	protected ArrayList<ISelectable> selectables;
	
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
	
	protected CCScene scene;
	protected LevelLayer levelLayer;
	protected HudLayer hudLayer;
	protected BackgoundLayer backgroundLayer;
	protected CCLayer gameLayer;
	protected CCLayer customOverLayer;
	protected PauseLayer pauseLayer;
	protected EndLevelLayer endLevelLayer;
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
	protected ArrayList<GameItem> itemsToAdd;
	
	protected boolean isPaused;
	
	protected ISelectable selectedItem; 
	
	protected GameItem startItem;
	
	protected ThumbnailManager thumbnailManager;
	
	protected CCNodeDraw nodeDraw;
	
	protected IGamePlay gamePlay;
	
	protected Level() {
		this.scene = CCScene.node();
		this.levelLayer = new LevelLayer(this);
		this.hudLayer = new HudLayer();
		this.backgroundLayer = new BackgoundLayer();
		this.gameLayer = CCLayer.node();
		this.pauseLayer = new PauseLayer();
		this.endLevelLayer = new EndLevelLayer();
		
		this.gameLayer.addChild(this.backgroundLayer, 0);
		this.gameLayer.addChild(this.levelLayer, 1);			
		this.levelOrigin = CGPoint.make(0, 0);
		this.gameLayer.setAnchorPoint(this.levelOrigin);
		
		this.scene.addChild(this.gameLayer, 0);
		this.isHudEnabled = true;
		this.scene.addChild(this.hudLayer, this.hudZ);
		this.scene.addChild(this.pauseLayer, this.hudZ);
		this.scene.addChild(this.endLevelLayer, this.hudZ);	
				
		this.items = new Hashtable<UUID, GameItem>();				
		this.selectables = new ArrayList<ISelectable>();
		
		this.cameraManager = new CameraManager(this.gameLayer);							
		
		this.itemsToRemove = new ArrayList<GameItem>();
		this.itemsToAdd = new ArrayList<GameItem>();
		
		this.thumbnailManager = new ThumbnailManager(this);
		this.nodeDraw = new CCNodeDraw(this);
		this.gameLayer.addChild(this.nodeDraw, zTop);
		
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
		this.setStartCamera();
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
		
		this.resume();
	}
			
	public void resetLevel() {		
		this.currentLevelName = "";
		
		for (GameItem item : this.items.values()) {
			item.destroy();
		}
		
		this.items.clear();
		this.selectables.clear();
		this.itemsToAdd.clear();
		this.itemsToRemove.clear();
		this.startItem = null;
		
		this.removeCustomOverLayer();
		this.setIsTouchEnabled(true);
		this.setIsHudEnabled(true);
		this.endLevelLayer.setVisible(false);
		// this.disablePauseLayer();		
	}
	
	public void disablePauseLayer() {
		this.pauseLayer.disable();		
		if (this.hudLayer != null) {
			this.hudLayer.getMenu().setVisible(true);
		}
	}
	
	public void enablePauseLayer() {
		if (this.hudLayer != null) {
			this.hudLayer.getMenu().setVisible(false);
		}
		
		this.pauseLayer.enable();
	}
	
	public void disableEndLevelLayer() {
		this.endLevelLayer.disable();		
		if (this.hudLayer != null) {
			this.hudLayer.getMenu().setVisible(true);
		}
	}
	
	public void enableEndLevelLayer() {
		if (this.hudLayer != null) {
			this.hudLayer.getMenu().setVisible(false);
		}
		
		this.endLevelLayer.enable();
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
		this.gravity = new Vector2(0, Gravity);
		this.world = new World(this.gravity, true);
		this.contactManager = new ContactManager();
		this.world.setContactListener(this.contactManager);
		// Main game item spritesheet		
		/*SpriteSheetFactory.add("labo", Level.zMid);
		SpriteSheetFactory.add("slime", Level.zFront);*/
		
		// Background
		CCSpriteSheet spriteSheet = SpriteSheetFactory.getSpriteSheet("decor", true);		
		// Sprite too big for VM in UbuntuRox		
		this.backgroundLayer.addChild(spriteSheet);
		//this.backgroundLayer.setRotation(-90f);				
		//this.backgroundLayer.setScale(2.0f);
		this.backgroundSprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("decor.png"));		
		this.backgroundSprite.setAnchorPoint(0, 0);
		spriteSheet.addChild(this.backgroundSprite);				
		
		// hud
//		this.label = CCLabel.makeLabel("Hud !", "DroidSans", 16);		
//		this.hudLayer.addChild(this.label);
//		label.setPosition(
//				CGPoint.ccp(20, 
//				CCDirector.sharedDirector().winSize().getHeight() - 20));
		
		// Items
		this.attachToFactory();
		
		// Levels
		HardCodedLevelBuilder.init();
	}
	
	public float getTimeRatio() {
		return 2.0f;
	}
	
	public void tick(float delta) {
		if (!isPaused) {
			delta = delta * this.getTimeRatio();
			if (this.itemsToAdd.size() > 0) {
				for(GameItem item : this.itemsToAdd) {
					this.addGameItem(item);
				}
				
				this.itemsToAdd.clear();
			}
									
			// TODO: physic step must be fix!
			synchronized (world) {
	    		world.step(delta, 6, 2);
	    	}												
			
			for(GameItem item : this.items.values()) {
				item.render(delta);
			}
									
			if (this.itemsToRemove.size() > 0) {
				for(GameItem item : this.itemsToRemove) {
					this.removeGameItem(item);
				}
				
				this.itemsToRemove.clear();
			}
			
			this.cameraManager.tick(delta);
			
			this.thumbnailManager.handle(this.selectables);
		}
	}
			
	public void addItemToRemove(GameItem item) {
		this.itemsToRemove.add(item);
		if (item instanceof ISelectable)
		{
			ISelectable selectable = (ISelectable)item;			
			this.thumbnailManager.removeThumbnail(selectable);
		}
	}
	
	public void addItemToAdd(GameItem item) {
		this.itemsToAdd.add(item);
	}		
		
	public void setPause(boolean value) {		
		if (value) {			
			if (!this.isPaused) {
				this.levelLayer.pauseSchedulerAndActions();
			}
		}
		else
		{
			if (this.isPaused) {
				this.levelLayer.resumeSchedulerAndActions();
			}
		}
				
		for(GameItem item : this.items.values()) {
			item.setPause(value);
		}
		
		this.isPaused = value;
		this.setIsTouchEnabled(!this.isPaused);
	}
	
	public void togglePause() {
		this.setPause(!this.isPaused);
	}
	
	public void pause() {
		this.setPause(true);
		this.enablePauseLayer();
	}
	
	public void resume() {
		this.setPause(false);
		this.disablePauseLayer();		
		this.disableEndLevelLayer();
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
	
	private void addGameItem(GameItem item) {
		this.items.put(item.getId(), item);
		if (item instanceof ISelectable)
		{
			ISelectable selectable = (ISelectable)item;
			this.selectables.add(selectable);
		}
	}
	
	private void removeGameItem(GameItem item) {
		if (item != null) {
			if (item instanceof ISelectable)
			{
				ISelectable selectable = (ISelectable)item;
				this.selectables.remove(selectable);				
			}
			
			if (this.items.containsKey(item.getId())) {
				item.destroy();
				this.items.remove(item.getId());
			}						
		}
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
	
	public void draw(GL10 gl) {
		if (Level.DebugMode) {
			for(GameItem item : this.items.values()) {
				item.draw(gl);
			}
			
			this.cameraManager.draw(gl);
		}
	}
	
	public ISelectable getSelectedItem() {
		return this.selectedItem;
	}
	
	public GameItem getSelectedGameItem() {
		return (GameItem) this.selectedItem;
	}
	
	public void setSelectedSlimy(ISelectable toSelect) {
		this.unselectCurrent();
		toSelect.select();
		this.selectedItem = toSelect;		
	}
	
	public void unselectCurrent() {
		if(this.selectedItem != null) {
			this.selectedItem.unselect();
			this.selectedItem = null;
		}
	}
	
	public void activateSelection(CGPoint gameReference) {
		if (this.selectedItem != null) {
			// CGPoint gameTarget = this.cameraManager.getGamePoint(screenTarget);
			this.selectedItem.selectionStop(gameReference);
			
			if (this.selectedItem instanceof GameItem) {
				if (this.gamePlay != null) {
					this.gamePlay.activateSelection(gameReference);
				}
				else {
					GameItem follow = (GameItem)this.selectedItem;
					this.cameraManager.followZoom(follow);
				}				
			}
			
			this.unselectCurrent();			
		}
	}
	
	public void simpleSelect() {
		if (this.selectedItem != null) {
			// this.cameraManager.centerCameraOn(this.selectedItem.getPosition());
			if (this.selectedItem instanceof GameItem) {
				if (this.selectedItem instanceof Thumbnail) {
					((Thumbnail)this.selectedItem).selectionStop(null);
				}
				else {
					if (this.gamePlay == null) {
						GameItem center = (GameItem)this.selectedItem;
						this.cameraManager.moveInterpolateTo(center, 0.3f);
						// this.cameraManager.zoomInterpolateTo(center, 1.0f, 0.3f);
					}
					else {
						this.gamePlay.simpleSelect();
					}
				}				
			}
			
			this.unselectCurrent();
		}
	}
	
	public boolean moveSelection(CGPoint gameReference) {
		if (this.selectedItem != null) {			
			this.selectedItem.selectionMove(gameReference);
			return true;
		}
		else {
			return false;
		}						
	}

	public void trySelect(CGPoint gameReference) {
		if (this.selectedItem == null) {
			for(ISelectable selectable : this.selectables) {
				if (selectable.canSelect(gameReference)) {
					if (this.selectedItem != null) {
						if (CGPoint.ccpDistance(selectable.getPosition(), gameReference) < CGPoint.ccpDistance(this.selectedItem.getPosition(), gameReference)) {
							this.selectedItem = selectable;
						}
					}
					else {
						this.selectedItem = selectable;
					}									
				}
			}
			
			if (this.selectedItem != null) {
				this.selectedItem.select(gameReference);
				if (!(this.selectedItem instanceof Thumbnail)) {
					if (this.gamePlay == null) {
						this.cameraManager.unzoomForMargin(this.selectedItem.getPosition(), 1.0f);
					}
					else {
						this.gamePlay.selectBegin(gameReference);
					}
				}
			}
		}
	}
	
	public GameItem getStartItem() {
		return this.startItem;
	}
	
	public void setStartItem(GameItem start) {
		this.startItem = start;
	}
	
	public Vector2 getGravity() {
		return this.gravity;
	}
	
	public void addGamePlay(IGamePlay gamePlay) {
		if (gamePlay instanceof GameItem) {
			GameItem item = (GameItem) gamePlay;
			this.addGameItem(item);
		}
		
		this.gamePlay = gamePlay;
		this.gamePlay.setLevel(this);
	}	
	
	public void setHudText(String text) {
		this.hudLayer.setHudText(text);
	}
	
	public void hideHudText() {
		this.hudLayer.hideHudText();
	}
	
	public CCLabel getHudLabel() {
		return this.hudLayer.getLabel();
	}
	
	public IGamePlay getGamePlay() {
		return this.gamePlay;
	}		
	
	public void goHome() {
		CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
	}
	
	public void setStartCamera() {
		if (this.getGamePlay() != null) {
			this.getGamePlay().startLevel();
		}
		else {
			if(this.getStartItem() != null) {						
				this.getCameraManager().zoomInterpolateTo(this.getStartItem(), 1.0f, 1.0f);
				this.getCameraManager().follow(this.getStartItem());
			}
		}
	}
	
	public void win() {
		this.endLevel(winTxt, this.gamePlay.getScore());
	}
	
	public void gameOver() {
		this.endLevel(gameOverTxt, 0);
	}
	
	private void endLevel(String text, int score) {
		this.enableEndLevelLayer();
		this.endLevelLayer.setText(text);
		if (this.gamePlay != null) {
			this.endLevelLayer.setScore(score);
		}
	}
}