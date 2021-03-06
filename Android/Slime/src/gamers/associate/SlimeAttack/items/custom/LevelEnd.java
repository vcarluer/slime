package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.game.ContactInfo;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.items.base.GameItemPhysic;
import gamers.associate.SlimeAttack.items.base.SpriteType;

import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LevelEnd extends GameItemPhysic {

	public LevelEnd(float x, float y, float width, float height,
			World world, float worldRatio) {
		super(x, y, width, height, world, worldRatio);
		this.spriteType = SpriteType.NONE;
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
    		this.IsAllSensor = true;
    		fixtureDef.filter.categoryBits = GameItemPhysic.Category_Level;
    		this.body.createFixture(fixtureDef);
    	}		
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.GameItemPhysic#handleContact(gamers.associate.Slime.GameItemPhysic)
	 */
	@Override
	protected void handleContact(ContactInfo item) {		
		super.handleContact(item);
		Level.currentLevel.addItemToRemove(item.getContactWith());
		item.getContactWith().handleSpecialRemove();
	}
	
	
}
