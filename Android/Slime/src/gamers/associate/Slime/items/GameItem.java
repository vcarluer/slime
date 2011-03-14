package gamers.associate.Slime.items;

import java.util.UUID;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.types.CGPoint;

public class GameItem {
	protected UUID id;
	protected CGPoint position;
	protected float angle;
	protected float width;
	protected float height;
	protected boolean isPaused;
	
	public GameItem(float x, float y, float width, float height) {		
		this.id = UUID.randomUUID();		
		this.position = new CGPoint();
		this.position.x = x;
		this.position.y = y;
		this.angle = 0f;		
		this.width = width;
		this.height = height;		
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

	public void setPosition(CGPoint position) {
		this.position = position;
	}
}
