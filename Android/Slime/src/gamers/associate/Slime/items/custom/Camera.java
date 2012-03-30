package gamers.associate.Slime.items.custom;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.config.ccMacros;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Util;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.ITrigerable;
import gamers.associate.Slime.items.base.SpriteType;

public class Camera extends GameItemPhysic {
	public static String Anim_Wait = "camera";
	public static float Default_Width = 56f;
	public static float Default_Height = 25f;
	
	public static float Default_fov = 45f;
	public static float Default_distance = 200f;
	
	private static float Reference_Width = 56f;
	private static float Reference_Height = 25f;
	
	private boolean isOn;	
	private List<ITrigerable> targets;
	private float fov;
	private float viewDistance;
	
	private boolean startOn;
	private String targetName;
	
	private CGPoint targetPoint;
	private CGPoint zero;
	private CGPoint v1;
	private CGPoint v2;
	
	private boolean spoted;
	private boolean vComputed;
	
	public Camera(float x, float y, float width, float height, World world,
			float worldRatio, String targetName, boolean startOn, float fov, float viewDistance) {
		super(x, y, width, height, world, worldRatio);
		// temp scale direct 
		this.spriteType = SpriteType.SINGLE_SCALE_DIRECT;
		
		this.setTargetName(targetName);
		this.startOn = startOn;
		this.setFov(Default_fov);
		this.setViewDistance(Default_distance);
		
		if (fov > 0) {
			this.setFov(fov);
		}
		
		if (viewDistance > 0) {
			this.viewDistance = viewDistance;			
		}
		
		if (width == 0 && height == 0) {
			this.width = this.bodyWidth = Default_Width;
			this.height = this.bodyHeight = Default_Height;
		}		
		
		this.referenceSize.width = Reference_Width;
		this.referenceSize.height = Reference_Height;
		
		this.targetPoint = CGPoint.zero();
		this.zero = CGPoint.zero();
		this.v1 = CGPoint.zero();
		this.v2 = CGPoint.zero();
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
		return Camera.Anim_Wait;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public boolean isStartOn() {
		return startOn;
	}

	public void setStartOn(boolean startOn) {
		this.startOn = startOn;
	}
	
	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
		
		if (this.isOn && this.vComputed) {			
			gl.glEnable(GL10.GL_LINE_SMOOTH);
			gl.glLineWidth(1.0f);
			if (this.spoted)	 {
				gl.glColor4f(1.0f, 0f, 0f, 0.05f);
			} else {
				gl.glColor4f(0f, 0f, 1.0f, 0.05f);
			}
	        			
	        CGPoint v0 = this.getPosition();
			Util.ccDrawTrianglePlain(gl, v0, CGPoint.ccpAdd(v0, this.v1), CGPoint.ccpAdd(v0, this.v2));
		}		
	}

	@Override
	public void render(float delta) {		
		super.render(delta);
		boolean previousSpoted = this.spoted;
		if (this.isOn) {
			float rad = -1.0f * ccMacros.CC_DEGREES_TO_RADIANS(this.angle);
			this.targetPoint = CGPoint.ccpMult(CGPoint.ccpForAngle(rad), this.viewDistance);
			
			float radAngle = -1.0f * ccMacros.CC_DEGREES_TO_RADIANS(this.fov);
			this.v1 = CGPoint.ccpRotateByAngle(this.targetPoint, this.zero, radAngle);
			this.v2 = CGPoint.ccpRotateByAngle(this.targetPoint, this.zero, - radAngle);
			this.vComputed = true;
			
			this.spoted = false;
			for(Slimy check : Level.currentLevel.aliveList()) {				
				if (check != null) {				
					boolean spot = Util.inTriangle(this.getPosition(), v1, v2, check.getPosition());
					if (spot) {
						this.spoted = spot;
					}
				}
			}						
			
			if (previousSpoted != this.spoted) {
				this.getTargets();			
				if (this.targets != null) {								
					if (this.spoted) {						
						for(ITrigerable target : this.targets) {							
							target.trigger(this, LaserGun.DIRECT_SWITCH);
						}
					}								
				}
			}
		}
	}		
	
	private void getTargets() {
		if (this.targets == null) {
			if (this.getTargetName() != null && this.getTargetName() != "") {
				this.targets = Level.currentLevel.getTrigerables(this.getTargetName());
			}
		}				
	}

	public void initState() {
		this.setOn(this.isStartOn());
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}

	public float getViewDistance() {
		return viewDistance;
	}

	public void setViewDistance(float viewDistance) {
		this.viewDistance = viewDistance;
	}
	
	public void rotateBy(float time, float rotationAngle) {
		CCRotateBy rot = CCRotateBy.action(time, rotationAngle);
		CCRotateBy rot2 = CCRotateBy.action(time, -rotationAngle);
		CCSequence seq = CCSequence.actions(rot, rot.reverse(), rot2, rot2.reverse());
		CCRepeatForever repeat = CCRepeatForever.action(seq);
		this.getSprite().runAction(repeat);
	}
}