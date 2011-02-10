package gamers.associate.Slime;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGSize;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class HardCodedLevel extends Level {

	public HardCodedLevel(CCNode node) {
		super(node);		
	}
	
	@Override
	protected void init() {
		super.init();
		
		// TEMP
		// Define the ground body.
        BodyDef bxGroundBodyDef = new BodyDef();
        bxGroundBodyDef.position.set(0.0f, 0.0f);
		
		// Call the body factory which allocates memory for the ground body
		// from a pool and creates the ground box shape (also from a pool).
		// The body is also added to the world.
        Body groundBody = world.createBody(bxGroundBodyDef);
        
        // Define the ground box shape.
        PolygonShape groundBox = new PolygonShape();
        
        CGSize s = CCDirector.sharedDirector().winSize();
        float scaledWidth = s.width/worldRatio;
        float scaledHeight = s.height/worldRatio;
        
        Vector2 bottomLeft = new Vector2(0f,0f);
        Vector2 topLeft = new Vector2(0f,scaledHeight);
        Vector2 topRight = new Vector2(scaledWidth,scaledHeight);
        Vector2 bottomRight = new Vector2(scaledWidth,0f);
        
     // bottom
		groundBox.setAsEdge(bottomLeft, bottomRight);
		groundBody.createFixture(groundBox,0);
		
		// top
		groundBox.setAsEdge(topLeft, topRight);
		groundBody.createFixture(groundBox,0);
		
		// left
		groundBox.setAsEdge(topLeft, bottomLeft);
		groundBody.createFixture(groundBox,0);
		
		// right
		groundBox.setAsEdge(topRight, bottomRight);
		groundBody.createFixture(groundBox,0);
		
		PlatformFactory.create(100, 50, 100, 10);
		PlatformFactory.create(s.width - 100, s.height - 100, 100, 10);
		
		this.goalPortal = GoalPortalFactory.create(s.width / 2, 20);
		this.items.add(this.goalPortal);
	}
}
