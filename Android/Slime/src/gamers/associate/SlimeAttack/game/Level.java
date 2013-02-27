package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.achievements.AchievementStatistics;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.base.GameItemPhysic;
import gamers.associate.SlimeAttack.items.base.IElectrificable;
import gamers.associate.SlimeAttack.items.base.ISelectable;
import gamers.associate.SlimeAttack.items.base.ITrigerable;
import gamers.associate.SlimeAttack.items.custom.EvacuationPlug;
import gamers.associate.SlimeAttack.items.custom.GoalPortal;
import gamers.associate.SlimeAttack.items.custom.Slimy;
import gamers.associate.SlimeAttack.items.custom.Thumbnail;
import gamers.associate.SlimeAttack.layers.BackgoundLayer;
import gamers.associate.SlimeAttack.layers.EndLevelLayer;
import gamers.associate.SlimeAttack.layers.HudLayer;
import gamers.associate.SlimeAttack.layers.LevelLayer;
import gamers.associate.SlimeAttack.layers.MessageLayer;
import gamers.associate.SlimeAttack.layers.PauseLayer;
import gamers.associate.SlimeAttack.layers.SurvivalGameOverLayer;
import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.LevelDefinition;
import gamers.associate.SlimeAttack.levels.LevelHome;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;

import android.annotation.SuppressLint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.GameItem"
 */
@SuppressLint("DefaultLocale") 
public class Level implements IGameItemHandler {
	public static boolean DebugMode = SlimeFactory.IsLevelDebugMode;
	public static boolean isInit;	
	public static float Gravity = -10;
	private static String backgroundPath = "bkg/";
	private static final String HomeBkg = "world00-01.png";
	private static final boolean forceBgScale = true;
	
	public static Level currentLevel; 
	
	public static int zUnder = -1;
	public static int zBack = 0;
	public static int zMid = 1;
	public static int zFront = 2;
	public static int zTop = 3;
	
	private static String winTxt = "VICTORY";	
	
	private float timeRatio = 2.0f;
	protected World world;
	protected Vector2 gravity;
	protected float worldRatio = 32f;
	protected Hashtable<UUID, GameItem> items;
	protected ArrayList<ISelectable> selectables;	
	
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
	protected MessageLayer messageLayer;
	
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
	protected ArrayList<Slimy> slimyList;
	protected ArrayList<IElectrificable> electrificables;
	
	protected GoalPortal goal;
	protected EvacuationPlug plug;
	
	protected boolean isGameOver;
	
	protected int lastScore;
	
	protected boolean isVictory;
	
	protected boolean endLevelShown;
	
	protected ArrayList<ITrigerable> trigerables;
	
	protected ArrayList<ITrigerable> trigerablesToAdd;
	
	protected boolean isPhysicDisabled;
	
	private Random randomGen;
	
	private GameItem helpItem;
	
	private List<GameItem> tempList;
	
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
		this.messageLayer = MessageLayer.get();
		
		this.gameLayer.addChild(this.backgroundLayer, 0);
		this.gameLayer.addChild(this.levelLayer, 1);			
		this.levelOrigin = CGPoint.make(0, 0);
		this.gameLayer.setAnchorPoint(0, 0);		
		this.scene.addChild(this.gameLayer, 0);
		this.isHudEnabled = true;
		this.scene.addChild(this.hudLayer, this.hudZ);
		this.scene.addChild(this.pauseLayer, this.hudZ);
		this.scene.addChild(this.endLevelLayer, this.hudZ);	
		this.scene.addChild(this.messageLayer, zTop);
				
		this.items = new Hashtable<UUID, GameItem>();				
		this.selectables = new ArrayList<ISelectable>();
		
		this.cameraManager = new CameraManager(this.gameLayer);			
		
		this.itemsToRemove = new ArrayList<GameItem>();
		this.itemsToAdd = new ArrayList<GameItem>();
		
		this.thumbnailManager = new ThumbnailManager(this);
		this.nodeDraw = new CCNodeDraw(this);
		this.gameLayer.addChild(this.nodeDraw, zTop);
		
		this.aliveSlimyList = new ArrayList<Slimy>();
		this.slimyList = new ArrayList<Slimy>();
		this.trigerables = new ArrayList<ITrigerable>();
		this.trigerablesToAdd = new ArrayList<ITrigerable>();
		this.electrificables = new ArrayList<IElectrificable>();
		
		this.tempList = new ArrayList<GameItem>();

		this.init();
		
		isInit = true;				
	}
	
	public static Level get(String levelName, GamePlay gamePlay) {
		return get(levelName, false, gamePlay);
	}
	
	public static Level get(String levelName, boolean forceReload, GamePlay gamePlay) {
		createSingleton();					
		
		// Resume existing level if exists, either reload one
		if (forceReload || currentLevel.getCurrentLevelName() != levelName) {
			currentLevel.loadLevel(levelName, gamePlay);
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
	
	public void attachToFactory() {		
		SlimeFactory.attachAll(this, this.levelLayer, this.world, this.worldRatio);
		this.hudLayer.attachToFactory();
	}
	
	public void reload() {		
		SlimeFactory.Log.d(SlimeAttack.TAG, "reload() start");
		if (this.getGamePlay().getType() == GamePlay.Survival) {
			SlimeFactory.GameInfo.removeLastScore();
		}
		
		this.preBuild();
		
		SlimeFactory.LevelBuilder.rebuild(this, this.levelDefinition);
		this.postBuild(this.currentLevelName);
		
		SlimeFactory.Log.d(SlimeAttack.TAG, "reload() end");
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
	public void loadLevel(String levelName, GamePlay gamePlay) {
		SlimeFactory.Log.d(SlimeAttack.TAG, "loadLevel(String levelName, GamePlay gamePlay) start");
		this.preBuild();		
		SlimeFactory.LevelBuilder.build(this, levelName, gamePlay);						
		this.postBuild(levelName);		
		SlimeFactory.Log.d(SlimeAttack.TAG, "loadLevel(String levelName, GamePlay gamePlay) end");
	}
	
	// Must be call before running scene with CCDirector
	public void loadLevel(LevelDefinition levelDef) {
		SlimeFactory.Log.d(SlimeAttack.TAG, "loadLevel(LevelDefinition levelDef) start");
		this.preBuild();
		SlimeFactory.LevelBuilder.build(this, levelDef);						
		this.postBuild(levelDef.getId());				
		SlimeFactory.Log.d(SlimeAttack.TAG, "loadLevel(LevelDefinition levelDef) end");
	}
	
	private void preBuild() {
		this.resetLevel();			
	}
	
	private void setBackground() {						
		String fileName = "";
		if (SlimeFactory.GameInfo.getLastBkg() != null && SlimeFactory.GameInfo.getLastBkg().length() > 0) {
			fileName = SlimeFactory.GameInfo.getLastBkg();
		} else {
			// todo list assets in bkg folder instead
			int choice = this.randomGen.nextInt(7);
			
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
			case 4:
				fileName = "background-world01-01.png";
				break;
			case 5:
				fileName = "background-level-02-01.png";
				break;
			case 6:
				fileName = "background-level-03-01.png";
				break;
			}
			
			SlimeFactory.GameInfo.setLastBkgandSave(fileName);
		}		
		
		this.setBackgroundFrom(fileName);
	}
	
	public void setBackgroundFrom(String fileName) {
		if (this.backgroundSprite != null) {
			this.backgroundLayer.removeChild(this.backgroundSprite, true);
		}	

		this.backgroundSprite = CCSprite.sprite(backgroundPath + fileName);
		this.backgroundSprite.setAnchorPoint(0, 0);
		this.fixBgSize();
		this.backgroundLayer.addChild(this.backgroundSprite);
	}

	private void postBuild(String levelName) {
		SlimeFactory.Log.d(SlimeAttack.TAG, "postBuild(String levelName) start");
		if (levelName == LevelHome.Id) {
			this.setBackgroundFrom(HomeBkg);
		} else {
			this.setBackground();
		}
		
		this.currentLevelName = levelName;
		// Set camera right based on screen size
		this.attachLevelToCamera();
		
		this.hudLayer.upudateStarsCount();
		this.startLevel();
		
		SlimeFactory.Log.d(SlimeAttack.TAG, "postBuild(String levelName) end");
	}
			
	public void resetLevel() {		
		SlimeFactory.Log.d(SlimeAttack.TAG, "resetLevel() start");
		this.isPhysicDisabled = false;
		this.currentLevelName = "";		
		int i = 1;
		for (GameItem item : this.items.values()) {			
			SlimeFactory.Log.d(SlimeAttack.TAG, String.valueOf(i) + " - " + item.getName() + " - " + item.getClass().toString());
			item.destroy();
			i++;
		}
		
		for (GameItem item : this.itemsToAdd) {
			item.destroy();
		}

		for (GameItem item : this.itemsToRemove) {
			item.destroy();
		}
		
		this.gamePlay = null;

		this.goal = null;

		this.plug = null;
		
		this.electrificables.clear();
		this.trigerables.clear();
		this.trigerablesToAdd.clear();
		this.aliveSlimyList.clear();
		this.slimyList.clear();
		this.isGameOver = false;
		this.lastScore = 0;
		this.isVictory = false;
		this.endLevelShown = false;
		
		this.items.clear();
		this.selectables.clear();
		this.itemsToAdd.clear();
		this.itemsToRemove.clear();
		this.startItem = null;
		this.thumbnailManager.reset();

		this.removeCustomOverLayer();
		this.setIsTouchEnabled(true);
		this.setIsHudEnabled(true);
		this.endLevelLayer.setVisible(false);
		this.levelLayer.reset();
		
		this.setLevelOrigin(CGPoint.zero());
		this.cameraManager.cancelAll();
		this.hudLayer.resetLevel();
		this.resume();
		SlimeFactory.Log.d(SlimeAttack.TAG, "resetLevel() end");
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
	
	private Object syncObject = new Object();
	private float delta = 0f;
	public void tick(float deltaBase) {
		if (this.isGameOver && !this.isVictory && this.levelDefinition.getGamePlay() == GamePlay.TimeAttack) {
			this.reload();
		}

		delta = deltaBase * this.getTimeRatio();
		synchronized (this.syncObject) {
			if (this.itemsToAdd.size() > 0) {
				for(GameItem item : this.itemsToAdd) {
					this.addGameItem(item);
				}
				
				this.itemsToAdd.clear();
			}
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
		
		synchronized (this.syncObject) {
			if (this.itemsToRemove.size() > 0) {
				for(GameItem item : this.itemsToRemove) {
					this.removeGameItem(item);
				}
				
				this.itemsToRemove.clear();
			}
		}
		
		// Handle camera with real time
		this.cameraManager.tick(deltaBase);
		this.thumbnailManager.handle(this.selectables);
		if (this.hudLayer != null) {
			this.hudLayer.render(deltaBase);
		}
		
		// Handle end level animations
		if (this.endLevelShown) {
			this.endLevelLayer.tick(deltaBase);
		}
	}
			
	public void addItemToRemove(GameItem item) {
		synchronized (this.syncObject) {					
			if (item != null) {
				if (this.itemsToAdd.contains(item)) {
					if (item instanceof ITrigerable) {
						ITrigerable triger = (ITrigerable)item;
						this.trigerablesToAdd.remove(triger);
					}						
	
					item.destroy();
					this.itemsToAdd.remove(item);
				} else {
					this.itemsToRemove.add(item);
					if (item instanceof ISelectable)
					{
						ISelectable selectable = (ISelectable)item;			
						this.thumbnailManager.removeThumbnail(selectable);
					}
				}						
			}
		}
	}
	
	public void addItemToAdd(GameItem item) {
		synchronized (this.syncObject) {					
			if (item instanceof ITrigerable) {
				ITrigerable triger = (ITrigerable)item;
				this.trigerablesToAdd.add(triger);
			}
			
			this.itemsToAdd.add(item);
		}
	}		
	
	public ArrayList<GameItem> getItemsToAdd() {
		return this.itemsToAdd;
	}
		
	public void setPause(boolean value) {	
		for(GameItem item : this.itemsToAdd) {
			item.setPause(value);
		}
		
		for(GameItem item : this.items.values()) {
			item.setPause(value);
		}
		
		this.isPaused = value;
		
		if (this.gamePlay != null) {
			this.gamePlay.setPause(this.isPaused);
		}
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
			this.trigerablesToAdd.remove(trigerable);
		}
		
		if (item instanceof IElectrificable) {
			IElectrificable elec = (IElectrificable)item;
			this.electrificables.add(elec);
		}
		
		if (item instanceof Slimy) {
			Slimy slimy = (Slimy) item;
			if (!slimy.isDead()) {
				this.slimyList.add(slimy);
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
			
			if (item instanceof ITrigerable) {
				ITrigerable triger = (ITrigerable)item;
				this.trigerables.remove(triger);
			}
			
			if (item instanceof IElectrificable) {
				IElectrificable elec = (IElectrificable)item;
				this.electrificables.remove(elec);
			}
			
			if (this.items.containsKey(item.getId())) {
				item.destroy();
				this.items.remove(item.getId());
			}
			
			if (item instanceof Slimy) {
				Slimy slimy = (Slimy) item;
				if (this.slimyList.contains(slimy)) {
					this.slimyList.remove(slimy);
				}
				
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
		this.fixBgSize();
	} 
	
	private void fixBgSize() {
		if (this.backgroundSprite != null) {
			float wRatio = (this.levelWidth / this.backgroundSprite.getTextureRect().size.width);
			float hRatio = (this.levelHeight / this.backgroundSprite.getTextureRect().size.height);
			float ratio = Math.max(wRatio, hRatio);
			if (ratio > 1 || forceBgScale) {
				this.backgroundSprite.setScale(ratio);
			} else {
				this.backgroundSprite.setScale(1f);
			}
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
			this.hudLayer.gameBegin();
		}
	}
	
	public void simpleSelect(CGPoint gameReference) {
		if (this.selectedItem != null) {
			if (this.isPaused) {
				if (this.selectedItem instanceof GameItem) {				
					// Return true if auto or gameplay simpleSelect camera needed
					if (this.selectedItem.simpleSelect()) {
						if (this.gamePlay == null) {
							GameItem center = (GameItem)this.selectedItem;
							this.cameraManager.moveInterpolateTo(center, 0.3f);
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
		if (this.selectedItem != null && !this.isGameOver) {			
			this.selectedItem.selectionMove(gameReference);
			return true;
		}
		else {
			return false;
		}						
	}

	public void trySelect(CGPoint gameReference) {
		if (this.selectedItem == null && !this.isGameOver) {
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
						//AMZ density
						this.cameraManager.unzoomForMargin(this.selectedItem.getPosition(), SlimeFactory.SGSDensity);
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
	
	public void removeCurrentGamePlay() {
		if (this.gamePlay != null) {
			if (this.gamePlay instanceof GameItem) {
				GameItem item = (GameItem) gamePlay;
				this.removeGameItem(item);
			}			
		}
		
		this.gamePlay = null;		
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
	
	public CCBitmapFontAtlas getHudLabel() {
		return this.hudLayer.getLabel();
	}
	
	public void setHideCount(boolean isHide) {
		this.hudLayer.setHideCount(isHide);
	}
	
	public IGamePlay getGamePlay() {
		return this.gamePlay;
	}		
	
	public void goHome() {
		this.loadLevel(LevelHome.Id, GamePlay.None);
	}
	
	public void goNext() {
		if (this.getGamePlay().getType() == GamePlay.Survival) {
			String next = SlimeFactory.LevelBuilder.getNext(this.currentLevelName);
			if (next != null) {
				get(next, true, this.gamePlay.getType());
			}
		}
		
		if (this.getGamePlay().getType() == GamePlay.TimeAttack) {
			LevelDefinition levelDef = SlimeFactory.LevelBuilder.getNext(this.levelDefinition);
			if (levelDef != null) {
				get(levelDef);
			}
		}
		
	}
	
	public void startLevel() {
		if (this.getGamePlay() != null) {
			this.getGamePlay().startLevel();
		}
		else {
			if(this.getStartItem() != null) {	
				//AMZ replacing 1.0f by SGSDensity 
				this.getCameraManager().zoomInterpolateTo(this.getStartItem(),SlimeFactory.SGSDensity, 1.0f);
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
				if (this.getGamePlay().getType() == GamePlay.TimeAttack) {					
					Rank rank = TimeAttackGame.getRank(this.getGamePlay().bonusCount(), SlimeFactory.LevelBuilder.getTotalStar());
					AchievementStatistics.setLastRank(rank);
					this.levelDefinition.upgradeRank(rank);					
					
					LevelDefinition next = SlimeFactory.LevelBuilder.getNext(this.levelDefinition);
					if (next != null) {
						int diff = next.getWorld().getDifficulty(next.getNumber());
						SlimeFactory.GameInfo.unlockDifficulty(diff);
						next.setUnlock(true);
						next.handlePersistancy();
					}
					
					if (this.levelDefinition.isBoss()) {
						SlimeFactory.GameInfo.setStory1Finished(true);
					}
				} else {
					SlimeFactory.GameInfo.addLevelScore(this.lastScore);
					if (SlimeFactory.LevelBuilder.isBoss()) {
						AchievementStatistics.finishedSurvivalDifficulty = SlimeFactory.GameInfo.getDifficulty();
						SlimeFactory.GameInfo.unlockNextDifficultySurvival();
						SlimeFactory.GameInfo.setSurvivalGameOver(true);
					}
					
					SlimeFactory.GameInfo.setInARow();
					SlimeFactory.GameInfo.levelUp();
				}
				
				Level.currentLevel.getGamePlay().endMode();
				AchievementStatistics.winLeftTime = this.getGamePlay().getLeftTime();
				if (!AchievementStatistics.isTuto) {
					AchievementStatistics.consecutiveNoTutoWin++;
				}
				
				SlimeFactory.AchievementManager.handleEndLevelAchievements(false);
				this.levelDefinition.handlePersistancy();
			}
			
			if (showEndLevel) {
				this.showEndLevel();
			}						
			
			
			return true;
		}
		
		return false;
	}
	
	public boolean lose(boolean showEndLevel) {
		if (this.gamePlay != null && !this.isGameOver) {
			AchievementStatistics.consecutiveNoTutoWin = 0;
			this.lastScore = 0;
			this.isVictory = false;
			this.endLevel();
			
			ArrayList<Slimy> toLose = new ArrayList<Slimy>(this.aliveSlimyList);
			for(Slimy slimy : toLose) {
				slimy.lose();
			}
			
			if (this.getGamePlay().getType() == GamePlay.Survival) {
				SlimeFactory.GameInfo.setInARowLose();
			}
			
			if (showEndLevel) {
				this.showEndLevel();
			}						
						
			SlimeFactory.AchievementManager.handleEndLevelAchievements(true);
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
		
		if (this.selectedItem != null) {
			this.selectedItem.unselect();
			this.selectedItem = null;
		}
	}
	
	public void showEndLevel() {
		if (!this.endLevelShown) {
			if (this.isVictory) {
				SlimeFactory.GameInfo.storeIfBetterScore();
				Sounds.playEffect(R.raw.victory);
				this.showEndLevel(winTxt);
			}
			else {
				Sounds.playEffect(R.raw.lose);
				if (this.getGamePlay().getType() == GamePlay.Survival) {					
					this.showGameOver();
				} else {
				}
			}
		}
	}
			
	private void showGameOver() {
		CCDirector.sharedDirector().replaceScene(SurvivalGameOverLayer.getScene());
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
				this.endLevelLayer.setNextEnabled(true);
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
	
	public void activateCameraZoomByUser() {
		this.levelLayer.setZoomCameraActivated(true);
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
	
	public ArrayList<ITrigerable> getTrigerablesInItemToAdd(String name) {
		ArrayList<ITrigerable> list = new ArrayList<ITrigerable>();
		for (ITrigerable trigerable : this.trigerablesToAdd) {
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
	
	public void setLevelOrigin(CGPoint origin) {
		this.levelOrigin = origin;				
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
		
		// this.backgroundSprite.setPosition(this.getLevelOrigin());
		this.backgroundLayer.setPosition(this.getLevelOrigin());
//		this.gameLayer.removeChild(this.backgroundLayer, false);
//		this.gameLayer.addChild(this.backgroundLayer, -1, bgRatioX, bgRatioY, 
//				x, 
//				y);
	}
	
	// for tests
	public CCLayer getLevelLayer() {
		return this.levelLayer;
	}
	
	public CCLayer getGameLayer() {
		return this.gameLayer;
	}
	
	public CCLayer getBackroundLayer() {
		return this.backgroundLayer;
	}

	public void setNewBonus() {
		if (this.gamePlay != null && !this.gamePlay.isGameOver()) {
			this.gamePlay.setNewBonus();
		}
	}
	
	public GoalPortal getGoal() {
		return this.goal;
	}
	
	public void setGoal(GoalPortal goal) {
		this.goal = goal;
	}
	
	public EvacuationPlug getPlug() {
		return this.plug;		
	}
	
	public void setPlug(EvacuationPlug plug) {
		this.plug = plug;
	}
	
	public ArrayList<Slimy> slimies() {
		return this.slimyList;
	}
	
	public void setTitle(String title) {
		this.hudLayer.setTitle(title);
	}

	public GameItem getHelpItem() {
		return helpItem;
	}

	public void setHelpItem(GameItem helpItem) {
		this.helpItem = helpItem;
	}

	public void AnimNewBonus(CGPoint gamePosition) {
		CGPoint screenPos = this.cameraManager.getScreenPoint(gamePosition);
		this.hudLayer.starTaken(screenPos);
	}
	
	public float getNormalTimeRatio() {
		if (this.gamePlay != null) {
			return this.gamePlay.getNormalTimeRatio();
		} else {
			return 1.0f;
		}
	}
		
	public List<GameItem> getItemsByName(String name) {
		this.tempList.clear();
		if (name != null) {
			for(GameItem item : this.items.values()) {
				String itemName = item.getName();
				if (itemName != null && itemName.toUpperCase().equals(name.toUpperCase())) {
					this.tempList.add(item);
				}
			}
		}
		
		return this.tempList;
	}
	
	public List<IElectrificable> getElectrificables() {
		return this.electrificables;
	}
	
	public List<Slimy> aliveList() {
		return this.aliveSlimyList;
	}
	
	public void detachSlimies(GameItemPhysic gameItem) {
		SlimeFactory.Log.d(SlimeAttack.TAG, "detachSlimies gameItem start");
		for(Slimy slimy : this.aliveSlimyList) {
			slimy.detach(gameItem);
		}
		
		SlimeFactory.Log.d(SlimeAttack.TAG, "detachSlimies gameItem end");
	}
	
	public void detachSlimies() {
		SlimeFactory.Log.d(SlimeAttack.TAG, "detachSlimies start");
		for(Slimy slimy : this.aliveSlimyList) {
			slimy.detach();
		}
		
		SlimeFactory.Log.d(SlimeAttack.TAG, "detachSlimies end");
	}

	public LevelDefinition getLevelDefinition() {
		return this.levelDefinition;
	}

	public PauseLayer getPauseLayer() {
		return this.pauseLayer;
	}
	
	public void timesUp() {
		this.hudLayer.timesup();
	}
	
	public void killAllSlimies() {
		for(Slimy slimy : this.aliveSlimyList) {
			slimy.splash();
		}
	}
}