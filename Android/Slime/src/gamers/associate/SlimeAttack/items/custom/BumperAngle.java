package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.ContactInfo;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.base.GameItemPhysic;
import gamers.associate.SlimeAttack.items.base.SpriteType;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BumperAngle extends GameItemPhysic {
	public static float Default_Powa = 1.5f;	
	public static String Anim_Wait = "bumper-2-wait";
	public static String Anim_Bump = "bumper-2";	
	public static float Default_Width = 48f;
	public static float Default_Height = 48f;
	private static float Reference_Width = 53f;
	private static float Reference_Height = 53f;
	
	private float powa;
		
	public BumperAngle(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);		
		this.spriteType = SpriteType.ANIM_SCALE;		
		
		if (width == 0 && height == 0) {
			this.width = this.bodyWidth = Default_Width;
			this.height = this.bodyHeight = Default_Height; 
		}		
		this.powa = Default_Powa;
		this.setNoStick(true);
		this.referenceSize.width = Reference_Width;
		this.referenceSize.height = Reference_Height;
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
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.sprite.runAction(repeat);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return BumperAngle.Anim_Wait;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {
		Sounds.playEffect(R.raw.bump);
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Bump), false);
		// Delay to see too quick animation
		CCDelayTime delay = CCDelayTime.action(0.3f);
		CCSequence seq = CCSequence.actions(animate, delay);
		this.sprite.runAction(seq);
		super.handleContact(item);
	}		
}
