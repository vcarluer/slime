package gamers.associate.Slime.items.custom;

import java.util.ArrayList;
import java.util.Arrays;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Triangulate;
import gamers.associate.Slime.items.base.CCSpritePolygon;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicPolygon extends GameItemPhysic {
	public static final int Fill = 0;
	public static final int Empty = 1;
	public static String Anim_Base_Fill = "glass";
	public static String Anim_Base_Empty = "empty";
	protected CGPoint[] vertices;
	protected CGPoint[] bodyPoints;
	protected boolean isDynamic;
	protected boolean isStickable;
	
	protected int type;
	
	// Vertices counter clockwise base on 0, 0
	public PhysicPolygon(float x, float y, float width,
			float height, World world, float worldRatio, int type, boolean isStickable) {
		super(x, y, width, height, world, worldRatio);
		
		this.spriteType = SpriteType.POLYGON_REPEAT;		
		this.zOrder = Level.zBack;
		this.setNoStick(!isStickable);
		this.type = type;
	}
	
	public void initPoly(boolean isDynamic, CGPoint[] bodyPoints) {
		this.bodyPoints = bodyPoints;					
		this.isDynamic = isDynamic;
		
		ArrayList<CGPoint> contour = new ArrayList<CGPoint>(Arrays.asList(bodyPoints));
		ArrayList<CGPoint> body = new ArrayList<CGPoint>();
		Triangulate.process(contour, body);		
		
		this.vertices = new CGPoint[body.size()];
		int j = 0;
		for(CGPoint point : body) {
			this.vertices[j] = point;
			j++;
		}
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
			} else {
				bodyDef.type = BodyType.StaticBody;
			}
			
			CGPoint spawnPoint = new CGPoint();
			spawnPoint.x = this.position.x;
			spawnPoint.y = this.position.y;
			bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
			
			// Define another box shape for our dynamic body.
											
			
			synchronized (world) {
				this.body = world.createBody(bodyDef);
	    		this.body.setUserData(this);
	    		
				for (int i =0; i < this.vertices.length; i+=3) {
					CGPoint[] points = new CGPoint[3];
					points[0] = this.vertices[i];
					points[1] = this.vertices[i + 1];
					points[2] = this.vertices[i + 2];
					
					Vector2[] vectors = this.getWorldPolygonVectors(points);
					PolygonShape polygonShape = new PolygonShape();
					polygonShape.set(vectors);
					
					// Define the dynamic body fixture and set mass so it's dynamic.	    			    		
		    		FixtureDef fixtureDef = new FixtureDef();
		    		fixtureDef.shape = polygonShape;	
		    		fixtureDef.density = 1.0f;
		    		fixtureDef.friction = 5.0f;	    
		    		fixtureDef.restitution = 0f;
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
	}
	
	private Vector2[] getWorldPolygonVectors() {		
		return this.getWorldPolygonVectors(this.bodyPoints);
	}
	
	private Vector2[] getWorldPolygonVectors(CGPoint[] points) {		
		Vector2[] vectors = new Vector2[points.length];		
		for(int i = 0; i < points.length; i++) {
			Vector2 vec = new Vector2(points[i].x / this.worldRatio, points[i].y / this.worldRatio);
			vectors[i] = vec;
		}
		
		return vectors;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		switch (this.type) {
			case Empty:
				return Anim_Base_Empty;
			case Fill:
			default:
				return Anim_Base_Fill;
		}
	}
	
	public void initAnimation() {
		if (this.type == Fill) {
			CCAnimate animation = CCAnimate.action(this.animationList.get(Anim_Base_Fill), false);
			this.currentAction = CCRepeatForever.action(animation);
			this.sprite.runAction(this.currentAction);
		}
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#runReferenceAction()
	 */
	@Override
	protected void runReferenceAction() {
		this.initAnimation();
	}
}
