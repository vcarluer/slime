package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.ISelectable;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccMacros;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGAffineTransform;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class SlimyJump extends Slimy implements ISelectable {

	public static float Default_Powa = 2.0f;
	public static float Max_Impulse = 10f;
	
	private CGPoint target;
	private Vector2 targetImpulse;
	private Vector2 worldImpulse;
	private boolean selected;
	private CGPoint worldSelect;
	private float powa;
	private CGRect scaledRect;
	
	public SlimyJump(float x, float y, float width, float height, World world,
			float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		
		this.powa = Default_Powa;
		this.target = CGPoint.getZero();
		this.targetImpulse = new Vector2();
		this.worldImpulse = new Vector2();
		this.scaledRect = CGRect.zero();
	}
	
	public void selectionMove(CGPoint gameReference) {
		if (this.selected) {			
			this.computeTarget(gameReference);
		}
	}
	
	protected void computeTarget(CGPoint gameTouch) {
		if (this.selected) {
			this.targetImpulse.x = (this.getPosition().x - gameTouch.x) * this.powa;
			this.targetImpulse.y = (this.getPosition().y - gameTouch.y) * this.powa;
			
			this.target.x = this.getPosition().x + this.targetImpulse.x;
			this.target.y = this.getPosition().y + this.targetImpulse.y;
			
			this.worldSelect = gameTouch;
		}
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.GameItem#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl) {		
		super.draw(gl);
		if (this.selected && this.target != null) {
			/*gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glLineWidth(2.0f);*/
			gl.glEnable(GL10.GL_LINE_SMOOTH);
			gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);				        
			CGPoint spawnPoint =  this.getPosition();			
			CCDrawingPrimitives.ccDrawLine(gl, spawnPoint, this.target);
			// Not compatible with glLineWidth > 1; 
			gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
			gl.glLineWidth(3.0f);
			CCDrawingPrimitives.ccDrawLine(gl, spawnPoint, this.worldSelect);
			// CGSize s = CCDirector.sharedDirector().winSize();
			gl.glEnable(GL10.GL_LINE_SMOOTH);
			gl.glLineWidth(1.0f);
            gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
			CCDrawingPrimitives.ccDrawCircle(gl, this.getPosition(), 50, ccMacros.CC_DEGREES_TO_RADIANS(90), 50, true);						
		}
		
		if (this.scaledRect != null) {
			// Drawing selection rectangle
			gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glLineWidth(2.0f);
			gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);            
			
			float x1 = this.scaledRect.origin.x;
			float y1 = this.scaledRect.origin.y;
			float x2 = this.scaledRect.origin.x + CGRect.width(this.scaledRect);
			float y2 = this.scaledRect.origin.y + CGRect.height(this.scaledRect);
			
			CGPoint vertices[] = {
					this.scaledRect.origin, 
					CGPoint.ccp(x2, y1),
					CGPoint.ccp(x2, y2),
					CGPoint.ccp(x1, y2)
					};
            CCDrawingPrimitives.ccDrawPoly(gl, vertices, 4, true);
		}
	}
	
	public boolean trySelect(CGPoint gameReference) {
		if (!this.isDead) {				
			if (this.isInSlimy(gameReference)) {			
				this.select();
				this.computeTarget(gameReference);
			}
		}
		
		return this.selected;
	}
	
	public void select() {
		this.selected = true;		
	}
	
	public void unselect() {
		if (this.selected) {
			this.selected = false;			
		}
	}
	
	private boolean isInSlimy(CGPoint gameTarget) {				
		return CGRect.containsPoint(this.scaledRect, gameTarget);						
	}		
		
	public boolean isSelected() {
		return this.selected;
	}
	
	@Override
	public void selectionStop(CGPoint gameReference) {
		if (this.selected) {
			this.computeTarget(gameReference);

			Vector2 pos = this.getBody().getPosition();
			this.worldImpulse.x = (this.targetImpulse.x  / this.worldRatio);
			this.worldImpulse.y = (this.targetImpulse.y / this.worldRatio);
			if (this.worldImpulse.len() > (Max_Impulse)) {
				this.worldImpulse.nor().mul(Max_Impulse);
			}
			
			this.getBody().applyLinearImpulse(this.worldImpulse, pos);
		}		
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.custom.Slimy#render(float)
	 */
	@Override
	public void render(float delta) {		
		super.render(delta);
		float zoom = Level.currentLevel.getCameraManager().getCurrentZoom();
		if (zoom != 0) {
			float ratio = 1;
			CGRect spriteRect = this.getRect();
			if (zoom < 1) {
				ratio = 1 / zoom;
				float baseX1 = spriteRect.origin.x;
				float baseY1 = spriteRect.origin.y;			
				
				float baseW = CGRect.width(spriteRect);
				float baseH = CGRect.height(spriteRect);
				float scaledW = baseW * ratio; 
				float scaledH = baseH * ratio;
				
				float targX1 = baseX1 - (scaledW - baseW) / 2;
				float targY1 = baseY1 - (scaledH - baseH) / 2;
				
				this.scaledRect.set(targX1, targY1, scaledW, scaledH);
			}
			else {
				this.scaledRect = spriteRect;
			}									
		}
	}
}
