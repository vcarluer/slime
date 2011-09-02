package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.Util;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.base.SpriteSheetFactory;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.config.ccMacros;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints .WeldJointDef;

public class SlimyJump extends Slimy implements ISelectable {

	public static String thumbSprite = "wait-v-01.png";
	public static String Anim_Dbz_Aura = "aura";
	public static String Anim_Arrow = "greenarrow";
	
	public static float Default_Powa = 1.5f;
	public static float Max_Impulse = 7f;
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
	private CCSprite auraSprite;
	private CCSpriteSheet auraSheet;
	private CGPoint auraPosition;
	private static float startAuraScale = 0.35f;
	private static float endAuraScale = 1.0f;
	private float auraScale;
	private CCSprite arrowSprite;
	private static float arrowScale = 1.5f;
	private static float arrowAngleShift = -90;
	private boolean isDisabled;
	
	private boolean stickHandled;
	
	private RevoluteJointDef currentJointDef;	
	private Joint currentJoint;
	
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
		this.auraPosition = CGPoint.zero();
		this.auraScale = endAuraScale - startAuraScale;
	}
	
	public void selectionMove(CGPoint gameReference) {
		if (this.selected) {			
			this.computeTarget(gameReference);
		}
	}
	
	protected void computeTarget(CGPoint gameTouch) {
		if (this.selected) {
			float zoom = Level.currentLevel.getCameraManager().getCurrentZoom();
			
			this.targetImpulse.x = ((this.getPosition().x - gameTouch.x) * zoom) * this.powa;
			this.targetImpulse.y = ((this.getPosition().y - gameTouch.y) * zoom) * this.powa;						
			
			this.target.x = this.getPosition().x + this.targetImpulse.x;
			this.target.y = this.getPosition().y + this.targetImpulse.y;
			
			this.worldSelect = gameTouch;
			
			this.worldImpulse.x = (this.targetImpulse.x  / this.worldRatio);
			this.worldImpulse.y = (this.targetImpulse.y / this.worldRatio);
			if (this.worldImpulse.len() > (Max_Impulse)) {
				this.worldImpulse.nor().mul(Max_Impulse);
			}
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
		if (this.isActive()) {				
			if (this.isInSlimy(gameReference)) {			
				this.select();
				this.computeTarget(gameReference);
			}
		}
		
		return this.selected;
	}
	
	public boolean canSelect(CGPoint gameReference) {
		boolean can = false;
		if (this.isActive()) {				
			if (this.isInSlimy(gameReference)) {			
				can = true;
			}
		}
		
		return can;
	}
	
	public void select() {
		this.selected = true;
		this.auraSprite.setVisible(true);
		this.arrowSprite.setVisible(true);
		CCAnimate animation = CCAnimate.action(this.animationList.get(Anim_Dbz_Aura), false);
		CCRepeatForever repeat = CCRepeatForever.action(animation);
		this.auraSprite.runAction(repeat);
		Sounds.playEffect(R.raw.slimyselect);
	}
	
	public void select(CGPoint gameReference) {
		this.select();		
		this.computeTarget(gameReference);
	}
	
	public void unselect() {
		if (this.selected) {
			this.selected = false;	
			this.stopAura();
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
		if (this.selected && this.isLanded) {
			this.computeTarget(gameReference);			
			if (this.getBody() != null) {
				Vector2 pos = this.getBody().getPosition();						
				this.getBody().applyLinearImpulse(this.worldImpulse, pos);
				Sounds.playEffect(R.raw.slimyjump);
				this.isLanded = false;
				this.stickHandled = false;
				if (this.currentJoint != null) {
					this.world.destroyJoint(this.currentJoint);
					this.currentJoint = null;
				}
			}
		}		
	}		

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.custom.Slimy#render(float)
	 */
	@Override
	public void render(float delta) {
		super.render(delta);
		if (this.isSelected()) {
			this.auraPosition.x = this.getPosition().x;
			this.auraPosition.y = this.getPosition().y - this.height / 2;
			this.auraSprite.setPosition(this.auraPosition);
			float scale = this.worldImpulse.len() / Max_Impulse;
			// scale go from 0 to 10, reajust to aura scale			
			scale = scale * this.auraScale + startAuraScale;
			
			this.auraSprite.setScale(scale);
			this.arrowSprite.setPosition(this.getPosition());
			float radians = (float)Math.atan2(this.worldImpulse.x, this.worldImpulse.y);
			float degrees = ccMacros.CC_RADIANS_TO_DEGREES(radians) + arrowAngleShift;
			float zoom = Level.currentLevel.getCameraManager().getCurrentZoom();
			float cameraScale = 0;
			if (zoom != 0) {
				cameraScale = 1 / zoom;
			}
			this.arrowSprite.setScale(arrowScale * cameraScale);
			this.arrowSprite.setRotation(degrees);
		}		
		
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
	protected void contactInternal(ContactInfo item) {		
		this.land(item);	
	}
	
	public void land(ContactInfo contact) {	
		this.land();
		if (this.isLanded 
				&& !this.stickHandled 
				&& !contact.getContactWith().isNoStick()
				&& !contact.getContactWith().isIsAllSensor()
				&& !this.isDead
				&& this.currentJoint == null) {
													
			Vector2 normal = contact.getManifold().getNormal();
			if (normal != null) {
				float diffX = this.getBody().getPosition().x - contact.getContactWith().getBody().getPosition().x;
				if (diffX > 0 && normal.x < 0) {					
					normal.x = - normal.x;					
				}
				if (diffX < 0 && normal.x > 0) {
					normal.x = - normal.x;
				}
				
				float diffY = this.getBody().getPosition().y - contact.getContactWith().getBody().getPosition().y;
				if (diffY > 0 && normal.y < 0) {					
					normal.y = - normal.y;					
				}
				if (diffY < 0 && normal.y > 0) {					
					normal.y = - normal.y;					
				}
				
				float radians = (float)Math.atan2(normal.x, normal.y);
				float degrees = ccMacros.CC_RADIANS_TO_DEGREES(radians);				
				this.setAngle(degrees);
			}
			
			if (this.currentJointDef == null) {
				this.currentJointDef = new RevoluteJointDef();									
			}
											
			this.currentJointDef.initialize(this.getBody(), contact.getContactWith().getBody(), this.getBody().getPosition());
			this.currentJointDef.collideConnected = true;
			this.currentJointDef.enableMotor = false;
			this.currentJointDef.enableLimit = true;
			this.currentJointDef.lowerAngle = 0;
			this.currentJointDef.upperAngle = 0;
						
			this.currentJoint = this.world.createJoint(this.currentJointDef);
			
			this.stickHandled = true;
		}				
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
		return !this.isDead && !this.isDisabled;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemCocos#postSpriteInit()
	 */
	@Override
	protected void postSpriteInit() {		
		super.postSpriteInit();
		this.auraSheet = SpriteSheetFactory.getSpriteSheet("slimydbz");		
		this.auraSprite = CCSprite.sprite(GameItemCocos.getAnimFirstFrame(Anim_Dbz_Aura));
		this.auraSprite.setVisible(false);
		this.auraSprite.setAnchorPoint(0.5f, 0f);
		this.auraSheet.addChild(this.auraSprite);
		
		this.arrowSprite = CCSprite.sprite(GameItemCocos.getAnimFirstFrame(Anim_Arrow));
		this.arrowSprite.setVisible(false);		
		this.auraSheet.addChild(this.arrowSprite);					
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#destroy()
	 */
	@Override
	public void destroy() {
//		if (this.currentJoint != null) {
//			this.world.destroyJoint(this.currentJoint);
//			this.currentJoint = null;
//			this.currentJointDef = null;
//		}
		this.auraSheet.removeChild(this.auraSprite, true);
		this.auraSheet.removeChild(this.arrowSprite, true);
		super.destroy();
	}
	
	private void stopAura() {
		this.auraSprite.setVisible(false);
		this.auraSprite.stopAllActions();
		this.arrowSprite.setVisible(false);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.custom.Slimy#win()
	 */
	@Override
	public void win() {
		this.isDisabled = true;
		super.win();
	}
	
	@Override
	protected void kill() {
		super.kill();
		this.unselect();
		if (this.currentJoint != null) {
			this.world.destroyJoint(this.currentJoint);
			this.currentJoint = null;
			this.currentJointDef = null;
		}
	}
}