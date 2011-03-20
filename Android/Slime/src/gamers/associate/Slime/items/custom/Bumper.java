package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Bumper extends GameItemPhysic {
	public static float Default_Powa = 1.5f;	
	public static String Anim_Base = "red_tri";	
	private static float Default_Width = 64f;
	private static float Default_Height = 64f;
	
	private float powa;
		
	public Bumper(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);		
		this.spriteType = SpriteType.SINGLE_SCALE;		
		
		if (width == 0 && height == 0) {
			this.width = this.bodyWidth = Default_Width;
			this.height = this.bodyHeight = Default_Height; 
		}		
		this.powa = Default_Powa;
	}
	
	public void setPowa(float powa) {
		this.powa = powa;
		for(Fixture fix : this.body.getFixtureList()) {
			fix.setRestitution(this.powa);
		}
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
		PolygonShape bumperShape = new PolygonShape();		
		float bw2 = this.bodyWidth / 2 / this.worldRatio;
		float bh2 = this.bodyHeight / 2 / this.worldRatio;
		Vector2[] vertices = new Vector2[3];		
		vertices[0] = new Vector2(bw2, - bh2);
		vertices[1] = new Vector2(- bw2, bh2);
		vertices[2] = new Vector2(- bw2, - bh2);			
		
		bumperShape.set(vertices);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = bumperShape;	
    		fixtureDef.density = 1.0f;
    		fixtureDef.friction = 1.0f;
    		fixtureDef.restitution = this.powa;
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_Level;
    		this.body.createFixture(fixtureDef);
    	}		
	}
	
	public void waitAnim() {		
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return Bumper.Anim_Base;
	}		
}
