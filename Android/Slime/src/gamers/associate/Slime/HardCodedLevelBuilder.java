package gamers.associate.Slime;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class HardCodedLevelBuilder {
	
	private static boolean groundBoxInit;
	
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
	
	private static void createGroundBox(Level level) {
		//if (!groundBoxInit) {
			/*
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
			groundBody.createFixture(groundBox,0);*/
			
			CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());		
			
			float si = 10;
			float m = si / 2;
			float w = s.width;
			float w2 = s.width / 2;
			float h = s.height;
			float h2 = s.height / 2;
			
			// up
			SlimeFactory.Platform.create(w2, h + m, w, si);
			//right
			SlimeFactory.Platform.create(w + m, h2, si, h);
			// bottom
			SlimeFactory.Platform.create(w2, -m, w, si);
			// left
			SlimeFactory.Platform.create(-m, h2, si, h);
			
			//groundBoxInit = true;
		//}
	}
	
	
	public static void buildHome(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
		
		createGroundBox(level);		
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
		
		SpawnPortal spawnPortal = SlimeFactory.SpawnPortal.createAndMove(
				level.levelWidth / 2, 
				level.levelHeight - 32,
				level.levelWidth / 2,
				5);
		level.setSpawnPortal(spawnPortal);
		
		// Platform
		float goalPlatH = 20f;
		float goalPlatW = 100f;
		SlimeFactory.Platform.create(s.width / 2,goalPlatH / 2, goalPlatW, goalPlatH);
		
		// Goal
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, goalPlatH + 15);
		level.setGoalPortal(goalPortal);
		
		// Bumper
		SlimeFactory.Bumper.create(30, s.height / 2, 60, 120, 2.0f);
						
		level.addCustomOverLayer(HomeLayer.get());
		level.setIsHudEnabled(false);
		level.setIsTouchEnabled(false);
	}		
	
	
	public static void buildLevel1(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
		
		createGroundBox(level);
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
		
		SpawnPortal spawnPortal = SlimeFactory.SpawnPortal.createAndMove(
				level.levelWidth / 2, 
				level.levelHeight - 32,
				level.levelWidth / 2,
				5);
		level.setSpawnPortal(spawnPortal);
		
		// Platform
		float goalPlatH = 20f;
		float goalPlatW = 100f;
		SlimeFactory.Platform.create(s.width / 2, goalPlatH / 2, goalPlatW, goalPlatH);
		
		// Goal
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, goalPlatH + 15);
		level.setGoalPortal(goalPortal);
		
		// Bumper
		SlimeFactory.Bumper.create(30, s.height / 2, 60, 120, 2.0f);		
	}
	
	public static float getHeightRatio() {
		return CCDirector.sharedDirector().winSize().height / CCDirector.sharedDirector().winSize().width;
	}
	
	public static void buildLevel2(Level level) {
		level.setLevelSize(
				2400,
				2400 * getHeightRatio());
		
		createGroundBox(level);
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
				
		// Platform
		float goalPlatW = 100f;
		float goalPlatH = 100f;		
		SlimeFactory.Platform.create(goalPlatW / 2, goalPlatH / 2, goalPlatW, goalPlatH);
		
		// Goal
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, goalPlatH + 15);
		level.setGoalPortal(goalPortal);
		
		// Spawn cannon
		level.setSpawnCannon(SlimeFactory.Cannon.create(goalPlatW - SpawnCannon.Default_Width / 2, goalPlatH + SpawnCannon.Default_Height / 2));		
	}
}
