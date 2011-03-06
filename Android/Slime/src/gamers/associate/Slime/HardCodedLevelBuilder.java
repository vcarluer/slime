package gamers.associate.Slime;

import gamers.associate.Slime.items.Bumper;
import gamers.associate.Slime.items.GoalPortal;
import gamers.associate.Slime.items.SpawnCannon;
import gamers.associate.Slime.items.SpawnPortal;
import gamers.associate.Slime.layers.HomeLayer;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;


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
		float width = 800;
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
		SlimeFactory.Platform.createBL(cX, cY, 128, 80);
		cX += 128;
		SlimeFactory.Box.createBL(cX, 80, 40, 40);		
		SlimeFactory.Box.createBL(cX, 120, 40, 40);		
		SlimeFactory.Platform.createBL(cX, cY, 72, 80);
		cX += 72;
		SlimeFactory.Lava.createBL(cX, cY, 50, 50);
		cX += 50;
		SlimeFactory.Platform.createBL(cX, cY, 100, 50);
		cX += 40;
		SlimeFactory.Bumper.createBL(cX, 50, 50, 50).setAngle(90);
		//Bumper bumper = SlimeFactory.Bumper.createBL(cX, 50, 100, 100);		
		cX += 50;
		SlimeFactory.Platform.createBL(cX, 50, 10, 128);
		
		// Line 2
		cX = 400;
		cY = 250;
		SlimeFactory.Platform.createBL(cX, cY, 128, 20);
		SlimeFactory.Box.createBL(cX, cY + 20, 10, 80);
		cX += 128;		
		SlimeFactory.Platform.createBL(cX, cY, 128, 20);
		SlimeFactory.Platform.createBL(cX, cY + 20, 20, 128);
		cX += 20;
		// Goal
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(cX + 20, cY + 40);
		level.setGoalPortal(goalPortal);
		cX += 108;		
		
		// Spawn cannon
		level.setSpawnCannon(spawnCannon);		
	}
}
