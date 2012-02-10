package gamers.associate.Slime.game;

import gamers.associate.Slime.R;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.base.ITrigerable;
import gamers.associate.Slime.items.custom.Slimy;
import gamers.associate.Slime.items.custom.Thumbnail;
import gamers.associate.Slime.layers.BackgoundLayer;
import gamers.associate.Slime.layers.EndLevelLayer;
import gamers.associate.Slime.layers.HudLayer;
import gamers.associate.Slime.layers.LevelLayer;
import gamers.associate.Slime.layers.PauseLayer;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelHome;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.GameItem"
 */
public class Level {	
	public static boolean DebugMode = false;
	public static boolean isInit;	
	public static float Gravity = -10;
	private static String backgroundPath = "bkg/";
	
	public static Level currentLevel; 
	
	public static int zBack = 0;
	public static int zMid = 1;
	public static int zFront = 2;
	public static int zTop = 3;
	
	private static String winTxt = "VICTORY";
	private static String gameOverTxt = "GAME OVER";
	
	private float timeRatio = 2.0f;
	protected World world;
	protected Vector2 gravity;
	protected float worldRatio = 32f;
	protected Hashtable<UUID, GameItem> items;
	protected ArrayList<ISelectable> selectables;
	
	private static final float bgWidth = 1467f;
	private static final float bgHeight = 800f;
	
	public static String ManualDimension = "Manual";
	private String maxDimension = ManualDimension;
	
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
	
	protected LevelDefinition levelDefinition;
	
	protected boolean isActivated;
	
	protected ArrayList<Slimy> aliveSlimyList;
	
	protected boolean isGameOver;
	
	protected int lastScore;
	
	protected boolean isVictory;
	
	protected boolean endLevelShown;
	
	protected ArrayList<ITrigerable> trigerables;
	
	protected boolean isPhysicDisabled;
	
	private Random randomGen;
	
	public Level() {
		this.randomGen = new Random();
		this.scene = CCScene.node();
		this.levelLayer = new LevelLayer(this);
		this.hudLayer = new HudLayer();
		this.backgroundLayer = new BackgoundLayer();
		this.backgroundLayer.setAnchorPoint(0, 0);
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
		
		this.aliveSlimyList = new ArrayList<Slimy>();
		this.trigerables = new ArrayList<ITrigerable>();
		
		this.init();
		
		isInit = true;
	}
	
	public static Level get(String levelName) {
		return get(levelName, false);
	}
	
	public static Level get(String levelName, boolean forceReload) {
		createSingleton();					
		
		// Resume existing level if exists, either reload one
		if (forceReload || currentLevel.getCurrentLevelName() != levelName) {
			currentLevel.loadLevel(levelName);
		}				
		
		return currentLevel;
	}
	
	private static void createSingleton() {
		// Level singleton  (for box2d and texture performances
		if (currentLevel == null || isInit == false) {
			currentLevel = new Level();
		}
	}
	
	public static Level get(LevelDefinition levelDef) {
		return get(levelDef, false);
	}
	
	public static Level get(LevelDefinition levelDef, boolean forceReload) {
		createSingleton();
		
		// Resume existing level if exists, either reload one
		if (forceReload || currentLevel.getCurrentLevelName() != levelDef.getId()) {
			currentLevel.loadLevel(levelDef);
		}
		
		return currentLevel;
	}
	
	protected void attachToFactory() {		
		SlimeFactory.attachAll(this, this.levelLayer, this.world, this.worldRatio);
	}
	
	public void reload() {
		// currentLevel.loadLevel(this.currentLevelName);
		
		// currentLevel.loadLevel(this.levelDefinition);
		
		// Set camera right based on screen size
		// currentLevel.getCameraManager().setCameraView();
		//this.setStartCamera();
		
		this.preBuild();
		SlimeFactory.GameInfo.removeLastScore();
		SlimeFactory.LevelBuilder.rebuild(this, this.levelDefinition);
		this.postBuild(this.currentLevelName);
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
		this.preBuild();		
		SlimeFactory.LevelBuilder.build(this, levelName);						
		this.postBuild(levelName);				
	}
	
	// Must be call before running scene with CCDirector
	public void loadLevel(LevelDefinition levelDef) {
		this.preBuild();
		SlimeFactory.LevelBuilder.build(this, levelDef);						
		this.postBuild(levelDef.getId());				
	}
	
	private void preBuild() {
		this.resetLevel();			
	}
	
	private void setBackground() {
		if (this.backgroundSprite != null) {
			this.backgroundLayer.removeChild(this.backgroundSprite, true);
		}				
				
		int choice = this.randomGen.nextInt(4);
		String fileName = "";
		switch (choice) {
		default:
		case 0:
			fileName = "background-level-01.png";
			break;
		case 1:
			fileName = "background-world00-01.png";
			break;
		case 2:
			fileName = "world00-01.png";
			break;
		case 3:
			fileName = "splash-level-01.png";
			break;
		}
		
		this.backgroundSprite = CCSprite.sprite(backgroundPath + fileName);
		this.backgroundSprite.setAnchorPoint(0, 0);
		float wRatio = this.levelWidth / this.backgroundSprite.getTextureRect().size.width;
		float hRatio = this.levelHeight / this.backgroundSprite.getTextureRect().size.height;
		float ratio = Math.max(wRatio, hRatio);
		if (ratio > 1) {
			this.backgroundSprite.setScale(ratio);
		}
		this.backgroundLayer.addChild(this.backgroundSprite);
	}
	
	private void postBuild(String levelName) {
		this.setBackground();
		this.currentLevelName = levelName;
		// Set camera right based on screen size
		this.attachLevelToCamera();

		this.startLevel();
	}	
			
	public void resetLevel() {				
		this.isPhysicDisabled = false;
		this.currentLevelName = "";
		
		for (GameItem item : this.items.values()) {
			item.destroy();
		}
		
		this.gamePlay = null;
		
		this.trigerables.clear();
		this.aliveSlimyList.clear();
		this.isGameOver = false;
		this.lastScore = 0;		
		this.isVictory = false;
		this.endLevelShown = false;
		
		this.items.clear();
		this.selectables.clear();
		this.itemsToAdd.clear();
		this.itemsToRemove.clear();
		this.startItem = null;
		
		this.removeCustomOverLayer();
		this.setIsTouchEnabled(true);
		this.setIsHudEnabled(true);
		this.endLevelLayer.setVisible(false);
		this.levelLayer.reset();		
		// this.disablePauseLayer();
		
		this.setLevelOrigin(CGPoint.zero());
		
		this.resume();
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
		
		this.pauseLayer.setMaxScore(this.levelDefinition.getMaxScore());
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
		// CCSpriteSheet spriteSheet = SpriteSheetFactory.getSpriteSheet("decor", true);		
		// Sprite too big for VM in UbuntuRox		
		// this.backgroundLayer.addChild(spriteSheet);
		//this.backgroundLayer.setRotation(-90f);				
		//this.backgroundLayer.setScale(2.0f);
		// this.backgroundSprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("decor.png"));		
		
		/*this.backgroundSprite = CCSprite.sprite("background-level-01.png");
		this.backgroundSprite.setAnchorPoint(0, 0);
		this.backgroundLayer.addChild(this.backgroundSprite);*/
		
		// spriteSheet.addChild(this.backgroundSprite);				
		
		// hud
//		this.label = CCLabel.makeLabel("Hud !", "DroidSans", 16);		
//		this.hudLayer.addChild(this.label);
//		label.setPosition(
//				CGPoint.ccp(20, 
//				CCDirector.sharedDirector().winSize().getHeight() - 20));
		
		// Items
		this.attachToFactory();
		
		// Levels
		SlimeFactory.LevelBuilder.init();
	}
	
	public float getTimeRatio() {
		return this.timeRatio;
	}
	
	public void setTimeRatio(float timeRatio) {
		this.timeRatio = timeRatio;
	}	
	
	public void tick(float delta) {
		if (this.isGameOver && !this.isVictory) {
			this.reload();
		}

		delta = delta * this.getTimeRatio();
		if (this.itemsToAdd.size() > 0) {
			for(GameItem item : this.itemsToAdd) {
				this.addGameItem(item);
			}
			
			this.itemsToAdd.clear();
		}
		
		if (!isPaused) {
			
									
			if (!this.isPhysicDisabled) {
				// TODO: physic step must be fix!
				synchronized (this.world) {
					this.world.step(delta, 8, 4); // 6 2
		    	}
			}
			
			for(GameItem item : this.items.values()) {
				item.render(delta);
			}
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
	
	public ArrayList<GameItem> getItemsToAdd() {
		return this.itemsToAdd;
	}
		
	public void setPause(boolean value) {		
//		if (value) {			
//			if (!this.isPaused) {
//				this.levelLayer.pauseSchedulerAndActions();				
//			}
//		}
//		else
//		{
//			if (this.isPaused) {
//				this.levelLayer.resumeSchedulerAndActions();
//			}
//		}
				
		for(GameItem item : this.items.values()) {
			item.setPause(value);
		}
		
		this.isPaused = value;
		
		if (this.gamePlay != null) {
			this.gamePlay.setPause(this.isPaused);
		}
//		this.setIsTouchEnabled(!this.isPaused);
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
		
		if (item instanceof ITrigerable)
		{
			ITrigerable trigerable = (ITrigerable)item;
			this.trigerables.add(trigerable);
		}
		
		if (item instanceof Slimy) {
			Slimy slimy = (Slimy) item;
			if (!slimy.isDead()) {
				this.aliveSlimyList.add(slimy);
				if (this.gamePlay != null) {
					this.gamePlay.setNewAliveSlimyCount(this.aliveSlimyList.size());
				}
			}
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
			
			if (item instanceof Slimy) {
				Slimy slimy = (Slimy) item;
				if (this.aliveSlimyList.contains(slimy)) {
					this.aliveSlimyList.remove(slimy);
					if (this.gamePlay != null) {
						this.gamePlay.setNewAliveSlimyCount(this.aliveSlimyList.size());
					}
				}
			}
		}
	}
	
	public void setLevelSize(float width, float height) {
		this.levelWidth = width;
		this.levelHeight = height;
		
		if (width > bgWidth) {
			this.backgroundLayer.setScale(width / bgWidth);
		} else {
			this.backgroundLayer.setScale(1f);
		}
		
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
		for(GameItem item : this.items.values()) {
			item.draw(gl);
		}
			
		this.cameraManager.draw(gl);
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
	
	public void simpleSelect(CGPoint gameReference) {
		if (this.selectedItem != null) {
			if (this.isPaused) {
				// this.cameraManager.centerCameraOn(this.selectedItem.getPosition());
				if (this.selectedItem instanceof GameItem) {				
					// Return true if auto or gameplay simpleSelect camera needed
					if (this.selectedItem.simpleSelect()) {
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
			} else {
				this.activateSelection(gameReference);
			}			
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
					
					if (this.selectedItem != null) {
						break;
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
		if (this.gamePlay != null) {
			this.gamePlay.setLevel(this);
		}		
	}	
	
	public void setHudText(String text) {
		this.hudLayer.setHudText(text);
	}
	
	public void setStartText(String text) {
		this.hudLayer.setHudStartText(text);
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
		// CCDirector.sharedDirector().replaceScene(LevelSelection.get().getScene());
		// this.loadLevel(LevelBuilder.LevelSelection);
		this.loadLevel(LevelHome.Id);
		// Sounds.resumeMusic();
	}
	
	public void goNext() {
		// String next = SlimeFactory.LevelBuilder.getNext(this.currentLevelName);		
//		if (next != null) {
//			this.loadLevel(next);
//		}
		
		// this.loadLevel(LevelBuilder.LevelSelection);
		get(SlimeFactory.LevelBuilder.getNext(this.currentLevelName), true);
	}
	
	public void startLevel() {
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
	
	public boolean win() {		
		return this.win(true);				
	}
	
	public boolean lose() {
		return this.lose(true);
	}
	
	public boolean win(boolean showEndLevel) {				
		if (this.gamePlay != null && !this.isGameOver) {						
			this.lastScore = this.gamePlay.getScore();
			this.isVictory = true;
			this.endLevel();
			if (this.levelDefinition != null) {
				this.levelDefinition.setLastScore(this.lastScore);				
			}
			
			SlimeFactory.GameInfo.addLevelScore(this.lastScore);

			if (showEndLevel) {
				this.showEndLevel();
			}						
						
			return true;
		}
		
		return false;
	}
	
	public boolean lose(boolean showEndLevel) {
		if (this.gamePlay != null && !this.isGameOver) {
			this.lastScore = 0;
			this.isVictory = false;
			this.endLevel();
			
			ArrayList<Slimy> toLose = new ArrayList<Slimy>(this.aliveSlimyList);
			for(Slimy slimy : toLose) {
				slimy.lose();
			}
			
//			if (showEndLevel) {
//				this.showEndLevel();
//			}						
			
			return true;
		}
		
		return false;
	}
	
	private void endLevel() {
		this.stopGamePlay();		
		this.isGameOver = true;
	}
	
	public void stopGamePlay() {
		if (this.gamePlay != null) {
			this.gamePlay.stop();
		}
	}
	
	public void showEndLevel() {
		if (!this.endLevelShown) {
			if (this.isVictory) {
				Sounds.playEffect(R.raw.victory);
				this.showEndLevel(winTxt);
			}
			else {
				Sounds.playEffect(R.raw.lose);
				this.showEndLevel(gameOverTxt);
			}
		}
	}
			
	private void showEndLevel(String text) {
		if (!this.endLevelShown) {
			this.enableEndLevelLayer();
			int score = 0;		
			// No more care of text...
			if (this.gamePlay != null) {			
				score = this.lastScore;			
			}
			
			if (this.isVictory) {
				this.endLevelLayer.setVictory(score);
			} else {
				this.endLevelLayer.setLose();
			}
			
			if (this.levelDefinition != null) {				
				this.endLevelLayer.setNextEnabled(this.levelDefinition.getMaxScore() > 0);
				this.endLevelLayer.setHomeEnabled(true);
			}
			
			this.setIsHudEnabled(false);
			this.setIsTouchEnabled(false);
			
			this.endLevelShown = true;
		}
	}
	
	public void setLevelDefinition(LevelDefinition definition) {
		this.levelDefinition = definition;
	}
	
	public Boolean hasNext() {
		// return SlimeFactory.LevelBuilder.getNext(this.currentLevelName) != null;
		return true;
	}
	
	public void activate()
	{
		this.isActivated = true;
	}
	
	public void desactivate()
	{
		this.isActivated = false;		
	}
	
	public boolean getActivated() {
		return this.isActivated;
	}
	
	public void slimyKilled(Slimy slimy) {
		this.aliveSlimyList.remove(slimy);
		if (this.gamePlay != null) {
			this.gamePlay.setNewAliveSlimyCount(this.aliveSlimyList.size());
		}
	}
	
	public void activateCameraMoveAndZoomByUser() {
		this.levelLayer.setMoveCameraActivated(true);
		this.levelLayer.setZoomCameraActivated(true);
	}
	
	public void desactivateCameraMoveAndZoomByUser() {
		this.levelLayer.setMoveCameraActivated(false);
		this.levelLayer.setZoomCameraActivated(false);
	}
	
	public ArrayList<ITrigerable> getTrigerables(String name) {
		ArrayList<ITrigerable> list = new ArrayList<ITrigerable>();
		for (ITrigerable trigerable : this.trigerables) {
			if (trigerable.getName().equals(name)) {
				list.add(trigerable);
			}
		}
		
		return list;
	}

	/**
	 * @return the isPhysicDisabled
	 */
	public boolean isPhysicDisabled() {
		return isPhysicDisabled;
	}

	/**
	 * @param isPhysicDisabled the isPhysicDisabled to set
	 */
	public void setPhysicDisabled(boolean isPhysicDisabled) {
		this.isPhysicDisabled = isPhysicDisabled;
	}

	/**
	 * @return the isPaused
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * @param isPaused the isPaused to set
	 */
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public void shiftAll(int xShift, int yShift) {
		/*for(GameItem item : this.items.values()) {
			// item.shift(xShift, yShift)
		}*/
	}
	
	public void setLevelOrigin(CGPoint origin) {
		this.levelOrigin = origin;
		// this.gameLayer.setAnchorPoint(this.levelOrigin);
		// this.gameLayer.setPosition(origin.x, origin.y);
		this.backgroundLayer.setPosition(origin.x, origin.y);
	}

	public String getMaxDimension() {
		return maxDimension;
	}

	public void setMaxDimension(String maxDimension) {
		this.maxDimension = maxDimension;
	}
	
	public CGPoint getLevelOrigin() {
		return this.levelOrigin;
	}
	
	public void setLevelOrigin(float x, float y) {
		this.levelOrigin.x = x;
		this.levelOrigin.y = y;
		
		this.backgroundLayer.setPosition(this.getLevelOrigin());
	}
}