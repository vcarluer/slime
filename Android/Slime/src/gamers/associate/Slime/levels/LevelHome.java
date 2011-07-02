package gamers.associate.Slime.levels;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.GoalPortal;
import gamers.associate.Slime.items.custom.SpawnPortal;
import gamers.associate.Slime.layers.HomeLayer;

public class LevelHome extends LevelDefinition {
	public static String id = "Home";
	
	@Override
	public void buildLevel(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
		
		LevelUtil.createGroundBox(level);
		LevelUtil.createLand(level);
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
		
		SpawnPortal spawnPortal = SlimeFactory.SpawnPortal.createAndMove(
				level.getLevelWidth() / 2, 
				level.getLevelHeight() - 32,
				level.getLevelWidth() / 2,
				5);
		
		// Platform
		float goalPlatH = 20f;
		float goalPlatW = 100f;
		SlimeFactory.Platform.create(s.width / 2,LevelUtil.LAND_HEIGHT + goalPlatH / 2, goalPlatW, goalPlatH);
		
		// Goal
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(s.width / 2, LevelUtil.LAND_HEIGHT + goalPlatH + 15);
		
		// Bumper
		SlimeFactory.Bumper.create(30, s.height / 2, 60, 120, 2.0f);
						
		SlimeFactory.HomeLevelHandler.create().setPortal(spawnPortal);
		
		level.addCustomOverLayer(HomeLayer.get());
		level.setIsHudEnabled(false);
		level.setIsTouchEnabled(false);		
	}

	@Override
	protected void init() {
		this.setId(id);
		this.setSpecial(true);
	}
}
