package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttackLite.R;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.game.Util;
import gamers.associate.SlimeAttack.items.base.GameItemPhysic;
import gamers.associate.SlimeAttack.items.base.ITrigerable;
import gamers.associate.SlimeAttack.items.base.SpriteType;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccMacros;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

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
	private CGPoint v1;
	private CGPoint v2;
	
	private boolean spoted;
	private boolean vComputed;
	
	private float rotateTime;
	private float rotateAngle;
	private float baseAngle;
	private boolean baseAngleDefined;
	
	private CGPoint tmp1;
	private CGPoint tmp2;
	private int miniTimer = 0;
	
	public Camera(float x, float y, float width, float height, World world,
			float worldRatio, String targetName, boolean startOn, float fov, float viewDistance,
			float rotateTime, float rotateAngle) {
		super(x, y, width, height, world, worldRatio);
		// temp scale direct 
		this.spriteType = SpriteType.SINGLE_SCALE_DIRECT;
		this.zOrder = Level.zTop;
		
		this.setTargetName(targetName);
		this.startOn = startOn;
		this.setFov(Default_fov);
		this.setViewDistance(Default_distance);
		this.setRotateTime(rotateTime);
		this.setRotateAngle(rotateAngle);
		
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
		this.v1 = CGPoint.zero();
		this.v2 = CGPoint.zero();
		this.tmp1 = CGPoint.zero();
		this.tmp2 = CGPoint .zero();
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
				SlimeFactory.triggerZoneAlertColor(gl);
				
			} else {
				SlimeFactory.triggerZoneColor(gl);
			}
	        			
	        CGPoint v0 = this.getPosition();
	        
	        this.tmp1.x = this.v1.x + v0.x;
	        this.tmp1.y = this.v1.y + v0.y;
	        this.tmp2.x = this.v2.x + v0.x;
	        this.tmp2.y = this.v2.y + v0.y;
			Util.ccDrawTrianglePlain(gl, v0, this.tmp1, this.tmp2);
		}		
	}

	@Override
	public void render(float delta) {		
		super.render(delta);
		boolean previousSpoted = this.spoted;
		if (this.isOn) {
			float rad = -1.0f * ccMacros.CC_DEGREES_TO_RADIANS(this.angle);
			this.tmp1.x = (float) Math.cos(rad);
			this.tmp1.y = (float) Math.sin(rad);
			
			this.targetPoint.x = this.tmp1.x * this.viewDistance;
			this.targetPoint.y = this.tmp1.y * this.viewDistance;
			
			float radAngle = -1.0f * ccMacros.CC_DEGREES_TO_RADIANS(this.fov);
												
	        float t = this.targetPoint.x;
	        float cosa1 = (float)Math.cos(radAngle);
	        float sina1 = (float)Math.sin(radAngle);
	        this.v1.x = t*cosa1 - this.targetPoint.y*sina1;
	        this.v1.y = t*sina1 + this.targetPoint.y*cosa1;	        
	        float cosa2 = (float)Math.cos(-radAngle);
	        float sina2 = (float)Math.sin(-radAngle);
	        this.v2.x = t*cosa2 - this.targetPoint.y*sina2;
	        this.v2.y = t*sina2 + this.targetPoint.y*cosa2;	        
			
			this.vComputed = true;
			
			this.spoted = false;
			for(Slimy check : Level.currentLevel.aliveList()) {				
				if (check != null) {				
					this.spoted = Util.inTriangle(this.getPosition(), v1, v2, check.getPosition());
					if (this.spoted && miniTimer == 0) {
						//Sounds.playMusic(R.raw.bipcamera,true);
						Sounds.playEffect(R.raw.bipcamera);
						miniTimer++;
					}else
					{
						if(miniTimer==30){
							miniTimer = 0;
						}else{
							miniTimer++;
						}
					}
				}
			}						
			
			if (previousSpoted != this.spoted) {
				this.getTargets();			
				if (this.targets != null) {					
					for(ITrigerable target : this.targets) {		
						if (this.spoted) {
							target.triggerOn(this);
						} else {
							target.triggerOff(this);
						}
					}							
				}
			}
			
			this.handleRotation(delta);
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
		this.setRotateTime(time);
		this.setRotateAngle(rotationAngle);	
	}
	
	private void handleRotation(float delta) {
		if (this.getRotateTime() > 0 && this.getRotateAngle() != 0) {
			float realAngle = this.rotateAngle * 2;
			if (!this.baseAngleDefined) {
				this.rotateAngleCurrent = this.rotateAngle;
				this.baseAngle = this.angle - this.rotateAngle;
				this.baseAngleDefined = true;
			}
			
			float deltaAngle = this.getRotateAngle() / this.getRotateTime() * delta;
			this.rotateAngleCurrent += deltaAngle;
			boolean turn = false;
			if (Math.abs(this.rotateAngleCurrent) >= Math.abs(realAngle)) {
				rotateAngleCurrent = realAngle;
				turn = true;
			}
			
			float newAngle = this.baseAngle + this.rotateAngleCurrent;
			this.setAngle(newAngle);
			
			if (turn) {
				this.rotateAngle *= -1;
				this.baseAngle = this.angle;
				this.rotateAngleCurrent = 0;
			}
		}
	}
	
	private float rotateAngleCurrent;

	public float getRotateTime() {
		return rotateTime;
	}

	public void setRotateTime(float rotateTime) {
		this.rotateTime = rotateTime;
	}

	public float getRotateAngle() {
		return rotateAngle;
	}

	public void setRotateAngle(float rotateAngle) {
		this.rotateAngle = rotateAngle;
	}
}