package gamers.associate.Slime.items;

import java.util.ArrayList;

import org.cocos2d.config.ccMacros;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameItemPhysic extends GameItemPhysicFx {
	public static short Category_Static = 0x0001;
	public static short Category_InGame = 0x0002;
	public static short Category_OutGame = 0x0003;
		
	protected Body body; 
	protected float bodyWidth;
	protected float bodyHeight;	
	protected ArrayList<GameItemPhysic> contacts;
	
	public GameItemPhysic(float x, float y, float width, float height, World world, float worldRatio) {		
		super(x, y, width, height, world, worldRatio);		
		this.bodyWidth = this.width;
		this.bodyHeight = this.height;
		this.contacts = new ArrayList<GameItemPhysic>();
	}
	
	@Override
	public void destroy() {		
		this.world.destroyBody(this.body);
		this.world = null;
		this.body = null;
		super.destroy();
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
		this.handleContacts();
		
		if (this.sprite != null && this.body != null) {			
			this.sprite.setPosition(this.body.getPosition().x * this.worldRatio, this.body.getPosition().y * this.worldRatio);			
			this.sprite.setRotation(-1.0f * ccMacros.CC_RADIANS_TO_DEGREES(this.body.getAngle()));
		}
		
		super.render(delta);
	}
	
	public void addContact(Object with) {
		if (with instanceof GameItemPhysic) {
			GameItemPhysic item = (GameItemPhysic)with;
			this.contacts.add(item);
		}
	}
	
	protected void handleContacts() {		
		for(GameItemPhysic item : this.contacts) {
			this.handleContact(item);
		}
		
		this.contacts.clear();
	}
	
	protected void handleContact(GameItemPhysic item) {		
	}
	
	@Override
	public void setAngle(float angle) {
		super.setAngle(angle);
		float radAngle = -1.0f * ccMacros.CC_DEGREES_TO_RADIANS(this.angle);
		this.body.setTransform(this.body.getPosition(), radAngle);
	}
}
