package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class SpawnCannon extends GameItemPhysic {
	public static String texture = "metal.png";
	public static float Default_Width = 32f;
	public static float Default_Height = 32f;
	
	public SpawnCannon(CCNode node, float x, float y, float width, float height,
			World world, float worldRatio) {
		super(node, x, y, width, height, world, worldRatio);
		
		if (width == 0 && this.height == 0) {
			this.bodyWidth = this.width = Default_Width;
			this.bodyHeight = this.height = Default_Height;
			this.transformTexture();
		}
		
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
	
	public Slimy spawnSlime(CGPoint target) {
		Slimy slimy = SlimeFactory.Slimy.create(this.position.x, this.position.y + 40, 1.0f);		
		Vector2 targetVec = new Vector2(target.x, target.y);
		Vector2 pos = slimy.getBody().getPosition();
		Vector2 impulse = targetVec.sub(pos).mul(1 / this.worldRatio);
		//Vector2 impulse = new Vector2(targetVec.x - pos.x, targetVec.y - pos.y);
		slimy.getBody().applyLinearImpulse(impulse, pos);
		return slimy;
	}
}
