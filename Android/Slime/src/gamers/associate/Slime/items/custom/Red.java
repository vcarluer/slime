package gamers.associate.Slime.items.custom;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.items.base.GameItemPhysic;

public class Red extends GameItemPhysic {
	public static final float Default_Width = 125;
	public static final float Default_Height = 85;	

	public static String Anim_Bite = "bite";
	public static String Anim_Breaking = "breaking";
	public static String Anim_Contracting = "contracting";
	public static String Anim_Wait = "wait";
	
	public Red(float x, float y, float width, float height, World world,
			float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		
		this.spriteType = spriteType.ANIM_SCALE;
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;			
		}				
	}

	@Override
	protected void initBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();		
		bodyDef.type = BodyType.DynamicBody;
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
			fixtureDef.density = 8.0f;
			fixtureDef.friction = 8.0f;
    		fixtureDef.restitution = 0.1f;
    		
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    		this.body.createFixture(fixtureDef);
		}
	}
	
	@Override
	protected String getReferenceAnimationName() {
		return Anim_Wait;
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {					
		super.handleContact(item);
	}
	
	public void waitAnim() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
		CCDelayTime delay = CCDelayTime.action(2f);
		CCSequence seq = CCSequence.actions(animate, delay);
		CCRepeatForever repeat = CCRepeatForever.action(seq);
		this.sprite.runAction(repeat);	
	}
}
