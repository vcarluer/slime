package gamers.associate.Slime.items;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Lava extends GameItemPhysic {
	public static String texture1 = "lava-1.png";
	public static String texture2 = "lava-2.png";
	public static String Anim_Init = "lava";
	
	public Lava(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		this.textureMode = TextureMode.REPEAT;
		this.spriteType = SpriteType.ANIM_REPEAT;
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
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_Static;
    		this.body.createFixture(fixtureDef);
    	}		
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemPhysic#handleContact(gamers.associate.Slime.items.GameItemPhysic)
	 */
	@Override
	protected void handleContact(GameItemPhysic item) {
		super.handleContact(item);
		if (item instanceof IBurnable) {
			((IBurnable)item).burn();
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
