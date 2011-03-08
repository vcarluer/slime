package gamers.associate.Slime.items;


import gamers.associate.Slime.CCSpriteRepeat;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.nodes.CCTextureNode;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends GameItemPhysic {	
	public static String texture = "metal2.png"; 
		
	public Platform(CCNode node, float x, float y, float width, float height, World world, float worldRatio) {
		super(node, x, y, width, height, world, worldRatio);				
		this.initBody();
		this.textureMode = TextureMode.REPEAT;
		/*this.textureNode = CCTextureRepeatNode.sprite(Platform.texture);
		this.textureNode.setWidth(this.width);
		this.textureNode.setHeight(this.height);		
		this.textureNode.setTextureRect(x - width / 2, y - height / 2, width, height, false);
		//this.textureNode.setFlipY(true);
		this.rootNode.addChild(this.textureNode);
		//this.textureNode = new CCTextureNode();
		//this.textureNode.setTexture(CCTextureCache.sharedTextureCache().addImage(Platform.texture));
		//this.rootNode.addChild(this.textureNode, 0);*/
	}
	
	@Override
	protected void initBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();		
		CGPoint spawnPoint = new CGPoint();
		spawnPoint.x = this.position.x;
		spawnPoint.y = this.position.y;
		bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
		
		// Define another box shape for our dynamic body.
		PolygonShape staticBox = new PolygonShape();
		staticBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = staticBox;	
    		fixtureDef.density = 1.0f;
    		fixtureDef.friction = 1.0f;
    		fixtureDef.restitution = 0f;
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_Static;
    		this.body.createFixture(fixtureDef);
    	}  
	}
}
