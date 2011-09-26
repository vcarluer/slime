package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.SpriteType;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccMacros;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class LaserBeam extends GameItemPhysic {
	private static float Default_Height = 5f;
	private CGPoint source;
	private CGPoint target;
	private boolean isOn;

	public LaserBeam(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);		
		this.spriteType = SpriteType.NONE;		
				
		if (this.height == 0) {
			this.bodyHeight = this.height = Default_Height;			
		}		
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
	
	private void refresh() {
		if (this.source != null && this.target != null) {
			this.bodyWidth = this.width = CGPoint.ccpDistance(this.source, this.target);
			this.setAngle(ccMacros.CC_RADIANS_TO_DEGREES(CGPoint.ccpAngle(this.source, this.target)));
			this.setPosition(CGPoint.ccpMidpoint(this.source, this.target));
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
				((Slimy)contact.getContactWith()).burn();
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
			gl.glLineWidth(this.height);								
			CCDrawingPrimitives.ccDrawLine(gl, this.source, this.target);
		}
		
		super.draw(gl);
	}
}