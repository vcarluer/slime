package gamers.associate.Slime.game;

import gamers.associate.Slime.items.custom.Box;
import gamers.associate.Slime.items.custom.Bumper;
import gamers.associate.Slime.items.custom.GoalPortal;
import gamers.associate.Slime.items.custom.Platform;
import gamers.associate.Slime.items.custom.SpawnCannon;
import gamers.associate.Slime.items.custom.SpawnPortal;
import gamers.associate.Slime.layers.HomeLayer;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;


public class HardCodedLevelBuilder {
	
	public static float LAND_HEIGHT = 64f;
	
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
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());		
		
		// Creating Level end box which destroys physic objects when then contact it			
		float si = 10;
		float m = level.getWorlRatio() * 2;
		float w = s.width;
		float w2 = s.width / 2;
		float h = s.height;
		float h2 = s.height / 2;					
		// up
		SlimeFactory.LevelEnd.create(w2, h + m, w, si);
		//right
		SlimeFactory.LevelEnd.create(w + m, h2, si, h);
		// bottom
		SlimeFactory.LevelEnd.create(w2, -m, w, si);
		// left
		SlimeFactory.LevelEnd.create(-m, h2, si, h);			
	}
	
	private static void createLand(Level level) {
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());		
		
		SlimeFactory.Platform.create(s.width / 2, LAND_HEIGHT / 2, s.width, LAND_HEIGHT);
	}
		
	public static void buildHome(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
		
		createGroundBox(level);
		createLand(level);
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
		SlimeFactory.Platform.create(s.width / 2,LAND_HEIGHT + goalPlatH / 2, goalPlatW, goalPlatH);
		
		// Goal
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, LAND_HEIGHT + goalPlatH + 15);
		level.setGoalPortal(goalPortal);
		
		// Bumper
		SlimeFactory.Bumper.create(30, s.height / 2, 60, 120, 2.0f);
						
		SlimeFactory.HomeLevelHandler.create();
		
		level.addCustomOverLayer(HomeLayer.get());
		level.setIsHudEnabled(false);
		level.setIsTouchEnabled(false);
	}		
	
	
	public static void buildLevel1(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
		
		createGroundBox(level);
		createLand(level);
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
		SlimeFactory.Platform.create(s.width / 2, LAND_HEIGHT + goalPlatH / 2, goalPlatW, goalPlatH);
		
		// Goal
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, LAND_HEIGHT + goalPlatH + 15);
		level.setGoalPortal(goalPortal);
		
		// Bumper
		SlimeFactory.Bumper.create(30, s.height / 2, 60, 120, 2.0f);		
	}
	
	public static float getHeightRatio() {
		return CCDirector.sharedDirector().winSize().height / CCDirector.sharedDirector().winSize().width;
	}
	
	public static void buildLevel2(Level level) {
		float width = 950;
		level.setLevelSize(
				width,
				width * getHeightRatio());
		
		createGroundBox(level);
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
				
		// Platform
		/*float goalPlatW = 100f;
		float goalPlatH = 100f;		
		SlimeFactory.Platform.create(goalPlatW / 2, goalPlatH / 2, goalPlatW, goalPlatH);*/
		
		// Lava
		/*SlimeFactory.Lava.create(200, 25, 200, 50);
		SlimeFactory.Platform.create(325, 50, 50, 100);
		SlimeFactory.Lava.create(400, 25, 100, 50);
		SlimeFactory.Bumper.create(375, 75, 50, 50);*/
		
		// Line 1
		float cX = 0;
		float cY = 0;
		SlimeFactory.Platform.createBL(cX, cY, 100, 100);
		SpawnCannon spawnCannon = SlimeFactory.Cannon.create(100 - SpawnCannon.Default_Width / 2, 100 + SpawnCannon.Default_Height / 2);
		cX += 100;
		SlimeFactory.Lava.createBL(cX, cY, 200, 50);
		cX += 200;
		SlimeFactory.Platform.createBL(cX, cY, 50, 100);
		cX += 50;		
		SlimeFactory.Platform.createBL(cX, cY, 50, 50);
		SlimeFactory.Bumper.createBL(cX, 50, 50, 50);
		cX += 50;
		SlimeFactory.Lava.createBL(cX, cY, 50, 50);
		cX += 50;
		SlimeFactory.Platform.createBL(cX, cY, 200, 80);
		cX += 70;
		SlimeFactory.Box.createBL(cX, 80, 40, 40);		
		SlimeFactory.Box.createBL(cX, 120, 40, 40);
		cX += 50;
		/*CGPoint[] vertices = new CGPoint[4];
		vertices[0] = CGPoint.make(0, 50);
		vertices[1] = CGPoint.make(0, 0);
		vertices[2] = CGPoint.make(50, 50);
		vertices[3] = CGPoint.make(50, 0);
		
		CGPoint[] body = new CGPoint[4];
		body[0] = CGPoint.make(0, 50);
		body[1] = CGPoint.make(0, 0);
		body[2] = CGPoint.make(50, 0);
		body[3] = CGPoint.make(50, 50);*/
		
		CGPoint[] vertices = new CGPoint[9];
		vertices[0] = CGPoint.make(0, 0);
		vertices[1] = CGPoint.make(20, 40);
		vertices[2] = CGPoint.make(20, 0);
		vertices[3] = CGPoint.make(50, 50);
		vertices[4] = CGPoint.make(50, 0);
		vertices[5] = CGPoint.make(70, 35);
		vertices[6] = CGPoint.make(70, 0);
		vertices[7] = CGPoint.make(77, 20);
		vertices[8] = CGPoint.make(80, 0);		
		
		CGPoint[] body = new CGPoint[6];
		body[0] = CGPoint.make(0, 0);
		body[1] = CGPoint.make(80, 0);
		body[2] = CGPoint.make(77, 20);
		body[3] = CGPoint.make(70, 35);
		body[4] = CGPoint.make(50, 50);
		body[5] = CGPoint.make(20, 40);
		
		SlimeFactory.Polygon.create(cX, 80, 0, 0, true, body, vertices);
		cX += 80;
		SlimeFactory.Lava.createBL(cX, cY, 200, 50);
		cX += 200;
		SlimeFactory.Platform.createBL(cX, cY, 100, 50);
		cX += 40;
		SlimeFactory.Bumper.createBL(cX, 50, 50, 50, 0.8f).setAngle(-90);
		//Bumper bumper = SlimeFactory.Bumper.createBL(cX, 50, 100, 100);		
		cX += 50;
		SlimeFactory.Platform.createBL(cX, 50, 10, 128);
		
		// Line 2
		cX = 400;
		cY = 250;
		SlimeFactory.Platform.createBL(cX, cY, 256, 20);
		SlimeFactory.Box.createBL(cX, cY + 20, 10, 80);
		cX += 128;				
		SlimeFactory.Platform.createBL(cX, cY + 20, 20, 128);
		cX += 64;
		// Goal
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(cX, cY + 40);
		level.setGoalPortal(goalPortal);
		cX += 108;
		
		//Line 3
		cX = 0;
		cY = 398;
		SlimeFactory.Platform.createBL(cX, cY, 256, 20);
		cX += 256;		
		SlimeFactory.Box.createBL(cX, cY + 20, 60, 60);
		SlimeFactory.Platform.createBL(cX, cY, 128, 20);
				
		// Chain
		Platform platform = SlimeFactory.Platform.create(cX, cY - 5, 30, 10);
		cY -= 10;
		Box.setChainMode(true);
		CGSize segSize = CGSize.make(3, 15);
		RevoluteJointDef joint = new RevoluteJointDef();
		Body link = platform.getBody();
		float count = 10;
		for(int i = 0; i < count; i++) {
			Box box = SlimeFactory.Box.create(cX, cY - (i + 1) * (segSize.height) + segSize.height / 2, segSize.width, segSize.height);			
			Vector2 linkPoint = new Vector2();
			linkPoint.x = box.getBody().getPosition().x;
			linkPoint.y = box.getBody().getPosition().y + (segSize.height / 2 / level.getWorlRatio());
			joint.initialize(link, box.getBody(), linkPoint);
			level.getWorld().createJoint(joint);
			link = box.getBody();
		}
		
		Box.setChainMode(false);
		
		float boxSize = 20f;
		Box box = SlimeFactory.Box.create(cX, cY - (count * segSize.height) - boxSize / 2, boxSize, boxSize);
		Vector2 linkPoint = new Vector2();
		linkPoint.x = box.getBody().getPosition().x;
		linkPoint.y = box.getBody().getPosition().y + (boxSize / 2 / level.getWorlRatio());
		joint.initialize(link, box.getBody(), linkPoint);
		level.getWorld().createJoint(joint);
		
		box.getBody().applyLinearImpulse(new Vector2(5.0f, 0f), box.getBody().getPosition());
		
		cY += 10;		
		// end chain
		
		cX += 128;				
		SlimeFactory.Platform.createBL(cX, cY, 256, 128);		
		cX += 256;
		SlimeFactory.Platform.createBL(cX - 20, cY + 128, 20, 128);				
		
		// Spawn cannon
		level.setSpawnCannon(spawnCannon);		
	}
}
