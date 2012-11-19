package gamers.associate.Slime.items.custom;


import gamers.associate.Slime.R;
import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends GameItemPhysic {	
	public static final int Sticky = 0;
	public static final int Bump = 1;
	public static final int NoSticky = 2;
	public static final int Icy = 3;
	public static final int Wall = 4;
	public static final int Corner = 5;
	public static final int T = 6;
	public static final int Cross = 7;
	public static final int End = 8;
	public static final int NoStickyCorner = 9;
	public static final int NoStickyEnd = 10;
	
	public static float Default_Height= 32f;
	
	public static String Anim_Base_Sticky = "pf";
	public static String Anim_Base_Bump = "pf-bump";
	public static String Anim_Base_NoSticky = "pf-nosticky";
	public static String Anim_Base_Icy = "ice";
	public static String Anim_Base_Wall = "glass";
	public static String Anim_Base_Corner = "pf-corner";
	public static String Anim_Base_T = "pf-T";
	public static String Anim_Base_Cross = "pf-cross";
	public static String Anim_Base_End = "pf-end";
	public static String Anim_Base_NoSticky_Corner = "pf-nosticky-corner";
	public static String Anim_Base_NoSticky_End = "pf-nosticky-end";
	
	private int type;
	private boolean move;
		
	public Platform(float x, float y, float width, float height, World world, float worldRatio, int platformType, boolean platformMove) {
		super(x, y, width, height, world, worldRatio);
		this.spriteType = SpriteType.SINGLE_REPEAT;		
		this.zOrder = Level.zMid;
		this.type = platformType;
		this.move = platformMove;
		if (this.type != Sticky && this.type != Wall && this.type != Corner && this.type != T && this.type != Cross && this.type != End) {
			this.noStick = true;
		}
	}
	
	@Override
	protected void initBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();
		if(this.move){
			bodyDef.type = BodyType.DynamicBody;	
		}
		else{
			bodyDef.type = BodyType.StaticBody;
		}
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
    			fixtureDef.restitution = 1.50f;
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
		case NoStickyCorner:
			return Anim_Base_NoSticky_Corner;
		case NoStickyEnd:
			return Anim_Base_NoSticky_End;
		case Wall:
			return Anim_Base_Wall;
		case Corner:
			return Anim_Base_Corner;
		case T:
			return Anim_Base_T;
		case Cross:
			return Anim_Base_Cross;
		case End:
			return Anim_Base_End;
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
		if (this.type == Bump) {
			Sounds.playEffect(R.raw.platformbump);
		}else if(this.type == Sticky || this.type == Wall){
			Sounds.playEffect(R.raw.platformstick);
		}
		super.handleContact(item);
		
	}
	
	public int getType() {
		return this.type;
	}
	
	public boolean getMove() {
		return this.move;
	}
	
	public void animate() {		
	}
}
