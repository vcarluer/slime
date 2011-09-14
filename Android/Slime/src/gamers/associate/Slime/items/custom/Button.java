package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.ITrigerable;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Button extends GameItemPhysic {
	public static String Anim_Wait = "bumper-wait";
	public static String Anim_Bump = "bumper";	
	private static float Default_Width = 40f;
	private static float Default_Height = 40f;
	private static float Reference_Width = 40f;
	private static float Reference_Height = 40f;
	
	private String target;
	private float resetTime;
	private boolean isEnabled;
		
	public Button(float x, float y, float width, float height,
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
		target = "";
		this.isEnabled = true;
	}	
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public void setResetTime(float resetTime) {
		this.resetTime = resetTime;
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
		dynamicBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = dynamicBox;
			fixtureDef.density = 1.0f;
			fixtureDef.friction = 3.0f;
    		fixtureDef.restitution = 0f;    		
    		
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    		this.body.createFixture(fixtureDef);
		}
	}
	
	public void waitAnim() {
		CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Wait), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		this.sprite.runAction(repeat);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {
		return Button.Anim_Wait;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemPhysic#handleContact(gamers.associate.Slime.items.base.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {
		if (this.isEnabled) 
		{
			this.isEnabled = false;
			Sounds.playEffect(R.raw.bump);
			CCAnimate animate = CCAnimate.action(this.animationList.get(Anim_Bump), false);
			CCDelayTime delay = CCDelayTime.action(this.resetTime);
			CCCallFunc activate = CCCallFunc.action(this, "activate");
			CCSequence sequence = CCSequence.actions(animate, delay, activate);
			this.sprite.runAction(sequence);
			if (this.target != "") {
				for (ITrigerable trigerable : Level.currentLevel.getTrigerables(this.target)) {
					trigerable.trigger(this, null);
				}
			}
		}
		
		super.handleContact(item);
	}
	
	public void activate() {
		this.isEnabled = true;
	}
}