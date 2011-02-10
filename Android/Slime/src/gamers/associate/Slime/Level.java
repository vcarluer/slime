package gamers.associate.Slime;

import java.util.ArrayList;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.GameItem"
 */
public abstract class Level {
	protected CCNode rootNode;	
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
	
	public Level(CCNode node) {
		this.rootNode = node;
		this.items = new ArrayList<GameItem>();
		this.init();
	}
	
	protected void init()
	{		
		this.gravity = new Vector2(0, -10);
		this.world = new World(this.gravity, true);
		this.contactManager = new ContactManager();
		this.world.setContactListener(this.contactManager);
		
		// Sprite too big for VM in UbuntuRox
		/*CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("decor.plist");
		CCSpriteSheet spriteSheet = CCSpriteSheet.spriteSheet("decor.png");
		this.rootNode.addChild(spriteSheet);
		this.backgroundSprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("decor.png"));
		this.backgroundSprite.setAnchorPoint(0, 0);
		this.backgroundSprite.setScale(0.5f);
		spriteSheet.addChild(this.backgroundSprite);*/
				
		SlimyFactory.Attach(this.rootNode, this.world, this.worldRatio);
		SpawnPortalFactory.Attach(this.rootNode);
		this.spawnPortal = SpawnPortalFactory.create(
				CCDirector.sharedDirector().winSize().getWidth() / 2, 
				CCDirector.sharedDirector().winSize().getHeight() - 32,
				80,
				5);
		this.items.add(this.spawnPortal);
	}
	
	protected void tick(float delta) {
		synchronized (world) {
    		world.step(delta, 6, 2);
    	}
		
		for(GameItem item : this.items) {
			item.render(delta);
		}
	}
	
	public void SpawnSlime() {		
		Slimy slimy = SlimyFactory.create(this.spawnPortal.getPosition().x, this.spawnPortal.getPosition().y);
		slimy.fall();
		this.items.add(slimy);
		
	}
}
