package gamers.associate.Slime.items;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicPolygon extends GameItemPhysic {
	public static String texture = "metal2.png";
	protected CGPoint[] vertices;
	protected CGPoint[] bodyPoints;
	protected boolean isDynamic;	
	
	// Vertices counter clockwise base on 0, 0
	public PhysicPolygon(CCNode node, float x, float y, float width,
			float height, World world, float worldRatio) {
		super(node, x, y, width, height, world, worldRatio);
		
		this.textureMode = TextureMode.REPEAT;
	}
	
	public void initPoly(boolean isDynamic, CGPoint[] bodyPoints, CGPoint[] glVertices) {
		this.bodyPoints = bodyPoints;
		this.vertices = glVertices;
		this.isDynamic = isDynamic;		
	}

	@Override
	protected void initBody() {		
		if (vertices != null) {
			// Physic body
			BodyDef bodyDef = new BodyDef();		
			if (this.isDynamic) {
				bodyDef.type = BodyType.DynamicBody;
			}
			
			CGPoint spawnPoint = new CGPoint();
			spawnPoint.x = this.position.x;
			spawnPoint.y = this.position.y;
			bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
			
			// Define another box shape for our dynamic body.
			PolygonShape polygonShape = new PolygonShape();								
			
			Vector2[] vectors = this.getWorldPolygonVectors();
			polygonShape.set(vectors);
			
			synchronized (world) {
	    		// Define the dynamic body fixture and set mass so it's dynamic.
	    		this.body = world.createBody(bodyDef);
	    		this.body.setUserData(this);	    		
	    		FixtureDef fixtureDef = new FixtureDef();
	    		fixtureDef.shape = polygonShape;	
	    		fixtureDef.density = 1.0f;
	    		fixtureDef.friction = 1.0f;	    		
	    		if (this.isDynamic) {
	    			fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
	    		}
	    		else {
	    			fixtureDef.filter.categoryBits = GameItemPhysic.Category_Static;
	    		}
	    			    		
	    		this.body.createFixture(fixtureDef);
	    	}		
		}
	}
	
	private Vector2[] getWorldPolygonVectors() {		
		Vector2[] vectors = new Vector2[this.bodyPoints.length];		
		for(int i = 0; i < this.bodyPoints.length; i++) {
			Vector2 vec = new Vector2(this.bodyPoints[i].x / this.worldRatio, this.bodyPoints[i].y / this.worldRatio);
			vectors[i] = vec;
		}
		
		return vectors;
	}
}
