package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.ContactInfo;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItemPhysic;
import gamers.associate.Slime.items.base.IBurnable;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Lava extends GameItemPhysic {
	public static String Anim_Init = "lava";
	
	public Lava(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);		
		this.spriteType = SpriteType.ANIM_REPEAT;
		this.zOrder = Level.zBack;
	}

	@Override
	protected void initBody() {
		// Physic body
		BodyDef bodyDef = new BodyDef();		
		CGPoint spawnPoint = new CGPoint();
		spawnPoint.x = this.position.x;
		spawnPoint.y = this.position.y;
		bodyDef.position.set(spawnPoint.x/worldRatio, spawnPoint.y/worldRatio);
		
		// Define another box shape for our dynamic body.
		PolygonShape staticBox = new PolygonShape();
		staticBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);
    		this.body.setUserData(this);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = staticBox;
    		fixtureDef.isSensor = true;
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_Level;
    		this.body.createFixture(fixtureDef);
    	}		
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemPhysic#handleContact(gamers.associate.Slime.items.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {
		super.handleContact(item);
		if (item.getContactWith() instanceof IBurnable) {
			((IBurnable)item.getContactWith()).burn();
		}
	}
	
	public void initAnimation() {
		CCAnimate animation = CCAnimate.action(this.animationList.get(Anim_Init), false);
		this.currentAction = CCRepeatForever.action(animation);
		this.sprite.runAction(this.currentAction);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#runReferenceAction()
	 */
	@Override
	protected void runReferenceAction() {
		this.initAnimation();
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {		
		return Anim_Init;
	}
}
