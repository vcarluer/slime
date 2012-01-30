package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Box extends GameItemPhysic{
	public static final int glassbox = 0;
	public static final int multitubes = 1;
	public static final int tube = 2;
	public static final int bottle = 3;
		
	public static String Anim_Base_tube = "gw_smalltube";
	public static String Anim_Base_bottle = "gw_bottle";
	public static String Anim_Base_glassbox = "gw_box";
	public static String Anim_Base_multitubes = "gw_multitube";
	public static boolean chainMode;
	
	private int type;
	private boolean isStatic;	
	
	public static void setChainMode(boolean value) {
		chainMode = value;
	}
	
	public Box(float x, float y, float width, float height,
			World world, float worldRatio, int boxType, boolean isStatic, boolean isStickable) {
		super(x, y, width, height, world, worldRatio);
		
		this.spriteType = SpriteType.SINGLE_SCALE;
		this.zOrder = Level.zMid;
		this.noStick = !isStickable;
		this.setStatic(isStatic);
		this.setType(boxType);
	}

	@Override
	protected void initBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();		
		if (this.isStatic()) {
			bodyDef.type = BodyType.StaticBody;
		} else {
			bodyDef.type = BodyType.DynamicBody;
		}
				
		CGPoint spawnPoint = new CGPoint();
		spawnPoint.x = this.position.x;
		spawnPoint.y = this.position.y;
		bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
		
		// Define another box shape for our dynamic body.
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = dynamicBox;	
    		if (chainMode) {
    			fixtureDef.density = 5.0f;
    			fixtureDef.friction = 0.5f;
        		fixtureDef.restitution = 0.2f;
    		}
    		else {
    			fixtureDef.density = 1.0f;
    			fixtureDef.friction = 3.0f;
        		fixtureDef.restitution = 0f;
    		}
    		
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    		this.body.createFixture(fixtureDef);
    	} 		
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		switch (this.getType()) {		
		case tube:
			return Anim_Base_tube;
		case bottle:
			return Anim_Base_bottle;		
		case multitubes:		
			return Anim_Base_multitubes;
		case glassbox:
		default:
			return Anim_Base_glassbox;
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
}
