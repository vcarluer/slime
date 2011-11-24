package gamers.associate.Slime.items.custom;


import gamers.associate.Slime.R;
import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.IBurnable;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.config.ccMacros;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Slimy extends GameItemPhysic implements IBurnable {
	
	public static String Anim_Burned_Wait = "burned-wait";
	public static String Anim_Burning = "burning";
	public static String Anim_Falling = "falling";
	public static String Anim_Landing_H = "landing-h";
	public static String Anim_Landing_V = "landing-v";
	public static String Anim_Wait_H = "wait-h";
	public static String Anim_Wait_V = "wait-v";
	public static String Anim_Splash = "splashed";
	public static String Anim_Success = "success";
	public static String Anim_Spawn = "spawn";
	public static String Anim_Spawn_Falling = "spawn-falling";
	
	public static float Default_Width = 24f;
	public static float Default_Height = 26f;
	protected static float Default_Body_Width = 20f; //16f
	protected static float Default_Body_Height = 23f; //23f
	
	private static float Reference_Width = 32f;
	private static float Reference_Height = 35f;
	
	protected Boolean isLanded;	
	protected CCAction waitAction;
	protected Boolean isDead;
	protected Boolean isDying;
	
	protected boolean hasLanded;
	
	public Slimy(float x, float y, float width, float height, World world, float worldRatio) {		
		super(x, y, width, height, world, worldRatio);
		this.spriteType = SpriteType.ANIM_SCALE;
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;			
		}	
		
		this.bodyWidth = Default_Body_Width * this.width / Default_Width;
		this.bodyHeight = Default_Body_Height * this.height / Default_Height;

		this.isLanded = false;
		this.isDead = false;
		this.isDying = false;
		
		this.referenceSize.width = Reference_Width;
		this.referenceSize.height = Reference_Height;
	}
	
	@Override
	protected void initBody() {
		
	}
	
	protected void spawnBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		CGPoint spawnPoint = new CGPoint();
		spawnPoint.x = this.position.x;
		spawnPoint.y = this.position.y;
		bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
		bodyDef.angle = -1.0f * ccMacros.CC_DEGREES_TO_RADIANS(this.getAngle());		
		
		synchronized (world) {
			// Define the dynamic body fixture and set mass so it's dynamic.
			this.body = world.createBody(bodyDef);
			this.body.setUserData(this);
			
			this.createFixture();
    	}  
	}
	
	protected void createFixture() {
		// Define another box shape for our dynamic body.
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);

		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicBox;	
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.1f;
		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
		this.body.createFixture(fixtureDef);
	}		
	
	public void fadeIn() {
		CCFadeIn fade = CCFadeIn.action(0.5f);
		this.sprite.runAction(fade);
	}
	
	public void waitAnim() {
		if (this.waitAction != null && this.sprite != null) {
			this.sprite.stopAction(this.waitAction);
		}
		
		if (this.hasLanded) {
			CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait_V), false);
			CCDelayTime delay = CCDelayTime.action(3f);
			CCAnimate reverse = animate.reverse();
		
			this.waitAction = CCRepeatForever.action(CCSequence.actions(animate, reverse, delay));
		}
		else {
			CCAnimate animateSpawnFalling = CCAnimate.action(this.animationList.get(Anim_Spawn_Falling), false);
			CCAnimate reverse = animateSpawnFalling.reverse();
			this.waitAction = CCRepeatForever.action(CCSequence.actions(animateSpawnFalling, reverse));
		}
		
		this.sprite.runAction(this.waitAction);
	}
	
	public void fall() {		
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Falling), false);
		CCAnimate reverse = animate.reverse();
		
		CCAction action = CCRepeatForever.action(CCSequence.actions(animate, reverse));		
		this.currentAction = action;
		this.sprite.runAction(this.currentAction);
	}
	
	public void spawn() {
		CCAnimate animateSpawn = CCAnimate.action(this.animationList.get(Anim_Spawn), false);
		CCCallFunc callback = CCCallFunc.action(this, "spawnDone");
		CCSequence sequence = CCSequence.actions(animateSpawn, callback);
				
		this.currentAction = sequence;
		this.sprite.runAction(this.currentAction);
		
	}
	
	public void spawnDone() {
		this.spawnBody();
	}
	
	public void land() {
		if (!this.isLanded && !this.isDead && this.sprite != null && !this.isDying) {
			if (!this.hasLanded) {
				this.hasLanded = true;
				this.waitAnim();
			}
			
			if (this.currentAction != null) {
				this.sprite.stopAction(this.currentAction);
			}
			
			CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Landing_V), false);
			this.currentAction = animate;
			this.sprite.runAction(this.currentAction);
			this.isLanded = true;
						
			Sounds.playEffect(R.raw.slimyland);			
		}		
	}		
	
	@Override
	public void render(float delta) {
		super.render(delta);		
	}
	
	public void win() {
		this.success();
	}
	
	public void lose() {
		this.splash();
	}
	
	public void burn() {
		if (!this.isDead && !this.isDying) {
			if (this.currentAction != null) {				
				this.sprite.stopAction(this.currentAction);				
			}
			
			if (this.waitAction != null) {				
				this.sprite.stopAction(this.waitAction);
			}
					
			CCAnimate animateBlink = CCAnimate.action(this.animationList.get(Anim_Burned_Wait), false);
			CCDelayTime delay = CCDelayTime.action(3f);
			CCSequence blinkSeq = CCSequence.actions(animateBlink, animateBlink.reverse(), delay);
			this.waitAction = CCRepeatForever.action(blinkSeq);		
			this.sprite.runAction(this.waitAction);		
			
			CCAnimate animBurn = CCAnimate.action(this.animationList.get(Anim_Burning), false);
			CCCallFunc kill = CCCallFunc.action(this, "kill");
			CCSequence sequence = CCSequence.actions(animBurn, kill);
			this.currentAction = sequence;
			this.sprite.runAction(this.currentAction);
			this.isDying = true;
			Sounds.playEffect(R.raw.slimyfire);		
		}
	}
	
	public void splash() {
		if (!this.isDead && !this.isDying) {
			if (this.currentAction != null) {				
				this.sprite.stopAction(this.currentAction);				
			}
			
			if (this.waitAction != null) {				
				this.sprite.stopAction(this.waitAction);
			}
			
			CCAnimate animSplash = CCAnimate.action(this.animationList.get(Anim_Splash), false);			
			CCCallFunc kill = CCCallFunc.action(this, "kill");
			CCSequence sequence = CCSequence.actions(animSplash, kill);
			this.currentAction = sequence;		
			this.sprite.runAction(this.currentAction);
			this.isDying = true;
		}
	}
	
	public void success() {
		if (!this.isDead && !this.isDying) {
			if (this.currentAction != null) {				
				this.sprite.stopAction(this.currentAction);				
			}
			
			if (this.waitAction != null) {				
				this.sprite.stopAction(this.waitAction);
			}
					
			CCAnimate animSuccess = CCAnimate.action(this.animationList.get(Anim_Success), false);			
			CCDelayTime delay = CCDelayTime.action(2f);			
			CCSequence succSeq = CCSequence.actions(animSuccess, animSuccess.reverse(), delay);
			this.currentAction = CCRepeatForever.action(succSeq);				
			this.sprite.runAction(this.currentAction);
		}
	}
	
	public void kill() {
		if (this.body != null) {
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
		
		this.isDead = true;
		this.isDying = false;
		Level.currentLevel.slimyKilled(this);
	}
	
	public boolean isDead() {
		return this.isDead;
	}
	
	@Override
	protected String getReferenceAnimationName() {
		return Slimy.Anim_Wait_V;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.GameItemPhysic#handleContact(gamers.associate.Slime.GameItemPhysic)
	 */
	@Override 
	protected void handleContact(ContactInfo item) {		
		super.handleContact(item);
		this.contactInternal(item);
	}
	
	protected void contactInternal(ContactInfo item) {
		if (item.getContactWith() instanceof Slimy) {
			Slimy kSlimy = (Slimy)item.getContactWith();
			kSlimy.splash();
		}
		else {
			this.land();
		}
	}
	
	@Override
	protected void runReferenceAction() {
		this.waitAnim();
		this.fadeIn();
		this.spawn();
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handlePreRemove()
	 */
	@Override
	public void handleSpecialRemove() {
		Sounds.playEffect(R.raw.slimydeath);
		super.handleSpecialRemove();
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceTexture()
	 */
	/*@Override
	protected String getReferenceTexture() {		
		return super.getReferenceTexture();
	}*/
}