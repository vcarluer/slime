package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.IBurnable;
import gamers.associate.Slime.items.base.ITrigerable;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BecBunsen extends GameItemPhysic implements ITrigerable {
	public static String Anim_Base = "bunsen";
	public static String Anim_Wait = "bunsen-wait";
	public static String Anim_Starting = "bunsen-starting";
	
	private static float Default_Width = 20f;
	private static float Default_Height = 67f;
	
	private static float Reference_Width = 20f;
	private static float Reference_Height = 67f;
	
	private static float Reference_Fire_Width = 17.6f;
	private static float Reference_Fire_Height = 38.6f;
	private static float Reference_Base_Width = 7f;
	private static float Reference_Base_Height = Reference_Height - Reference_Fire_Height;
	
	private float base_width;
	private float base_height;
	private float fire_width;
	private float fire_height;
	
	private boolean isOn;
	
	private boolean startOn;
	
	private float animDelay = 0f;
	
	public BecBunsen(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		
		this.spriteType = SpriteType.ANIM_SCALE;
		this.zOrder = Level.zMid;
		this.noStick = true;
		
		if (width == 0 && height == 0) {
			this.width = this.bodyWidth = Default_Width;
			this.height = this.bodyHeight = Default_Height; 
		}
		
		this.referenceSize.width = Reference_Width;
		this.referenceSize.height = Reference_Height;
		
		this.base_width = Reference_Base_Width / Reference_Width * this.width;
		this.base_height = Reference_Base_Height / Reference_Height * this.height;
		this.fire_width = Reference_Fire_Width / Reference_Width * this.width;
		this.fire_height = Reference_Fire_Height / Reference_Height * this.height;				
	}
	
	public void setAnimDelay(float delay) {
		this.animDelay = delay;
	}
	
	public float getAnimDelay() {
		return this.animDelay;
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
		PolygonShape dynamicBoxBase = new PolygonShape();
		Vector2 center = new Vector2();
		center.x = 0;
		center.y = ( - this.height / 2f + this.base_height / 2f) / this.worldRatio;
		dynamicBoxBase.setAsBox(this.base_width / this.worldRatio / 2, this.base_height / this.worldRatio / 2, center, 0f);						
		
		// Define another box shape for our dynamic body.
		PolygonShape dynamicBoxFire = new PolygonShape();
		Vector2 center2 = new Vector2();
		center2.x = 0;
		center2.y = ( - this.height / 2f + this.base_height + this.fire_height / 2f) / this.worldRatio;
		dynamicBoxFire.setAsBox(this.fire_width / this.worldRatio / 2f, this.fire_height / this.worldRatio / 2f, center2, 0f);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDefBase = new FixtureDef();
    		fixtureDefBase.shape = dynamicBoxBase;	    		
    		fixtureDefBase.density = 1.0f;
    		fixtureDefBase.friction = 3.0f;
    		fixtureDefBase.restitution = 0f;       		    	
    		fixtureDefBase.filter.categoryBits = GameItemPhysic.Category_InGame;
    		
    		FixtureDef fixtureDefFire = new FixtureDef();
    		fixtureDefFire.shape = dynamicBoxFire;    		  
    		fixtureDefFire.isSensor = true;
    		fixtureDefFire.filter.categoryBits = GameItemPhysic.Category_InGame;
    		    		
    		this.body.createFixture(fixtureDefFire);
    		this.body.createFixture(fixtureDefBase);    		
    	}
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return BecBunsen.Anim_Base;
	}
	
	public void turnOn() {
		this.turnOn(false);
	}
	
	public void turnOn(boolean force) {
		if (!this.isOn || force) {			
			CCDelayTime delay = CCDelayTime.action(this.animDelay);
			CCAnimate animateStart = CCAnimate.action(this.animationList.get(Anim_Starting), false);
			CCCallFunc callStartEnded = CCCallFunc.action(this, "turnedOn");
			CCSequence sequence = CCSequence.actions(delay, animateStart, callStartEnded);
			this.sprite.runAction(sequence);
		}
	}
	
	public void turnedOn() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Base), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.sprite.runAction(repeat);
		this.isOn = true;
		this.body.setActive(true);
	}
	
	public void turnOff() {
		this.turnOff(false);
	}
	
	public void turnOff(boolean force) {
		if (this.isOn || force) {
			CCDelayTime delay = CCDelayTime.action(this.animDelay);
			CCAnimate animateStart = CCAnimate.action(this.animationList.get(Anim_Starting), false);		
			CCCallFunc callStartEnded = CCCallFunc.action(this, "turnedOff");
			CCSequence sequence = CCSequence.actions(delay, animateStart.reverse(), callStartEnded);
			this.sprite.runAction(sequence);
		}
	}
	
	public void turnedOff() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.sprite.runAction(repeat);
		this.isOn = false;
		this.body.setActive(false);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.game.ContactInfo)
	 */
	@Override
	protected void handleContact(ContactInfo contact) {		
		super.handleContact(contact);
		if (this.isOn) {
			if (contact.getContactWith() instanceof IBurnable) {
				((IBurnable)contact.getContactWith()).burn();
			}
		}
	}

	public void trigger(Object source, String data) {
		if (this.isOn) {
			this.turnOff();
		}
		else {
			this.turnOn();
		}
	}
	
	public void setStartOn(boolean startOn) {
		this.startOn = startOn;
	}
	
	public void initanimation() {
		this.turnedOff();
		if (this.startOn) {
			this.turnOn();
		}
	}
	
	public boolean getStartOn() {
		return this.startOn;
	}

	@Override
	public void triggerOn(Object source) {
		this.turnOn(true);
	}

	@Override
	public void triggerOff(Object source) {
		this.turnOff(true);
	}
}
