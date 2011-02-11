package gamers.associate.Slime;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GoalPortal extends GameItemPhysic {
	public static String Anim_Goal_Portal = "redportal";
	
	protected boolean isWon;
	
	public GoalPortal(CCNode node, float x, float y, float width, float height, World world,
			float worldRatio) {
		super(node, x, y, width, height, world, worldRatio);
		
		this.bodyWidth = this.width = 32;
		this.bodyHeight = this.height = 32;
		this.isWon = false;
		this.initBody();
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
		PolygonShape contactBox = new PolygonShape();
		contactBox.setAsBox(this.bodyWidth / this.worldRatio / 2, this.bodyHeight / this.worldRatio / 2);
		
		synchronized (world) {
    		// Define the dynamic body fixture and set mass so it's dynamic.
    		this.body = world.createBody(bodyDef);    		
    		this.body.setUserData(this);    		
    		Fixture fix = this.body.createFixture(contactBox, 1.0f);
    		fix.setSensor(true);
    	}		
	}
	
	public void createPortal() {
		CCAction animate = CCRepeatForever.action(CCAnimate.action(this.animationList.get(Anim_Goal_Portal), false));				
		this.sprite.runAction(animate);		
	}
	
	@Override
	public void contact(Object with) {		
		if (with instanceof Slimy) {
			Slimy slimy = (Slimy) with;
			slimy.win();
			this.isWon = true;
		}
	}
	
	public boolean isWon() {
		return this.isWon;
	}
	
	public void setWon(boolean value) {
		this.isWon = value;
	}

}
