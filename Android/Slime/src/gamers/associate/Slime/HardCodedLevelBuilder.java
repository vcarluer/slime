package gamers.associate.Slime;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class HardCodedLevelBuilder {
	
	public static void build(Level level, String levelName) {
		if (levelName == Level.LEVEL_HOME) {
			buildHome(level);
		}
		
		if (levelName == Level.LEVEL_1) {
			buildLevel1(level);
		}
		
		if (levelName == Level.LEVEL_2) {
			buildLevel2(level);
		}
	}
	
	public static void buildHome(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
		
		// TEMP
		// Define the ground body.
        BodyDef bxGroundBodyDef = new BodyDef();
        bxGroundBodyDef.position.set(0.0f, 0.0f);
		
		// Call the body factory which allocates memory for the ground body
		// from a pool and creates the ground box shape (also from a pool).
		// The body is also added to the world.
        Body groundBody = level.getWorld().createBody(bxGroundBodyDef);
        
        // Define the ground box shape.
        PolygonShape groundBox = new PolygonShape();
        
        // CGSize s = CCDirector.sharedDirector().winSize();
        CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
        float scaledWidth = s.width/level.getWorlRatio();
        float scaledHeight = s.height/level.getWorlRatio();
        
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
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, si + goalPlatH + 15);
		level.setGoalPortal(goalPortal);
		
		// Bumper
		Bumper bumper = SlimeFactory.Bumper.create(level.getWorlRatio() + si, h2, 60, 120, 2.0f);
		// Bumper bumper = SlimeFactory.Bumper.create(this.worldRatio + si, h2);
		level.addGameItem(bumper);
				
		// this.cameraManager.follow(this.spawnPortal);
		// level.getCameraManager().centerCameraOn(level.getSpawnPortal().getPosition());		
		
		level.addCustomOverLayer(HomeLayer.get());
		level.setIsHudEnabled(false);
		level.setIsTouchEnabled(false);
	}		
	
	
	public static void buildLevel1(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
		
		// TEMP
		// Define the ground body.
        BodyDef bxGroundBodyDef = new BodyDef();
        bxGroundBodyDef.position.set(0.0f, 0.0f);
		
		// Call the body factory which allocates memory for the ground body
		// from a pool and creates the ground box shape (also from a pool).
		// The body is also added to the world.
        Body groundBody = level.getWorld().createBody(bxGroundBodyDef);
        
        // Define the ground box shape.
        PolygonShape groundBox = new PolygonShape();
        
        // CGSize s = CCDirector.sharedDirector().winSize();
        CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
        float scaledWidth = s.width/level.getWorlRatio();
        float scaledHeight = s.height/level.getWorlRatio();
        
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
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, si + goalPlatH + 15);
		level.setGoalPortal(goalPortal);
		
		// Bumper
		Bumper bumper = SlimeFactory.Bumper.create(level.getWorlRatio() + si, h2, 60, 120, 2.0f);
		// Bumper bumper = SlimeFactory.Bumper.create(this.worldRatio + si, h2);
		level.addGameItem(bumper);
	}
	
	public static void buildLevel2(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
		
		// TEMP
		// Define the ground body.
        BodyDef bxGroundBodyDef = new BodyDef();
        bxGroundBodyDef.position.set(0.0f, 0.0f);
		
		// Call the body factory which allocates memory for the ground body
		// from a pool and creates the ground box shape (also from a pool).
		// The body is also added to the world.
        Body groundBody = level.getWorld().createBody(bxGroundBodyDef);
        
        // Define the ground box shape.
        PolygonShape groundBox = new PolygonShape();
        
        // CGSize s = CCDirector.sharedDirector().winSize();
        CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
        float scaledWidth = s.width/level.getWorlRatio();
        float scaledHeight = s.height/level.getWorlRatio();
        
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
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, si + goalPlatH + 15);
		level.setGoalPortal(goalPortal);
		
		// Bumper
		Bumper bumper = SlimeFactory.Bumper.create(level.getWorlRatio() + si, h2, 60, 120, 2.0f);
		// Bumper bumper = SlimeFactory.Bumper.create(this.worldRatio + si, h2);
		level.addGameItem(bumper);
		
		level.setSpawnCannon(SlimeFactory.Cannon.create(60, 60));		
	}
}
