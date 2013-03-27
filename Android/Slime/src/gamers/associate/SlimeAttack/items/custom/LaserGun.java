package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItemPhysic;
import gamers.associate.SlimeAttack.items.base.ITrigerable;
import gamers.associate.SlimeAttack.items.base.SpriteType;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.config.ccMacros;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LaserGun extends GameItemPhysic implements ITrigerable {
	public static String Anim_On = "laser-on";
	public static String Anim_Wait = "laser-wait";
	public static String Anim_Firing = "laser-firing";
	
	public static float Default_Width = 65f;
	public static float Default_Height = 15f;
	
	private static float Reference_Width = 65f;
	private static float Reference_Height = 15f;
	
	private static float Default_Beam_Offset = 12f;
	
	private boolean isOn;
	protected boolean turningOn;
	
	private boolean startOn;
	private String target;
	
	private ArrayList<LaserBeam> beam;
	
	private float beamOffset;
	private CGPoint tmp;
	
	public LaserGun(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		
		this.spriteType = SpriteType.ANIM_SCALE;
		this.zOrder = Level.zMid;
		this.noStick = true;
		
		if (width == 0 && height == 0) {
			this.width = this.bodyWidth = Default_Width;
			this.height = this.bodyHeight = Default_Height;
		}
		
		this.setBeamOffset(this.width * Default_Beam_Offset / Default_Width);
		
		this.referenceSize.width = Reference_Width;
		this.referenceSize.height = Reference_Height;
		this.tmp = CGPoint.zero();
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
		return LaserGun.Anim_Wait;
	}
	
	public void turnOn() {
		this.turnOn(false);
	}
	
	public void turnOn(boolean force) {
		if (!this.isOn() || force) {		
			this.turningOn = true;
			CCAnimate animateStart = CCAnimate.action(this.animationList.get(Anim_Firing), false);
			CCCallFunc callStartEnded = CCCallFunc.action(this, "turnedOn");
			CCSequence sequence = CCSequence.actions(animateStart, callStartEnded);
			this.sprite.runAction(sequence);
		}
	}
	
	public void turnedOn() {
		this.turningOn = false;
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_On), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.sprite.runAction(repeat);
		this.setOn(true);		
	}
	
	public void turnOff() {
		this.turnOff(false);
	}
	
	public void turnOff(boolean force) {
		if (this.isOn() || force) {			
			CCAnimate animateStart = CCAnimate.action(this.animationList.get(Anim_Firing), false);		
			CCCallFunc callStartEnded = CCCallFunc.action(this, "turnedOff");
			CCSequence sequence = CCSequence.actions(animateStart.reverse(), callStartEnded);
			this.setOn(false);
			this.sprite.runAction(sequence);
		}
	}
	
	public void turnedOff() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.sprite.runAction(repeat);
		this.setOn(false);
	}

	public void trigger(Object source, String data) {
		if (this.isOn()) {
			this.turnOff();
		}
		else {
			this.turnOn();
		}		
	}
	
	public void setStartOn(boolean startOn) {
		this.startOn = startOn;
	}
	
	public boolean getStartOn() {
		return this.startOn;
	}
	
	public void initanimation() {
		this.turnedOff();
		if (this.startOn) {
			this.turnOn();
		}
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTarget() {
		return target;
	}
	
	private ArrayList<LaserBeam> getBeam()	{
		if (this.beam == null) {
			if (this.target != null && this.target != "") {
				this.beam = new ArrayList<LaserBeam>();
				
				CGPoint startFire = this.getSourcePoint();
				for (ITrigerable targetBeam : Level.currentLevel.getTrigerables(this.target)) {					
					LaserBeam b = SlimeFactory.LaserBeam.create(startFire, targetBeam.getPosition(), false);
					b.setSourceItem(this);
					b.setTargetItem(targetBeam);
					this.beam.add(b);
				}
			}
		}
		
		return this.beam;
	}
	
	public CGPoint getSourcePoint() {
		// (x'-xc) = Kc*(x-xc) - Ks*(y-yc)
		// (y'-yc) = Ks*(x-xc) + Kc*(y-yc)
		float radAngle = - ccMacros.CC_DEGREES_TO_RADIANS(this.angle);
		float xFire = (float) (Math.cos(radAngle)*((this.position.x - this.width / 2f + this.getBeamOffset()) - this.position.x)) + this.position.x;
		float yFire = (float) (Math.sin(radAngle)*((this.position.x - this.width / 2f + this.getBeamOffset()) - this.position.x)) + this.position.y;
		this.tmp.x = xFire;
		this.tmp.y = yFire;
		return this.tmp;
	}
	
	@Override
	public void render(float delta) {
		if (this.getBeam() != null) {			
			for (LaserBeam b : this.getBeam()) {
				if (this.isOn()) {
					b.switchOn();
					b.refreshBeam();
				}
				else {
					b.switchOff();
				}
			}						
		}
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	@Override
	public void triggerOn(Object source) {
		this.turnOn(true);
	}

	@Override
	public void triggerOff(Object source) {
		this.turnOff(true);
	}

	public float getBeamOffset() {
		return beamOffset;
	}

	public void setBeamOffset(float beamOffset) {
		this.beamOffset = beamOffset;
	}
}
