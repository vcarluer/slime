package gamers.associate.SlimeAttack.items.base;

import java.util.UUID;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class GameItem {
	protected UUID id;
	protected CGPoint position;
	protected float angle;
	protected float width;
	protected float height;
	protected boolean isPaused;
	protected boolean isActive = true;
	private String name;
	
	public GameItem(float x, float y, float width, float height) {
		this(null, x, y, width, height);
	}
	
	public GameItem(String name, float x, float y, float width, float height) {		
		this.id = UUID.randomUUID();		
		this.position = CGPoint.make(x, y);		
		this.angle = 0f;		
		this.width = width;
		this.height = height;
		if (name != null) {
			this.name = name;
		} else {
			this.name = "";
		}		
	}
	
	public UUID getId() {
		return this.id;
	}
	
	public void destroy() {		
	}
	
	public void render(float delta) {		
	}
	
	public void draw(GL10 gl) {
	}
	
	public CGPoint getPosition() {
		return this.position;
	}
	
	public void setPause(boolean value) {
		if (value) {			
			if (!this.isPaused) {
				this.pause();
			}
		}
		else
		{
			if (this.isPaused) {
				this.resume();
			}
		}
		
		this.isPaused = value;
	}
	
	protected void pause() {	
	}
	
	protected void resume() {		
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public float getAngle() {
		return this.angle;
	}

	public void setPosition(CGPoint position) {
		this.position = position;
	}
	
	public void initItem() {		
	}
	
	public CGRect getRect() {
		return CGRect.make(this.position.x - this.width / 2, this.position.y - this.height / 2, this.width, this.height);
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public void setActive(boolean active) {
		this.isActive = active;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public float getWidth() {
		return this.width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
}
