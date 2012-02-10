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

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;
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
	public static String Anim_WaitDefense = "waitDefense";
	public static String Anim_WaitDead = "waitDead";
	
	private static float SlimeMaxDistance = 480f;
	// Used for wait too
	private static int maxWaitJump = 5;
	private static float minTimeBeforeHit = 1;
	private long lastHitTime;
	private long nextTrigger;
	private boolean waitTrigger;
	
	private int life = 1;	
	private RedState state;
		
	private CCAction waitAction;
	private CCAction waitActionDefense;
	private CCAction waitActionDead;
	private CCAction currentWait;
	private Vector2 impulse;
	
	private static Random rand = new Random();
	
	private boolean isBoss;
	private float densityBoss = 0.1f;	
	private float densityNorm = 2.0f;
	
	public Red(float x, float y, float width, float height, World world,
			float worldRatio, boolean isBoss) {
		super(x, y, width, height, world, worldRatio);
		
		this.spriteType = spriteType.ANIM_SCALE;
		this.setNoStick(true);
		this.isBoss = isBoss;
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;			
		}				
		
		this.bodyWidth = Default_Body_Width * this.width / Default_Width;
		this.bodyHeight = Default_Body_Height * this.height / Default_Height;
		
		this.state = RedState.Wait;
		this.impulse = new Vector2();
		
		this.waitTrigger = true;
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
					float density = this.densityNorm;
					if (this.isBoss()) {
						density = this.densityBoss;
					}

		    		fixtureDef.density = density;
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
				if (this.isBoss()) {
					slimy.splash();
				} else {
					this.contact(slimy);
				}
				
				break;
			case Defense:
			case Dead:			
				break;
			case PrepareAttack:
				if (!this.isBoss()) {
					this.contact(slimy);
				}

				break;
			case Wait:
				this.contact(slimy);			
				break;
			}
						
			this.impulse(this, slimy, true, 2, 4);
		} else {
			if (!(item.getContactWith() instanceof Red)) {
				this.land();
			}			
		}
	}

	private void contact(Slimy slimy) {
		float ySlimy = slimy.getPosition().y - slimy.getHeight() / 2;
		float yMe = this.getPosition().y - this.getHeight() / 2;
		if (yMe > ySlimy) {
			slimy.splash();
		} else {
			this.hit();
		}		
	}

	private void land() {
		if (this.state == RedState.Attack) {			
			this.sprite.stopAction(this.currentAction);
			this.goToWaitState();
		}
		
		Sounds.playEffect(R.raw.slimyland);
	}

	private void hit() {
		long hitTime = System.currentTimeMillis();
		if ((hitTime - this.lastHitTime) / 1000 > minTimeBeforeHit) {
			this.sprite.stopAction(this.currentAction);
			this.lastHitTime = hitTime;
			this.setLife(this.getLife() - 1);
			Sounds.playEffect(R.raw.slimydeath);
			if (this.getLife() == 0) {
				this.goToDeadState();
			} else {
				this.goToDefenseState();
			}
		}		
	}

	private void goToDefenseState() {
		this.state = RedState.Defense;
		this.impulse(Level.currentLevel.getStartItem(), this, true, 3, 7);		
		this.switchWait(this.state);
		this.defenseAnim();
		this.prepareNextJump(1);
	}

	private void prepareNextJump(int minSec) {
		this.nextTrigger = System.currentTimeMillis() + ((rand.nextInt(maxWaitJump) + minSec) * 1000);
		this.waitTrigger = true;
	}

	private void defenseAnim() {		
		CCAnimate shrink = CCAnimate.action(this.animationList.get(Anim_Contracting), false);
		this.currentAction = shrink;
		this.sprite.runAction(this.currentAction);
	}

	private void goToDeadState() {
		this.state = RedState.Dead;
		Sounds.playEffect(R.raw.slimyfire);
		this.deadAnim();
		this.swithBodyCategory();
	}	
	
	private void deadAnim() {		
		Slimy slimy = (Slimy) Level.currentLevel.getStartItem();
		if (slimy.isAlive() && this.isBoss()) {
			slimy.win();
			slimy.destroyBodyOnly();					
		}	
		
		boolean go = true;
		if (this.isBoss()) {
			go = Level.currentLevel.win(false);
		}
		
		if (go) {
			CCAnimate shrink = CCAnimate.action(this.animationList.get(Anim_Contracting), false);
			CCAnimate breaking = CCAnimate.action(this.animationList.get(Anim_Breaking), false);
			CCCallFunc call = CCCallFunc.action(this, "win");
			CCSequence seq = CCSequence.actions(shrink, breaking, call);
			this.currentAction = seq;
			this.sprite.runAction(this.currentAction);
		}
															
	}
	
	public void win() {
		this.switchWait(this.state);
		if (this.isBoss()) {
			Level.currentLevel.showEndLevel();
		}
	}

	public void waitAnim() {	 
		CCCallFunc call = CCCallFunc.action(this, "waitAnimReal");
		if (this.state == RedState.Defense) {		
			CCAnimate shrink = CCAnimate.action(this.animationList.get(Anim_Contracting), false);			
			CCSequence seq = CCSequence.actions(shrink.reverse(), call);
			this.currentAction = seq;
		} else {
			this.currentAction = call;
		}
							
		this.sprite.runAction(this.currentAction);
	}	
	
	public void waitAnimReal() {								
		this.state = RedState.Wait;		
		this.switchWait(this.state);
		this.prepareNextJump(0);
	}
	
	private void switchWait(RedState state) {												
		CCAction nextWait = null;
		
		if (state == RedState.Wait) {
			if (this.waitAction == null) {
				CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
				CCDelayTime delay = CCDelayTime.action(2f);
				CCSequence seq = CCSequence.actions(animate, delay);
				CCRepeatForever repeat = CCRepeatForever.action(seq);		
				this.waitAction = repeat;				
			}
			
			nextWait = this.waitAction;
		}
		
		if (state == RedState.Defense) {
			if (this.waitActionDefense == null) {
				CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_WaitDefense), false);
				CCDelayTime delay = CCDelayTime.action(2f);
				CCSequence seq = CCSequence.actions(animate, delay);
				CCRepeatForever repeat = CCRepeatForever.action(seq);		
				this.waitActionDefense = repeat;				
			}
			
			nextWait = this.waitActionDefense;
		}
		
		if (state == RedState.Dead) {
			if (this.waitActionDead == null) {
				CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_WaitDead), false);
				CCDelayTime delay = CCDelayTime.action(2f);
				CCSequence seq = CCSequence.actions(animate, delay);
				CCRepeatForever repeat = CCRepeatForever.action(seq);		
				this.waitActionDead = repeat;				
			}
			
			nextWait = this.waitActionDead;
		}

		if (this.currentWait != nextWait) {
			if (this.currentWait != null) {
				this.sprite.stopAction(this.currentWait);
			}
			
			if (nextWait != null) {
				this.currentWait = nextWait;
				this.sprite.runAction(this.currentWait);
			}
		}
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
		
		if (Level.currentLevel.getGamePlay().isStarted()) {
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
	}

	private void jump() {
		GameItem item = Level.currentLevel.getStartItem();
		if (CGPoint.ccpDistance(this.getPosition(), item.getPosition()) <= SlimeMaxDistance) {
			this.prepareJump();
			
		} else {
			this.prepareNextJump(0);
		}		
	}
	
	private void prepareJump() {	 
		CCCallFunc call = CCCallFunc.action(this, "prepareJumpReal");
		if (this.state == RedState.Defense) {		
			CCAnimate shrink = CCAnimate.action(this.animationList.get(Anim_Contracting), false);			
			CCSequence seq = CCSequence.actions(shrink.reverse(), call);
			this.currentAction = seq;
		} else {
			this.currentAction = call;
		}
							
		this.sprite.runAction(this.currentAction);					
	}
	
	public void prepareJumpReal() {
		Sounds.playEffect(R.raw.slimyselect);
		this.state = RedState.PrepareAttack;
		this.prepareJumpAnim();
	}
	
	private void prepareJumpAnim() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Bite));
		CCCallFunc call = CCCallFunc.action(this, "jumpReal");
		CCSequence seq = CCSequence.actions(animate, animate, call);
		this.currentAction = seq;
		this.sprite.runAction(this.currentAction);
		
		GameItem item = Level.currentLevel.getStartItem();
		int dir = this.getDir(item, this, false);
		if (dir > 0) {
			this.sprite.setFlipX(true);
		} else {
			this.sprite.setFlipX(false);
		}
	}
	
	public void jumpReal() {
		if (this.state == RedState.PrepareAttack) {
			GameItem item = Level.currentLevel.getStartItem();
			Sounds.playEffect(R.raw.slimyjump);
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
	
	private int getDir(GameItem target, GameItem source, boolean goAway) {
		int dir = 1;
		if (goAway) {
			dir = -1;
		}
		
		if (target.getPosition().x < source.getPosition().x) {
			dir = -dir;
		}
		
		return dir;
	}
	
	private int impulse(GameItem item, GameItemPhysic toImpulse, boolean goAway, int minPowa, int maxPowa) {
		int dir = this.getDir(item, toImpulse, goAway);
		
		int powa = rand.nextInt(maxPowa + 1 - minPowa) + minPowa;
		this.impulse.x = powa * dir;
		this.impulse.y = powa;
		if (toImpulse.getBody() != null) {
			toImpulse.getBody().applyLinearImpulse(this.impulse, toImpulse.getBody().getPosition());
		}
		
		return dir;
	}

	private void jumpRealAnim() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Bite));
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.currentAction = repeat;		
		this.sprite.runAction(this.currentAction);		
	}

	public boolean isBoss() {
		return isBoss;
	}

	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
}