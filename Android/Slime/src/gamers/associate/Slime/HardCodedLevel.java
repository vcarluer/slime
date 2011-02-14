package gamers.associate.Slime;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class HardCodedLevel extends Level {
	
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
		
		/*SlimeFactory.Platform.create(100, 50, 100, 10);
		SlimeFactory.Platform.create(s.width - 100, s.height - 100, 100, 10);*/
		
		float si = 10;
		float m = si / 2;
		float w = s.width;
		float w2 = s.width / 2;
		float h = s.height;
		float h2 = s.height / 2;
		
		// up
		SlimeFactory.Platform.create(w2, h - m, w, si);
		//right
		SlimeFactory.Platform.create(w - m, h2, si, h);
		// bottom
		SlimeFactory.Platform.create(w2, m, w, si);
		// left
		SlimeFactory.Platform.create(m, h2, si, h);
		
		// Platform
		float goalPlatH = 20f;
		float goalPlatW = 100f;
		SlimeFactory.Platform.create(s.width / 2, si + goalPlatH / 2, goalPlatW, goalPlatH);
		
		// Goal
		this.goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, si + goalPlatH + 15);
		this.items.add(this.goalPortal);
		
		// Bumper
		Bumper bumper = SlimeFactory.Bumper.create(this.worldRatio + si, h2, 60, 120, 2.0f);
		// Bumper bumper = SlimeFactory.Bumper.create(this.worldRatio + si, h2);
		this.items.add(bumper);
		
		// this.levelLayer.getCamera()bottomLeft		
	}
}
