package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.ITrigerable;
import gamers.associate.Slime.items.base.SpriteType;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccMacros;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LaserBeam extends GameItemPhysic {
	private static float Default_Height = 5f;
	private CGPoint source;
	private CGPoint target;
	private boolean isOn;
	private CGPoint ref;
	private CGPoint diff;
	private ITrigerable targetItem;
	private LaserGun sourceItem;
	private CGPoint tmp;
	
	public LaserBeam(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);		
		this.spriteType = SpriteType.NONE;		
				
		if (this.height == 0) {
			this.bodyHeight = this.height = Default_Height;			
		}		
		
		// Normalized ref
		this.ref = CGPoint.make(1, 0);		
		
		this.diff = CGPoint.zero();
		this.tmp = CGPoint.zero();
	}
	
	public void setSource(CGPoint source) {
		this.source = source;
		this.refresh();
	}
	
	public void setTarget(CGPoint target) {
		this.target = target;
		this.refresh();
	}
	
	public void setOn(boolean isOn) {
		this.isOn = isOn;
		if (this.body != null) {
			this.body.setActive(this.isOn);
		}
	}
	
	public void refreshBeam() {
		if (this.sourceItem != null) {
			this.source = this.sourceItem.getSourcePoint();
		}
		
		if (this.targetItem != null) {
			this.target = this.targetItem.getPosition();
		}
		
		this.refresh();
	}
	
	private void refresh() {
		if (this.source != null && this.target != null) {
			this.tmp.x = this.source.x - this.target.x;
			this.tmp.y = this.source.y - this.target.y;			
			this.bodyWidth = this.width = CGPoint.ccpLength(this.tmp);
			
			// in mid point. Strange algo but optimized
			this.position.x = (this.source.x + this.target.x) * 0.5f;
			this.position.y = (this.source.y + this.target.y) * 0.5f;
			this.setPosition(this.position);

			this.diff.x = this.target.x - this.source.x;
			this.diff.y = this.target.y - this.source.y;
					
			// Calculate signed angle (optimized)
			float ln = CGPoint.ccpLength(this.diff);
			this.tmp.x = this.diff.x / ln;
			this.tmp.y = this.diff.y / ln;
	        
			float dot = this.ref.x * this.tmp.x + this.ref.y * this.tmp.y;
	        float angle = (float)Math.atan2(this.ref.x * this.tmp.y - this.ref.y * this.tmp.x, dot);
	        if( Math.abs(angle) < SlimeFactory.kCGPointEpsilon ) angle = 0.f;
			
			float newAngle = - ccMacros.CC_RADIANS_TO_DEGREES(angle);			
			
			this.setAngle(newAngle);			
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
			fixtureDef.density = 0.0f;
			fixtureDef.friction = 0.0f;
    		fixtureDef.restitution = 0f;
    		fixtureDef.isSensor = true;
    		this.IsAllSensor = true;
    		
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    		this.body.createFixture(fixtureDef);
		}				
		
		this.refresh();
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo contact) {	
		if (this.isOn) {				
			if (contact.getContactWith() instanceof Slimy) {
				((Slimy)contact.getContactWith()).slice();
			}						
		}
		
		super.handleContact(contact);
	}
	
	public void switchOn() {
		this.isOn = true;
		if (this.body != null) {
			this.body.setActive(true);
		}
	}
	
	public void switchOff() {
		this.isOn = false;
		if (this.body != null) {
			this.body.setActive(false);
		}
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItem#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl) {
		if (this.isOn) {
			gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glColor4f(1.0f, 0.043f, 0.149f, 0.63f);
			gl.glLineWidth(this.height * Level.currentLevel.getCameraManager().getCurrentZoom());								
			CCDrawingPrimitives.ccDrawLine(gl, this.source, this.target);
		}
		
		if (Level.DebugMode) {
			CGPoint spawnPoint =  this.getPosition();
			gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);			
			gl.glPointSize(5.0f);						
			CCDrawingPrimitives.ccDrawPoint(gl, spawnPoint);
			gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
			CCDrawingPrimitives.ccDrawPoint(gl, this.target);
			CCDrawingPrimitives.ccDrawPoint(gl, this.source);			
		}
		
		super.draw(gl);
	}

	public ITrigerable getTargetItem() {
		return targetItem;
	}

	public void setTargetItem(ITrigerable targetItem) {
		this.targetItem = targetItem;
	}

	public LaserGun getSourceItem() {
		return sourceItem;
	}

	public void setSourceItem(LaserGun sourceItem) {
		this.sourceItem = sourceItem;
	}
}