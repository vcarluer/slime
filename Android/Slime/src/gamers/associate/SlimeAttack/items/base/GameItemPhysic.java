package gamers.associate.SlimeAttack.items.base;

import gamers.associate.SlimeAttack.game.ContactInfo;

import java.util.ArrayList;

import org.cocos2d.config.ccMacros;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract class GameItemPhysic extends GameItemPhysicFx {
	public static short Category_Level = 0x0001;
	public static short Category_InGame = 0x0002;
	public static short Category_OutGame = 0x0003;
		
	protected Body body; 
	protected float bodyWidth;
	protected float bodyHeight;	
	protected ArrayList<ContactInfo> contacts;
	protected boolean noStick;
	protected boolean IsAllSensor;
	protected boolean isPhysicDisabled;
	private boolean bodyCategorySwitched;
	private Vector2 transformVector;
	private boolean forcePosition;
	
	public GameItemPhysic(float x, float y, float width, float height, World world, float worldRatio) {
		this(null, x, y, width, height, world, worldRatio);
	}
	
	public GameItemPhysic(String name, float x, float y, float width, float height, World world, float worldRatio) {		
		super(name, x, y, width, height, world, worldRatio);		
		this.bodyWidth = this.width;
		this.bodyHeight = this.height;
		this.contacts = new ArrayList<ContactInfo>();
		this.transformVector = new Vector2();
	}
	
	@Override
	public void destroy() {		
		this.destroyBody();
		this.world = null;
		super.destroy();
	}
	
	public void disablePhysic() {
		this.isPhysicDisabled = true;
	}
	
	public void destroyBody() {
		if (this.body != null) {
			this.world.destroyBody(this.body);
		}

		this.body = null;
	}
	
	@Override
	public void initItem() {
		super.initItem();
		this.initBody();
	}
	
	protected abstract void initBody();
	
	public Body getBody() {
		return this.body;
	}
	
	@Override
	public void render(float delta) {
		if (!this.isPhysicDisabled) {
			this.handleContacts();
			
			if (this.sprite != null && this.body != null) {
				if (this.body.getType() == BodyType.StaticBody || this.body.getType() == BodyType.KinematicBody 
						|| (this.body.getType() == BodyType.DynamicBody && this.isForcePosition())) {
					// Set position based on sprite
					super.render(delta);
					if (this.positionChanged || this.angleChanged) {
						this.transformVector.x = this.getPosition().x / this.worldRatio;
						this.transformVector.y = this.getPosition().y / this.worldRatio;
						float rotation = -1.0f * ccMacros.CC_DEGREES_TO_RADIANS(this.getAngle());
						this.body.setTransform(this.transformVector, rotation);
					}									
				} else {
					float x = this.body.getPosition().x * this.worldRatio;
					float y = this.body.getPosition().y * this.worldRatio;
					float rotation = -1.0f * ccMacros.CC_RADIANS_TO_DEGREES(this.body.getAngle());
					this.sprite.setPosition(x, y);			
					this.sprite.setRotation(rotation);
					this.position.set(x, y);
					this.angle = rotation;
				}				
			}
		}
	}
	
	public void addContact(Object with, WorldManifold manifold) {
		if (with instanceof GameItemPhysic) {
			GameItemPhysic item = (GameItemPhysic)with;
			ContactInfo contact = new ContactInfo();
			contact.setContactWith(item);
			contact.setManifold(manifold);
			this.contacts.add(contact);
		}
	}
	
	protected void handleContacts() {		
		if (this.contacts.size() > 0) {
			for(ContactInfo contact : this.contacts) {
				this.handleContact(contact);
			}
			
			this.contacts.clear();
		}
	}
	
	protected void handleContact(ContactInfo contact) {		
	}
	
	@Override
	public void setAngle(float angle) {
		super.setAngle(angle);
		if (this.body != null) {
			float radAngle = -1.0f * ccMacros.CC_DEGREES_TO_RADIANS(this.angle);
			this.body.setTransform(this.body.getPosition(), radAngle);
		}
	}
	
	public void handleSpecialRemove() {		
	}

	/**
	 * @return the noStick
	 */
	public boolean isNoStick() {
		return noStick;
	}

	/**
	 * @param noStick the noStick to set
	 */
	public void setNoStick(boolean noStick) {
		this.noStick = noStick;
	}
	
	public boolean getNoStick() {
		return this.noStick;
	}

	/**
	 * @return the isAllSensor
	 */
	public boolean isIsAllSensor() {
		return IsAllSensor;
	}

	/**
	 * @param isAllSensor the isAllSensor to set
	 */
	public void setIsAllSensor(boolean isAllSensor) {
		IsAllSensor = isAllSensor;
	}
	
	protected void swithBodyCategory() {
		if (this.body != null && !this.bodyCategorySwitched) {
			this.bodyCategorySwitched = true;
			Filter filter = new Filter();
			
			filter.categoryBits = GameItemPhysic.Category_OutGame;
			filter.maskBits = GameItemPhysic.Category_Level;					
			filter.groupIndex = -1;
			for(Fixture fix : this.body.getFixtureList()) {
				// Change fixture shape here?
				fix.setFilterData(filter);
				fix.setRestitution(0f);
				fix.setFriction(1.0f);
				fix.setDensity(10f);
				this.body.resetMassData();
			}
		}
	}

	public boolean isForcePosition() {
		return forcePosition;
	}

	public void setForcePosition(boolean forcePosition) {
		this.forcePosition = forcePosition;
	}
}
