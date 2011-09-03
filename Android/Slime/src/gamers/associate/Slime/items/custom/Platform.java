package gamers.associate.Slime.items.custom;


import gamers.associate.Slime.R;
import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Platform extends GameItemPhysic {	
	public static final int Sticky = 0;
	public static final int Bump = 1;
	public static final int NoSticky = 2;
	public static final int Icy = 3;
	
	public static String Anim_Base_Sticky = "sticky";
	public static String Anim_Base_Bump = "bumper";
	public static String Anim_Base_NoSticky = "nosticky";
	public static String Anim_Base_Icy = "ice";
	
	private int type;
		
	public Platform(float x, float y, float width, float height, World world, float worldRatio, int platformType) {
		super(x, y, width, height, world, worldRatio);
		this.spriteType = SpriteType.SINGLE_REPEAT;		
		this.zOrder = Level.zBack;
		this.type = platformType;
		if (this.type != Sticky) {
			this.noStick = true;
		}
	}
	
	@Override
	protected void initBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
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
    		fixtureDef.friction = 0f;
    		if (this.type != Icy ) {    			
    			fixtureDef.friction = 1.0f;
    		}
    		
    		fixtureDef.restitution = 0f;
    		if (this.type == Bump) {
    			fixtureDef.restitution = 1.5f;
    		}    		
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_Level;
    		this.body.createFixture(fixtureDef);
    	}  
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {		
		switch (this.type) {		
		case Icy:
			return Anim_Base_Icy;
		case Bump:
			return Anim_Base_Bump;
		case NoSticky:
			return Anim_Base_NoSticky;
		case Sticky:
			default:
			return Anim_Base_Sticky;
		}
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {		
		super.handleContact(item);
	}	
}
