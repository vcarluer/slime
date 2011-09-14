package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.IBurnable;
import gamers.associate.Slime.items.base.ITrigerable;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class CircularSaw extends GameItemPhysic implements ITrigerable {
	public static String Anim_Running = "saw-running";
	public static String Anim_Wait = "saw-wait";
		
	private static float Default_Width = 40f;
	private static float Default_Height = 40f;
	
	private static float Reference_Width = 40f;
	private static float Reference_Height = 40f;
	
	private boolean isOn;
	
	private boolean startOn;
	
	private String name;
	
	public CircularSaw(float x, float y, float width, float height,
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
		
		this.name = "";
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
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = dynamicBox;	    		
			fixtureDef.density = 1.0f;
			fixtureDef.friction = 3.0f;
    		fixtureDef.restitution = 0f;    		
    		
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    		this.body.createFixture(fixtureDef);
    	} 		
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return CircularSaw.Anim_Wait;
	}
	
	public void turnOn() {
		if (!this.isOn) {
			this.sprite.stopAllActions();
			CCAnimate animateStart = CCAnimate.action(this.animationList.get(Anim_Running), false);			
			CCRepeatForever repeat = CCRepeatForever.action(animateStart);
			this.sprite.runAction(repeat);
			this.isOn = true;
		}
	}	
	
	public void turnOff() {
		if (this.isOn) {
			this.sprite.stopAllActions();			
			CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);		
			CCRepeatForever repeat = CCRepeatForever.action(animate);
			this.sprite.runAction(repeat);
			this.isOn = false;
		}
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

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
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
		if (this.startOn) {
			this.turnOn();
		}
		else {
			this.turnOff();
		}
	}
}
