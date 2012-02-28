package gamers.associate.Slime.levels;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.BumperAngle;
import gamers.associate.Slime.items.custom.LaserGun;
import gamers.associate.Slime.items.custom.Platform;
import gamers.associate.Slime.items.custom.SpawnPortal;
import gamers.associate.Slime.layers.HomeLayer;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

public class LevelHome extends LevelDefinitionHardCoded {
	public static String Id = "Home";		
	
	@Override
	public void buildLevel(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
		
		// LevelUtil.createGroundBox(level);
		// LevelUtil.createLand(level);
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
		
		SpawnPortal spawnPortal = SlimeFactory.SpawnPortal.createAndMove(
				level.getLevelWidth() / 2f, 
				level.getLevelHeight() + 32,
				level.getLevelWidth() / 2f,
				5);
				
		// Ground
		float groundHeight = 128f;
		SlimeFactory.Platform.createWallBL(0, 0, level.getLevelWidth(), groundHeight);
		float middleBlockS = 64f;
		SlimeFactory.Platform.createWallBL(level.getLevelWidth() / 2f - middleBlockS / 2f, groundHeight, middleBlockS, middleBlockS);
		
		//Borders
		float borderW = 32f;
		SlimeFactory.Platform.create(- borderW / 2f, groundHeight + (level.getLevelHeight() - groundHeight) / 2f, level.getLevelHeight() - groundHeight, borderW, Platform.Bump).setAngle(90f);
		SlimeFactory.Platform.create(level.getLevelWidth() + borderW / 2f, groundHeight + (level.getLevelHeight() - groundHeight) / 2f, level.getLevelHeight() - groundHeight, borderW, Platform.Bump).setAngle(90f);
		// Level ends
		float endHeight = 64f;
		SlimeFactory.LevelEnd.createBL(0, level.getLevelHeight(), borderW, endHeight);
		SlimeFactory.LevelEnd.createBL(level.getLevelWidth() - borderW, level.getLevelHeight(), borderW, endHeight);
		SlimeFactory.LevelEnd.createBL(0, level.getLevelHeight() + endHeight, level.getLevelWidth(), endHeight);
		
		//Bumpers
		SlimeFactory.BumperAngle.createBL(0, groundHeight, BumperAngle.Default_Width, BumperAngle.Default_Height);
		SlimeFactory.BumperAngle.createBL(level.getLevelWidth() - BumperAngle.Default_Width, groundHeight, BumperAngle.Default_Width, BumperAngle.Default_Height).setAngle(-90f);
		
		SlimeFactory.BumperAngle.createBL(level.getLevelWidth() / 2f + middleBlockS / 2f, groundHeight, BumperAngle.Default_Width, BumperAngle.Default_Height);
		SlimeFactory.BumperAngle.createBL(level.getLevelWidth() / 2f - middleBlockS / 2f - BumperAngle.Default_Width, groundHeight, BumperAngle.Default_Width, BumperAngle.Default_Height).setAngle(-90f);
		
		// Laser
		SlimeFactory.LaserGun.createBL(level.getLevelWidth() / 2f - LaserGun.Default_Width / 2f, groundHeight + middleBlockS, LaserGun.Default_Width, LaserGun.Default_Height, "laser", "target", true).setAngle(90f);
		float targetCenterX = level.getLevelWidth() / 2f;
		float targetCenterY = level.getLevelHeight() / 2f;
		SlimeFactory.Target.create(targetCenterX, targetCenterY, 16, 16, "target");
		SlimeFactory.Platform.create(targetCenterX, targetCenterY, 32f, 32f, Platform.Bump);
		
		SlimeFactory.HomeLevelHandler.create().setPortal(spawnPortal);
		
		level.addCustomOverLayer(HomeLayer.get());
		level.setIsHudEnabled(false);
		level.setIsTouchEnabled(false);		
	}

	@Override
	protected void initLevel() {
		this.setId(Id);
		this.setSpecial(true);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.levels.LevelDefinition#getNoScoreStore()
	 */
	@Override
	protected boolean getNoStore() {
		return true;
	}
}
