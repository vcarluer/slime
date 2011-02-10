package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends GameItemPhysic {	
	public static String texture = "metal.png"; 
		
	public Platform(CCNode node, float x, float y, World world, float worldRatio, float width, float height) {
		super(node, x, y, world, worldRatio);
		
		this.bodyWidth = this.width = width;
		this.bodyHeight = this.height = height;
		this.initBody();
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
		staticBox.setAsBox((this.bodyWidth * this.scale) / this.worldRatio / 2, (this.bodyHeight * this.scale) / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = staticBox;	
    		fixtureDef.density = 1.0f;
    		fixtureDef.friction = 0.3f;
    		fixtureDef.restitution = 0.3f;
    		this.body.createFixture(fixtureDef);
    	}  
	}
}
