package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.R;
import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.Util;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.base.SpriteSheetFactory;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.config.ccMacros;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCMotionStreak;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.particlesystem.CCParticleFire;
import org.cocos2d.particlesystem.CCParticleMeteor;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccColor4B;
import org.cocos2d.types.ccColor4F;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

public class SlimyJump extends Slimy implements ISelectable {

	private static final int Shoot_Circle = 50; // in dps
	private static final int AuraScaleDown = 5;
	public static String thumbSprite = "wait-v-01.png";
	public static String Anim_Dbz_Aura = "aura";
	public static String Anim_Arrow = "greenarrow";
	
	public static float Default_Powa = 4.5f; // in dps
	public static float Max_Impulse = 7f;
	private static float Default_Selection_Width = 72f; // 48f
	private static float Default_Selection_Height = 78f; // 52f
	private static float MaxAuraSize = 150f;
	private static float MinAuraSize = 36f;
	
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
	private float scaledShoot;
	
	private CCAction actionSelect;
	
	private boolean stickHandled;
	
	private DistanceJointDef currentJointDef;	
	private Joint currentJoint;
	private CGPoint jointStart;
	private CGPoint selectStart;
	private CGPoint selectScreenStart;
	private CGPoint selectScreenEnd;
	private CGPoint absoluteScreenStart;
	
	private boolean hasJumped;
	
	private float maxContactManifold;
	
	private CCParticleSystem emitter;
	private CCSprite effectLayer = CCSprite.sprite("pf-01.png");
	
	private float emitterStartSize;
	
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
		
		this.jointStart = CGPoint.zero();
		this.selectScreenStart = CGPoint.zero();
		this.selectScreenEnd = CGPoint.zero();
		this.absoluteScreenStart = CGPoint.zero();
		this.maxContactManifold = (this.height / this.worldRatio) * 3;
		
		this.MinAuraSize = this.height + 10;
		this.MaxAuraSize = this.height + 124;
		this.emitter = CCParticleFire.node();
		this.emitter.setEmitterMode(CCParticleSystem.kCCPositionTypeRelative);		
		this.emitter.setTexture(CCTextureCache.sharedTextureCache().addImage("fire.png"));
		this.emitter.setBlendAdditive(true);
		Level.currentLevel.getLevelLayer().addChild(this.emitter, Level.zBack);
		this.emitter.setEmitterMode(CCParticleSystem.kCCParticleModeRadius);
		this.emitter.setPositionType(CCParticleSystem.kCCPositionTypeRelative);
		this.emitter.setLife(0.1f);
		this.emitter.setStartColor(new ccColor4F(0, 1f, 0, 1f));		
		this.emitter.stopSystem();
		
		this.scaledShoot = Shoot_Circle * SlimeFactory.Density;
	}
	
	public void selectionMove(CGPoint gameReference) {
		if (this.selected && this.isActive()) {			
			this.computeTarget(gameReference);
		}
	}
	
	protected void computeTarget(CGPoint gameTouch) {
		if (this.selected) {
			float zoom = Level.currentLevel.getCameraManager().getCurrentZoom();
			
			if (this.selectStart == null) {
				this.selectStart = CGPoint.zero();
				this.computeScreenStart(this.getPosition());
			}
			
			
			CGPoint tmp = Level.currentLevel.getCameraManager().getScreenPoint(gameTouch);
			this.selectScreenEnd.x = tmp.x;
			this.selectScreenEnd.y = tmp.y;
			this.targetImpulse.x = (this.selectScreenStart.x - this.selectScreenEnd.x);
			this.targetImpulse.y = (this.selectScreenStart.y - this.selectScreenEnd.y);
			if (this.targetImpulse.len() > this.scaledShoot) {
				this.targetImpulse.nor().mul(this.scaledShoot);
			}
			
			this.targetImpulse.mul(1 / SlimeFactory.Density).mul(this.powa);
			
			this.target.x = this.selectStart.x + this.targetImpulse.x;
			this.target.y = this.selectStart.y + this.targetImpulse.y;			
			
			this.worldSelect = gameTouch;
			
			this.worldImpulse.x = (this.targetImpulse.x  / this.worldRatio);
			this.worldImpulse.y = (this.targetImpulse.y / this.worldRatio);
		}
	}
	
	private void computeScreenStart(CGPoint gamePoint) {
		this.selectStart = gamePoint;
		CGPoint tmp = Level.currentLevel.getCameraManager().getScreenPoint(this.selectStart);
		this.selectScreenStart.x = tmp.x;
		this.selectScreenStart.y = tmp.y;
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.GameItem#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl) {				
		super.draw(gl);
		
		if (this.currentJoint != null) {
			gl.glDisable(GL10.GL_LINE_SMOOTH);
			gl.glColor4f(0.0f, 170f / 255f, 54f / 255f, 1.0f);
			gl.glLineWidth(5f);
			this.jointStart.x = this.currentJoint.getAnchorA().x * this.worldRatio;
			this.jointStart.y = this.currentJoint.getAnchorA().y * this.worldRatio;
			CCDrawingPrimitives.ccDrawLine(gl, this.jointStart, this.getPosition());
		}
		
		if (this.selected && this.selectScreenStart != null) {
			gl.glEnable(GL10.GL_LINE_SMOOTH);
			gl.glLineWidth(1.0f);
            gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
			CGPoint tmp = Level.currentLevel.getCameraManager().getGamePoint(this.selectScreenStart);
			this.absoluteScreenStart.x = tmp.x;
			this.absoluteScreenStart.y = tmp.y;
            CCDrawingPrimitives.ccDrawCircle(gl, this.absoluteScreenStart, this.scaledShoot / Level.currentLevel.getCameraManager().getCurrentZoom(), ccMacros.CC_DEGREES_TO_RADIANS(90), 50, false);
		}					
		
		if (Level.DebugMode) {
			if (this.selectStart != null && this.target != null) {
				gl.glDisable(GL10.GL_LINE_SMOOTH);
				gl.glColor4f(0.0f, 170f / 255f, 54f / 255f, 1.0f);
				gl.glLineWidth(2f);										
				CCDrawingPrimitives.ccDrawLine(gl, this.selectStart, this.target);
			}
			
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
			
			if (this.currentJoint != null) {
				gl.glDisable(GL10.GL_LINE_SMOOTH);
				gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
				gl.glLineWidth(5.0f);
				gl.glPointSize(2.0f);			
				CGPoint jointS = CGPoint.ccp(this.currentJoint.getAnchorA().x * this.worldRatio, this.currentJoint.getAnchorA().y * this.worldRatio);
				CGPoint jointE = CGPoint.ccp(this.currentJoint.getAnchorB().x * this.worldRatio, this.currentJoint.getAnchorB().y * this.worldRatio);
				//CCDrawingPrimitives.ccDrawLine(gl, jointS, jointE);
				CCDrawingPrimitives.ccDrawPoint(gl, jointS);
				CCDrawingPrimitives.ccDrawPoint(gl, jointE);
			}
			
			if (this.getBody() != null) {						
				CGRect spriteBody = CGRect.make(this.getPosition().x - this.bodyWidth / 2, this.getPosition().y - this.bodyHeight / 2, this.bodyWidth, this.bodyHeight);
				Util.draw(gl, spriteBody, 2.0f, 1, 0, 0, 1);
				CGRect spriteRect = CGRect.make(this.getPosition().x - this.width / 2, this.getPosition().y - this.height / 2, this.width, this.height);
				Util.draw(gl, spriteRect, 2.0f, 0, 0, 1, 1);
				
			}
		}
	}
	
	public boolean trySelect(CGPoint gameReference) {
		if (this.canSelect(gameReference)) {			
			this.select();
			this.computeTarget(gameReference);			
		} 
		
		return this.selected;
	}
	
	public boolean canSelect(CGPoint gameReference) {
		boolean can = false;
		if (this.isActive()) {				
			return true;
//			
//			if (!this.hasJumped) {			
//				if (this.isInSlimy(gameReference)) {
//					can = true;
//				}
//			}
//			else {
//				can = true;
//			}
		}
		
		if (!can && this.isDying) {			
			this.getSprite().stopAllActions();
			this.kill();			
		}
		
		return can;
	}
	
	public void select() {		
		this.selected = true;
		// this.auraSprite.setVisible(true);
		this.arrowSprite.setVisible(true);
		// CCAnimate animation = CCAnimate.action(this.animationList.get(Anim_Dbz_Aura), false);
		CCAnimate animation = CCAnimate.action(this.animationList.get(Anim_Buzz), false);
		this.actionSelect = CCRepeatForever.action(animation);
		this.sprite.runAction(this.actionSelect);
		// this.auraSprite.runAction(repeat);
		Sounds.playEffect(R.raw.slimycharging);			
	}
	
	public void select(CGPoint gameReference) {
		this.computeScreenStart(gameReference);
		this.select();
		this.computeTarget(gameReference);
	}
	
	public void unselect() {
		if (this.selected) {
			Sounds.stopEffect(R.raw.slimycharging);
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
	
	public void selectionStop(CGPoint gameReference) {		
		if (this.selected && this.isActive()) {
			this.computeTarget(gameReference);			
			if (this.getBody() != null) {
				// this.getBody().setAwake(true);				
				if (!this.hasLanded) {
					this.hasLanded = true;
					this.waitAnim();
				}
				this.getBody().setLinearVelocity(new Vector2(0, 0));
				Vector2 pos = this.getBody().getPosition();		
				this.getBody().applyLinearImpulse(this.worldImpulse, pos);				
				this.hasJumped = true;
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
			/*this.auraPosition.x = this.getPosition().x;
			this.auraPosition.y = this.getPosition().y - this.height / 2;
			this.auraSprite.setPosition(this.auraPosition);			
			// this.auraSprite.setRotation(this.getAngle());
			float scale = this.worldImpulse.len() / Max_Impulse;
			// scale go from 0 to 10, reajust to aura scale			
			scale = scale * this.auraScale + startAuraScale;
						
			 this.auraSprite.setScale(scale);*/
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
			
			if (!this.emitter.getActive()) {
				this.emitter.resetSystem();
			}
						
			float ech = MaxAuraSize - MinAuraSize;
			float size = this.worldImpulse.len() *  ech / Max_Impulse;
			this.emitterStartSize = MinAuraSize + size;
			this.emitter.setStartSize(this.emitterStartSize);
		} else {
			this.emitterStartSize -= AuraScaleDown;
			if (this.emitterStartSize < MinAuraSize) {
				this.emitterStartSize = MinAuraSize;
			}
			
			this.emitter.setStartSize(this.emitterStartSize);
		}
		
		this.emitter.setPosition(this.getPosition());
				
		// this.effectLayer.setPosition(Level.currentLevel.getCameraManager().getScreenPoint(this.getPosition()));		
		
		// CCMotionStreak streak = Level.currentLevel.getMotionStreak();
		//if (!this.isLanded) {			
			//streak.setVisible(true);
		//	streak.setPosition(CCDirector.sharedDirector().convertToGL(this.getPosition()));
		//} else {
			//streak.setVisible(false);
		//}
		
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
				&& !contact.getContactWith().isIsAllSensor()
				&& !this.isDead
				&& !this.isDying
				&& this.currentJoint == null
				&& this.getBody() != null) {
			
			this.emitter.stopSystem();
			int contactCount = contact.getManifold().getNumberOfContactPoints();
			if (contactCount > 0) {
				if (!contact.getContactWith().isNoStick()) {
					Vector2 contactPoint = null;
					Vector2 tmpPoint = null;
					// Bug here => Always take the first one or destroyed object at 0 0 ?
					for (int i = 0; i < contactCount; i++) {
						try
						{
							tmpPoint = contact.getManifold().getPoints()[i];
							if (tmpPoint.x != 0 && tmpPoint.y != 0) {
								// Try to debug long attach bug
								if (this.getBody().getPosition().dst(tmpPoint) < this.maxContactManifold) {
									contactPoint = tmpPoint;
									break;
								}								
							}
						} catch (Exception ex) {
							Log.e(Slime.TAG, "Error during read of contact manifold in SlimyJump");
						}
					}
					
					if (contactPoint != null) {
					
						Vector2 normal = contact.getManifold().getNormal();
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
						
		//					float radAngle = -1.0f * ccMacros.CC_DEGREES_TO_RADIANS(this.angle);				
		
		//				
						Vector2 jointB = this.getBody().getPosition();
						//				Vector2 jointB = new Vector2();
		//				jointB.x = this.getBody().getPosition().x - (normal.x * ((this.bodyWidth / 2) / this.worldRatio));
		// 				jointB.y = this.getBody().getPosition().y - (normal.y * ((this.bodyHeight / 2) / this.worldRatio));
		//				jointB.x = anchor.x + normal.x * ((this.bodyWidth / 2) / this.worldRatio);
		//				jointB.y = anchor.y + normal.y * ((this.bodyHeight / 2) / this.worldRatio);
		//				this.body.setTransform(jointB, radians);
						
						if (this.currentJointDef == null) {
							this.currentJointDef = new DistanceJointDef();									
						}
						
						// contact.getContactWith().getBody(), this.getBody(), contactPoint, normal);
						// this.currentJointDef.bodyA = contact.getContactWith().getBody();
						// this.currentJointDef.bodyB = this.getBody();
						this.currentJointDef.initialize(contact.getContactWith().getBody(), this.getBody(), contactPoint, jointB);
						this.jointStart.x = contactPoint.x * this.worldRatio;
						this.jointStart.y = contactPoint.y * this.worldRatio;
						
		//				this.currentJointDef.target.set(contactPoint);
		//				this.currentJointDef.maxForce = 3000.0f * this.getBody().getMass();
						this.currentJointDef.collideConnected = true;
						// this.currentJointDef.collideConnected = true;
		//				this.currentJointDef.dampingRatio = 1.0f;
			//			this.currentJointDef.enableMotor = false;
			//			this.currentJointDef.enableLimit = true;
			//			this.currentJointDef.lowerAngle = 0;
			//			this.currentJointDef.upperAngle = 0;
						this.currentJointDef.frequencyHz = 0.9f;
						this.currentJointDef.dampingRatio = 1.5f;
									
						this.currentJoint = this.world.createJoint(this.currentJointDef);
						
						this.stickHandled = true;
						//this.getBody().setAwake(false);
					}
				}
			}
		}				
	}

	public CCSprite getThumbail() {
		if (this.thumbnailSprite == null) {
			this.thumbnailSprite = CCSprite.sprite(thumbSprite, true);			
		}
		
		this.thumbnailSprite.setScale(Thumbnail.Reference_Height / (this.height + Thumbnail.Reference_Inside_Margin));
		return this.thumbnailSprite; 
	}

	public CCNode getRootNode() {
		return this.rootNode;		
	}

	@Override
	public boolean isActive() {
		return !this.isDead && !this.isDisabled && !this.isDying && !this.isPaused;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemCocos#postSpriteInit()
	 */
	@Override
	protected void postSpriteInit() {		
		super.postSpriteInit();
		this.auraSheet = SpriteSheetFactory.getSpriteSheet("slimydbz");		
		/*this.auraSprite = CCSprite.sprite(GameItemCocos.getAnimFirstFrame(Anim_Dbz_Aura));
		this.auraSprite.setVisible(false);
		this.auraSprite.setAnchorPoint(0.5f, 0f);
		this.auraSheet.addChild(this.auraSprite);*/
		
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
		/*this.auraSheet.removeChild(this.auraSprite, true);
		this.auraSheet.removeChild(this.arrowSprite, true);*/
		Level.currentLevel.getLevelLayer().removeChild(this.emitter, true);
		super.destroy();
	}
	
	private void stopAura() {
		this.sprite.stopAction(this.actionSelect);
		/*this.auraSprite.setVisible(false);
		this.auraSprite.stopAllActions();
		*/
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
	public void kill() {
		super.kill();
		this.unselect();				
		
		this.currentJoint = null;
		this.currentJointDef = null;
	}

	public boolean simpleSelect() {
		return true;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#destroyBodyOnly()
	 */
	@Override
	public void destroyBodyOnly() {
		if (this.currentJoint != null) {
			this.world.destroyJoint(this.currentJoint);			
		}
		
		this.currentJoint = null;
		super.destroyBodyOnly();						
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.custom.Slimy#prekill()
	 */
	@Override
	protected void prekill() {
		super.prekill();
		if (this.currentJoint != null) {
			this.world.destroyJoint(this.currentJoint);			
		}
		
		this.currentJoint = null;
	}

	@Override
	public boolean isThumbnailAwaysOn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isThumbnailActive() {
		return true;
	}
}