package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.IElectrificable;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class EnergyBall extends GameItemPhysic {	
	public static String Anim_Wait = "energy-ball";
	private static float Default_Width = 37;
	private static float Default_Height = 37;
	public static float Reference_Width = 37;
	public static float Reference_Height = 37;
	private static float Default_Body_Width = 15;
	private static float Default_Body_Height = 15;
	
	private CGPoint tmp;
	
	public EnergyBall(float x, float y, float width, float height, World world,
			float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		this.spriteType = SpriteType.ANIM_SCALE_DIRECT;
		
		if (width == 0 && height == 0) {
			this.width = Default_Width;
			this.height = Default_Height; 
		}		
		
		this.bodyWidth = Default_Body_Width * this.width / Default_Width;
		this.bodyHeight = Default_Body_Height * this.height / Default_Height;
		
		this.referenceSize.width = Reference_Width;
		this.referenceSize.height = Reference_Height;
		
		this.tmp = CGPoint.zero();
		this.direction = CGPoint.zero();
	}

	@Override
	protected void initBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();		
		bodyDef.type = BodyType.StaticBody;
		CGPoint spawnPoint = new CGPoint();
		spawnPoint.x = this.position.x;
		spawnPoint.y = this.position.y;
		bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
		
		// Define another box shape for our dynamic body.
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = dynamicBox;
			fixtureDef.density = 0.0f;
			fixtureDef.friction = 0.0f;
    		fixtureDef.restitution = 0f;
    		fixtureDef.isSensor = true;
    		
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    		this.body.createFixture(fixtureDef);
		}
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return Anim_Wait;
	}

	public void waitAnim() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.sprite.runAction(repeat);
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {
		if (item.getContactWith() instanceof IElectrificable) {	
			IElectrificable elect = (IElectrificable) item.getContactWith();
			elect.electrify();
		}
		
		super.handleContact(item);
		this.removeMe();
	}

	@Override
	protected void runReferenceAction() {
		this.waitAnim();
	}
	
	/*
	 * speed = px / sec
	 */
	public void moveTo(CGPoint target, float speed) {
		this.move = true;
		
		// this.direction = CGPoint.ccpNormalize(CGPoint.ccpSub(target, this.getPosition()));
		this.tmp.x = target.x - this.position.x;
		this.tmp.y = target.y - this.position.y;
		float ln = CGPoint.ccpLength(this.tmp);
		this.direction.x = this.tmp.x / ln;
		this.direction.y = this.tmp.y / ln;		
		this.speed = speed;		
	}
	
	private boolean move;
	private CGPoint direction;
	private float speed;
	
	@Override
	public void render(float delta) {		
		super.render(delta);
		if (this.move) {
			if (this.speed > 0 && (this.direction.x != 0 || this.direction.y != 0)) {
				float x = this.getPosition().x + this.getMoveCoord(this.direction.x, delta);
				float y = this.getPosition().y + this.getMoveCoord(this.direction.y, delta);
				this.getSprite().setPosition(x, y);				
			}
		}
	}
	
	private float getMoveCoord(float directionCoord, float delta) {
		return directionCoord * this.speed * (delta / Level.currentLevel.getNormalTimeRatio());
	}
	
	protected void removeMe() {
		Level.currentLevel.addItemToRemove(this);
	}
}
