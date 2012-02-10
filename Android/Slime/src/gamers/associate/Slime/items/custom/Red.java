package gamers.associate.Slime.items.custom;

import java.util.Random;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCReverseTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.GameItemPhysic;

public class Red extends GameItemPhysic {
	public static final float Default_Width = 153;
	public static final float Default_Height = 105f;
	
	protected static float Default_Body_Width = 153f;
	protected static float Default_Body_Height = 105f;

	public static String Anim_Bite = "bite";
	public static String Anim_Breaking = "breaking";
	public static String Anim_Contracting = "contracting";
	public static String Anim_Wait = "wait";
	
	private static float SlimeMaxDistance = 480f;
	// Used for wait too
	private static int maxWaitJump = 3;
	private static float minTimeBeforeHit = 1;
	private long lastHitTime;
	private long nextTrigger;
	private boolean waitTrigger;
	
	private int life = 3;	
	private RedState state;
	
	private CCAction action;
	private Vector2 impulse;
	
	private static Random rand = new Random();
	
	public Red(float x, float y, float width, float height, World world,
			float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		
		this.spriteType = spriteType.ANIM_SCALE;
		this.setNoStick(true);
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;			
		}				
		
		this.bodyWidth = Default_Body_Width * this.width / Default_Width;
		this.bodyHeight = Default_Body_Height * this.height / Default_Height;
		
		this.state = RedState.Wait;
		this.impulse = new Vector2();
	}

	@Override
	protected void initBody() {
		// Physic body
				BodyDef bodyDef = new BodyDef();		
				bodyDef.type = BodyType.DynamicBody;
				CGPoint spawnPoint = new CGPoint();
				spawnPoint.x = this.position.x;
				spawnPoint.y = this.position.y;
				bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
				
				// Define another box shape for our dynamic body.
				PolygonShape dynamicBox = new PolygonShape();
				// dynamicBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
				float bW = this.bodyWidth / this.worldRatio;
				float bW2 = bW / 2;
				float bH = this.bodyHeight / this.worldRatio;
				float bH2 = bH / 2;
				Vector2 p1 = new Vector2(3 * bW / 14 - bW2, - bH2);
				Vector2 p2 = new Vector2(11 * bW / 14 - bW2, - bH2);
				Vector2 p3 = new Vector2(bW - bW2, 5 * bH / 10 - bH2);
				Vector2 p4 = new Vector2(12 * bW / 14 - bW2, 8 * bH / 10 - bH2);
				Vector2 p5 = new Vector2(8 * bW / 14 - bW2, 10 * bH / 10 - bH2);
				Vector2 p6 = new Vector2(3 * bW / 14 - bW2, 9 * bH / 10 - bH2);
				Vector2 p7 = new Vector2(bW / 14 - bW2, 6 *  bH / 10 - bH2);
				Vector2 p8 = new Vector2(bW / 14 - bW2, 3 *  bH / 10 - bH2);
				Vector2[] vertices = new Vector2[8];
				vertices[0] = p1;
				vertices[1] = p2;
				vertices[2] = p3;
				vertices[3] = p4;
				vertices[4] = p5;
				vertices[5] = p6;
				vertices[6] = p7;
				vertices[7] = p8;
				dynamicBox.set(vertices);
				
				synchronized (world) {
		    		// Define the dynamic body fixture and set mass so it's dynamic.
		    		this.body = world.createBody(bodyDef);
		    		this.body.setUserData(this);
		    		
		    		FixtureDef fixtureDef = new FixtureDef();
		    		fixtureDef.shape = dynamicBox;	    		
					fixtureDef.density = 0.1f;
					fixtureDef.friction = 0.9f;
		    		fixtureDef.restitution = 0.1f;    		
		    		
		    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
		    		this.body.createFixture(fixtureDef);
		    	}
	}
	
	@Override
	protected String getReferenceAnimationName() {
		return Anim_Wait;
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {					
		super.handleContact(item);
		
		if (item.getContactWith() instanceof Slimy) {
			Slimy slimy = (Slimy) item.getContactWith();
			switch (this.state) {
			case Attack:
				slimy.splash();
				break;
			case Defense:
			case Dead:
				break;
			case PrepareAttack:
			case Wait:
				float ySlimy = slimy.getPosition().y - slimy.getHeight() / 2;
				float yMe = this.getPosition().y - this.getHeight() / 2;
				if (yMe > ySlimy) {
					slimy.splash();
				} else {
					this.hit();
				}			
				break;
			}
						
			this.impulse(this, slimy, true, 2, 4);
		} else {
			this.land();
		}
	}

	private void land() {
		if (this.state == RedState.Attack) {
			this.goToWaitState();
		}
	}

	private void hit() {
		long hitTime = System.currentTimeMillis();
		if ((hitTime - this.lastHitTime) / 1000 > minTimeBeforeHit) {
			this.lastHitTime = hitTime;
			this.life--;
			if (this.life == 0) {
				this.goToDeadState();
			} else {
				this.goToDefenseState();
			}
		}		
	}

	private void goToDefenseState() {
		this.state = RedState.Defense;
		this.impulse(Level.currentLevel.getStartItem(), this, true, 3, 7);		
		this.defenseAnim();
		this.prepareNextJump();
	}

	private void prepareNextJump() {
		this.nextTrigger = System.currentTimeMillis() + ((rand.nextInt(maxWaitJump) + 2) * 1000);
		this.waitTrigger = true;
	}

	private void defenseAnim() {
		this.sprite.stopAllActions();
		CCAnimate shrink = CCAnimate.action(this.animationList.get(Anim_Contracting), false);
		this.action = shrink;
		this.sprite.runAction(this.action);
	}

	private void goToDeadState() {
		this.state = RedState.Dead;
		this.deadAnim();
		this.swithBodyCategory();
	}	
	
	private void deadAnim() {
		this.sprite.stopAllActions();
		CCAnimate shrink = CCAnimate.action(this.animationList.get(Anim_Contracting), false);
		CCAnimate breaking = CCAnimate.action(this.animationList.get(Anim_Breaking), false);
		// CCCallFunc call = CCCallFunc.action(this, "win");
		// CCSequence seq = CCSequence.actions(shrink, breaking, call);
		CCSequence seq = CCSequence.actions(shrink, breaking);
		this.action = seq;
		this.sprite.runAction(this.action);
	}
	
	public void win() {
		Slimy slimy = (Slimy) Level.currentLevel.getStartItem();
		slimy.win();
		slimy.destroyBodyOnly();
		Level.currentLevel.win();
	}

	public void waitAnim() {
		this.sprite.stopAllActions();		 
		CCCallFunc call = CCCallFunc.action(this, "waitAnimReal");
		if (this.state == RedState.Defense) {		
			CCAnimate shrink = CCAnimate.action(this.animationList.get(Anim_Contracting), false);			
			CCSequence seq = CCSequence.actions(shrink.reverse(), call);
			this.action = seq;
		} else {
			this.action = call;
		}
							
		this.sprite.runAction(this.action);
	}	
	
	public void waitAnimReal() {
		this.sprite.stopAllActions();
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
		CCDelayTime delay = CCDelayTime.action(2f);
		CCSequence seq = CCSequence.actions(animate, delay);
		CCRepeatForever repeat = CCRepeatForever.action(seq);		
		this.action = repeat;
		this.sprite.runAction(this.action);
		this.state = RedState.Wait;		
		this.prepareNextJump();
	}

	public void goToWaitState() {				
		this.waitAnim();
	}
	
	@Override
	public void render(float delta) {
		if (this.body != null) {
			this.body.setTransform(this.body.getPosition(), 0f);
		}
		
		super.render(delta);
		
		if (this.waitTrigger) {
			long time = System.currentTimeMillis();
			if (time > this.nextTrigger) {
				switch (this.state) {			
				case Attack:
				case Dead:			
				case PrepareAttack:
					break;
				case Defense:					
				case Wait:
					this.jump();
				}
				
				this.waitTrigger = false;
			}						
		}
	}

	private void jump() {
		GameItem item = Level.currentLevel.getStartItem();
		if (CGPoint.ccpDistance(this.getPosition(), item.getPosition()) <= SlimeMaxDistance) {
			this.prepareJump();
			
		} else {
			this.prepareNextJump();
		}		
	}
	
	private void prepareJump() {
		this.sprite.stopAllActions();		 
		CCCallFunc call = CCCallFunc.action(this, "prepareJumpReal");
		if (this.state == RedState.Defense) {		
			CCAnimate shrink = CCAnimate.action(this.animationList.get(Anim_Contracting), false);			
			CCSequence seq = CCSequence.actions(shrink.reverse(), call);
			this.action = seq;
		} else {
			this.action = call;
		}
							
		this.sprite.runAction(this.action);					
	}
	
	public void prepareJumpReal() {
		this.state = RedState.PrepareAttack;
		this.prepareJumpAnim();
	}
	
	private void prepareJumpAnim() {
		this.sprite.stopAllActions();
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Bite));
		CCCallFunc call = CCCallFunc.action(this, "jumpReal");
		CCSequence seq = CCSequence.actions(animate, call);
		this.action = seq;
		this.sprite.runAction(this.action);
	}
	
	public void jumpReal() {
		if (this.state == RedState.PrepareAttack) {
			GameItem item = Level.currentLevel.getStartItem();
			int dir = this.impulse(item, this, false, 4, 10);
			if (dir > 0) {
				this.sprite.setFlipX(true);
			} else {
				this.sprite.setFlipX(false);
			}
			this.state = RedState.Attack;
			
			this.jumpRealAnim();			
		}
	}
	
	private int impulse(GameItem item, GameItemPhysic toImpulse, boolean goAway, int minPowa, int maxPowa) {
		int dir = 1;
		if (goAway) {
			dir = -1;
		}
		
		if (item.getPosition().x < toImpulse.getPosition().x) {
			dir = -dir;
		}
		
		int powa = rand.nextInt(maxPowa + 1 - minPowa) + minPowa;
		this.impulse.x = powa * dir;
		this.impulse.y = powa;
		toImpulse.getBody().applyLinearImpulse(this.impulse, toImpulse.getBody().getPosition());
		
		return dir;
	}

	private void jumpRealAnim() {
		this.sprite.stopAllActions();
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Bite));
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.action = repeat;		
		this.sprite.runAction(this.action);		
	}
}