package gamers.associate.Slime;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCFollow;
import org.cocos2d.actions.camera.CCCameraAction;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.opengl.CCCamera;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.GameItem"
 */
public abstract class Level {		
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
	
	private CGPoint moveCameraBy;
	private boolean isCameraOnContinuousMove;
	
	public Level() {
		this.scene = CCScene.node();
		this.levelLayer = new LevelLayer(this);
		this.hudLayer = new HudLayer();
		this.backgroundLayer = new BackgoundLayer();
		this.gameLayer = CCLayer.node();
		
		this.gameLayer.addChild(this.backgroundLayer, 0);
		this.gameLayer.addChild(this.levelLayer, 1);
		
		this.scene.addChild(this.gameLayer, 0);
		this.scene.addChild(this.hudLayer, 1);
				
		this.items = new ArrayList<GameItem>();
		
		this.levelWidth = CCDirector.sharedDirector().winSize().getWidth();
		this.levelHeight = CCDirector.sharedDirector().winSize().getHeight();
		
		this.moveCameraBy = new CGPoint();
		
		this.init();
	}
	
	public CCScene getScene() {		
		return this.scene;
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
		this.backgroundSprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("decor.png"));
		this.backgroundSprite.setAnchorPoint(0, 0);
		spriteSheet.addChild(this.backgroundSprite);
		
		SpriteSheetFactory.add("labo");
		SlimeFactory.attachAll(this.levelLayer, this.world, this.worldRatio);		
		
		this.spawnPortal = SlimeFactory.SpawnPortal.createAndMove(
				CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() - 32,
				CCDirector.sharedDirector().winSize().getWidth() / 2,
				5);
		
		this.items.add(this.spawnPortal);
		
		this.label = CCLabel.makeLabel("Hud !", "DroidSans", 16);		
		this.hudLayer.addChild(this.label, 0);
		label.setPosition(
				CGPoint.ccp(CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() - 20));
	}
	
	protected void tick(float delta) {
		synchronized (world) {
    		world.step(delta, 6, 2);
    	}
		
		for(GameItem item : this.items) {
			item.render(delta);
		}
		
		if (this.isCameraOnContinuousMove) {
			this.moveCameraBy(this.moveCameraBy);
		}
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
		this.cameraFollow(gi);		
	}
	
	protected void cameraFollow(GameItem item) {		
		CCScaleTo scale = CCScaleTo.action(1, 1.5f);
		this.gameLayer.runAction(scale);
		//this.backgroundLayer.runAction(scale);
		
		/*CCCamera cam = this.levelLayer.getCamera();
		cam.setEye(0, 0, 1000f);*/
		
		/*CCFollow follow = CCFollow.action(item.getSprite());			
		this.levelLayer.runAction(follow);	*/	
	}
	
	public void moveCameraBy(CGPoint delta) {
		CGPoint position = this.gameLayer.getPosition();
		float maxLeft = - (this.levelWidth / 2) * this.gameLayer.getScale();				
		float left = - position.x - CCDirector.sharedDirector().winSize().width / 2;		
		if (delta.x > 0) {
			if ((left - delta.x) < maxLeft) {
				delta.x = left - maxLeft;
			}
		}
		
		float maxRight = this.levelWidth / 2 * this.gameLayer.getScale();				
		float right = - position.x + CCDirector.sharedDirector().winSize().width / 2;		
		if (delta.x < 0) {
			if ((right - delta.x) > maxRight) {
				delta.x = right - maxRight;
			}
		}
		
		float maxTop = this.levelHeight / 2 * this.gameLayer.getScale();
		float top = position.y + CCDirector.sharedDirector().winSize().height / 2;
		if (delta.y > 0) {
			if ((top + delta.y) > maxTop) {
				delta.y = maxTop - top;
			}
		}
		
		float maxBottom = - this.levelHeight / 2 * this.gameLayer.getScale();
		float bottom = position.y - CCDirector.sharedDirector().winSize().height / 2;
		if (delta.y < 0) {
			if ((bottom + delta.y) < maxBottom) {
				delta.y = maxBottom - bottom;
			}
		}
		
		position.x += delta.x;
		position.y += delta.y;
		this.gameLayer.setPosition(position);		
	}
	
	public void continuousMoveCameraBy(CGPoint delta) {
		this.moveCameraBy.x = delta.x;
		this.moveCameraBy.y = delta.y;
		this.isCameraOnContinuousMove = true;
	}
	
	public void stopContinousMoving() {
		this.isCameraOnContinuousMove = false;
	}
	
	public void zoomCameraBy(float zoomDelta) {
		float scale = this.gameLayer.getScale() + zoomDelta;
		this.gameLayer.setScale(scale);
	}
}
