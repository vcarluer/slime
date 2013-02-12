package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.ContactInfo;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.Sounds;
import gamers.associate.SlimeAttack.items.base.GameItemPhysic;
import gamers.associate.SlimeAttack.items.base.SpriteType;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Star extends GameItemPhysic {
	public static final float AnimDelay = 0.3f;
	public static String Anim_Wait = "star";
	public static String Anim_Fade = "star-fading";	
	public static float Default_Width = 40f;
	public static float Default_Height = 40f;
	public static float Reference_Width = 40f;
	public static float Reference_Height = 40f;	
	public static String BaseFrameName = "star-01.png";
	
	private boolean fading;
		
	public Star(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);		
		this.spriteType = SpriteType.ANIM_SCALE;		
		
		if (width == 0 && height == 0) {
			this.width = this.bodyWidth = Default_Width;
			this.height = this.bodyHeight = Default_Height; 
		}		

		this.setNoStick(true);
		this.referenceSize.width = Reference_Width;
		this.referenceSize.height = Reference_Height;		
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
    		this.IsAllSensor = true;
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    		this.body.createFixture(fixtureDef);
		}
	}
	
	public void waitAnim() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.sprite.runAction(repeat);
		
//		CCRotateTo rotate = CCRotateTo.action(5f, 359);
//		CCRepeatForever repeatRotate = CCRepeatForever.action(rotate);
//		this.sprite.runAction(repeatRotate);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return Star.Anim_Wait;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {	
		if (!this.fading && item.getContactWith() instanceof Slimy) {	
			this.fading = true;
			// CCAnimate animate = CCAnimate.action(this.animationList.get(Star.Anim_Fade), false);			
			Level.currentLevel.AnimNewBonus(this.getPosition());
			this.getSprite().setVisible(false);
			CCDelayTime delay = CCDelayTime.action(AnimDelay);
			CCCallFunc activate = CCCallFunc.action(this, "starTaken");
			CCSequence sequence = CCSequence.actions(delay, activate);
			this.sprite.runAction(sequence);						
		}
		
		super.handleContact(item);
	}
	
	public void starTaken() {
		Sounds.playEffect(R.raw.star);
		Level.currentLevel.setNewBonus();
		Level.currentLevel.addItemToRemove(this);		
	}
}