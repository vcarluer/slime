package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Util;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.ISelectable;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccMacros;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class SlimyJump extends Slimy implements ISelectable {

	public static String thumbSprite = "wait-v-01.png";
	
	public static float Default_Powa = 2.5f;
	public static float Max_Impulse = 10f;	
	private static float Default_Selection_Width = 72f; // 48f
	private static float Default_Selection_Height = 78f; // 52f	

	private CGPoint target;
	private Vector2 targetImpulse;
	private Vector2 worldImpulse;
	private boolean selected;
	private CGPoint worldSelect;
	private float powa;
	private CGRect scaledRect;
	private CGRect selectionRect;
	
	private CCSprite thumbnailSprite;	
	
	public SlimyJump(float x, float y, float width, float height, World world,
			float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		
		this.powa = Default_Powa;
		this.target = CGPoint.getZero();
		this.targetImpulse = new Vector2();
		this.worldImpulse = new Vector2();
		this.scaledRect = CGRect.zero();
		this.selectionRect = CGRect.zero();
		this.selectionRect.size.width = Default_Selection_Width;
		this.selectionRect.size.height = Default_Selection_Height;
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
		
		if (this.scaledRect != null && this.isActive()) {			
			// Drawing selection rectangle
			Util.draw(gl, this.scaledRect, 2.0f, 0, 1, 0, 1);
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
	
	public boolean canSelect(CGPoint gameReference) {
		boolean can = false;
		if (!this.isDead) {				
			if (this.isInSlimy(gameReference)) {			
				can = true;
			}
		}
		
		return can;
	}
	
	public void select() {
		this.selected = true;		
	}
	
	public void select(CGPoint gameReference) {
		this.select();		
		this.computeTarget(gameReference);
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
//		if (this.selected) {
//			Vector2 antigravity = new Vector2();
//			antigravity.x = - Level.currentLevel.getGravity().x * 1.1f * this.getBody().getMass();
//			antigravity.y = - Level.currentLevel.getGravity().y * 1.1f * this.getBody().getMass();
//			float shift = 0;
//			if (this.angle != 0) {
//				if (this.angle <= 180) {
//					shift = this.angle / 180;
//				}
//				else {
//					shift = (this.angle - 360) / 180;
//				}
//			}
//			
//			this.getBody().applyForce(antigravity, new Vector2(this.body.getPosition().x + shift / 100, this.body.getPosition().y));			
//		}
		
		float zoom = Level.currentLevel.getCameraManager().getCurrentZoom();
		if (zoom != 0) {			
//			CGRect spriteRect = this.getSelectionRect();										
//			float baseX1 = spriteRect.origin.x;
//			float baseY1 = spriteRect.origin.y;			
//			
//			float baseW = Default_Selection_Width;
//			float baseH = Default_Selection_Height;
//			float scaledW = baseW / zoom; 
//			float scaledH = baseH / zoom;
//			if (scaledW < this.width) {
//				baseW = this.width;
//				scaledW = this.width;
//			}
//			
//			if (scaledH < this.height) {
//				baseH = this.height;
//				scaledH = this.height;
//			}
//			
//			float targX1 = baseX1 - (scaledW - baseW) / 2;
//			float targY1 = baseY1 - (scaledH - baseH) / 2;
//			
//			this.scaledRect.set(targX1, targY1, scaledW, scaledH);
			
			//Todo: Compute only on canSelect?
			Util.getScaledRect(this.getSelectionRect(), this.width, this.height, zoom, this.scaledRect);
		}
	}
	
	public CGRect getSelectionRect() {
		this.selectionRect.origin.x = this.position.x - Default_Selection_Width / 2;
		this.selectionRect.origin.y = this.position.y - Default_Selection_Height / 2;		
		
		return this.selectionRect;
	}
	
	@Override
	protected void contactInternal(GameItemPhysic item) {		
		this.land();		
	}

	@Override
	public CCSprite getThumbail() {
		if (this.thumbnailSprite == null) {
			this.thumbnailSprite = CCSprite.sprite(thumbSprite, true);			
		}
		
		return this.thumbnailSprite; 
	}

	@Override
	public CCNode getRootNode() {
		return this.rootNode;		
	}

	@Override
	public boolean isActive() {
		return !this.isDead;
	}
}