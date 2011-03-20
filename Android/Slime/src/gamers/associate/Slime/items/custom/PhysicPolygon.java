package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.CCSpritePolygon;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicPolygon extends GameItemPhysic {
	public static String Anim_Base = "metal2";
	protected CGPoint[] vertices;
	protected CGPoint[] bodyPoints;
	protected boolean isDynamic;	
	
	// Vertices counter clockwise base on 0, 0
	public PhysicPolygon(float x, float y, float width,
			float height, World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		
		this.spriteType = SpriteType.POLYGON_REPEAT;		
		this.zOrder = Level.zBack;
	}
	
	public void initPoly(boolean isDynamic, CGPoint[] bodyPoints, CGPoint[] glVertices) {
		this.bodyPoints = bodyPoints;
		this.vertices = glVertices;
		this.isDynamic = isDynamic;				
	}
	
	public void initPolySprite(CCSprite createdSprite) {
		CCSpritePolygon spritePoly = (CCSpritePolygon)createdSprite;
		spritePoly.setVertices(this.vertices);
	}
	
	@Override
	protected void postCreateSprite(CCSprite createdSprite) {
		this.initPolySprite(createdSprite);
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
	    			fixtureDef.filter.categoryBits = GameItemPhysic.Category_Level;
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

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return PhysicPolygon.Anim_Base;
	}
}
