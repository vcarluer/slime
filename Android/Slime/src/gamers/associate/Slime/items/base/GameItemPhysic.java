package gamers.associate.Slime.items.base;

import gamers.associate.Slime.game.ContactInfo;

import java.util.ArrayList;

import org.cocos2d.config.ccMacros;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;

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
	
	public GameItemPhysic(float x, float y, float width, float height, World world, float worldRatio) {		
		super(x, y, width, height, world, worldRatio);		
		this.bodyWidth = this.width;
		this.bodyHeight = this.height;
		this.contacts = new ArrayList<ContactInfo>();
	}
	
	@Override
	public void destroy() {		
		if (this.body != null) {
			this.world.destroyBody(this.body);
		}
		
		this.world = null;
		this.body = null;
		super.destroy();
	}
	
	public void disablePhysic() {
		this.isPhysicDisabled = true;
	}
	
	public void destroyBodyOnly() {
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
				float x = this.body.getPosition().x * this.worldRatio;
				float y = this.body.getPosition().y * this.worldRatio;
				float rotation = -1.0f * ccMacros.CC_RADIANS_TO_DEGREES(this.body.getAngle());
				this.sprite.setPosition(x, y);			
				this.sprite.setRotation(rotation);
				this.position.set(x, y);
				this.angle = rotation;
			}
			
			// super.render(delta);
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
}
