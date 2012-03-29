package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Util;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.IElectrificable;
import gamers.associate.Slime.items.base.ITrigerable;
import gamers.associate.Slime.items.base.SpriteType;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.config.ccMacros;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class TeslaCoil extends GameItemPhysic  implements ITrigerable {
	public static String Anim_Wait = "tesla-coil-wait";
	public static String Anim_Ligthning = "tesla-coil-lightning";
	
	public static float Default_Width = 26f;
	public static float Default_Height = 45f;
	
	public static float Default_StrikeDistance = 100f;
	
	private static float Reference_Width = 26f;
	private static float Reference_Height = 45f;
	
	private static float Default_Beam_Offset = 38f;
	
	private float beamOffset;
	private CGPoint tmp;
	
	private boolean isOn;
	
	private boolean startOn;
	private float strikeDistance;	
	
	private CGPoint tmpTarget;
	private CGPoint ref;	
	
	private static float LigthningHeightRatio = 32f / 171f;

	public TeslaCoil(float x, float y, float width, float height, World world,
			float worldRatio, boolean startOn, float strikeDistance) {
		super(x, y, width, height, world, worldRatio);
		
		this.ref = CGPoint.make(1, 0);
		this.startOn = startOn;
		this.setStrikeDistance(strikeDistance);
		
		this.spriteType = SpriteType.ANIM_SCALE;
		
		this.zOrder = Level.zMid;
		this.noStick = true;
		
		if (width == 0 && height == 0) {
			this.width = this.bodyWidth = Default_Width;
			this.height = this.bodyHeight = Default_Height;
		}
		
		this.beamOffset = this.width * Default_Beam_Offset / Default_Width;
		
		this.referenceSize.width = Reference_Width;
		this.referenceSize.height = Reference_Height;
		this.tmp = CGPoint.zero();
		this.tmpTarget = CGPoint.zero();
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

	@Override
	protected String getReferenceAnimationName() {
		return Anim_Wait;
	}
	
	public void turnOn() {
		if (!this.isOn) {						
			CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Ligthning), false);
			CCRepeatForever repeat = CCRepeatForever.action(animate);
			this.sprite.runAction(repeat);
			this.isOn = true;
		}
	}
	
	public void turnOff() {
		if (this.isOn) {			
			CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
			CCRepeatForever repeat = CCRepeatForever.action(animate);
			this.sprite.runAction(repeat);
			this.isOn = false;
		}
	}

	public boolean isStartOn() {
		return startOn;
	}

	public void setStartOn(boolean startOn) {
		this.startOn = startOn;
	}
	
	public void initState() {
		this.turnOff();
		if (this.startOn) {
			this.turnOn();
		}
	}
	
	public CGPoint getSourcePoint() {
		// (x'-xc) = Kc*(x-xc) - Ks*(y-yc)
		// (y'-yc) = Ks*(x-xc) + Kc*(y-yc)
		float radAngle = - ccMacros.CC_DEGREES_TO_RADIANS(this.angle);
		float xFire = - (float) (Math.sin(radAngle)*((this.position.y - this.height / 2f + this.beamOffset) - this.position.y)) + this.position.x;
		float yFire = (float) (Math.cos(radAngle)*((this.position.y - this.height / 2f + this.beamOffset) - this.position.y)) + this.position.y;
		this.tmp.x = xFire;
		this.tmp.y = yFire;
		return this.tmp;
	}
	
	@Override
	public void render(float delta) {
		this.tmpTarget.set(0, 0);
		if (this.isOn) {
			for(IElectrificable elec : Level.currentLevel.getElectrificables()) {
				if (elec != null) {
					CGPoint source = this.getSourcePoint();
					if (CGPoint.ccpDistance(elec.getPosition(), source) <= this.getStrikeDistance()) {
						this.strike(this, elec);
					}
				}
			}					
		}
	}
	
	private Lightning lightning;
	
	public void strike(GameItem source, IElectrificable elect) {				
		if (this.lightning == null) {
			if (elect.isActive()) {
				elect.electrify();
				this.lightning = SlimeFactory.Lightning.create(source, elect, Lightning.DefaultLife);
			}			
		} else {
			if (this.lightning.isEnded()) {
				this.lightning = null;
			}
		}
	}

	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
		
		if (this.isOn) {
			gl.glEnable(GL10.GL_LINE_SMOOTH);
			gl.glLineWidth(1.0f);
            gl.glColor4f(0f, 0f, 1.0f, 0.05f);			
            Util.ccDrawCirclePlain(gl, this.getPosition(), this.strikeDistance, ccMacros.CC_DEGREES_TO_RADIANS(90), 50);
		}
	}

	public float getStrikeDistance() {
		return strikeDistance;
	}

	public void setStrikeDistance(float strikeDistance) {
		this.strikeDistance = strikeDistance;
	}
}
