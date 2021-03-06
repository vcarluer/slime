package gamers.associate.SlimeAttack.levels;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.custom.BumperAngle;
import gamers.associate.SlimeAttack.items.custom.LaserGun;
import gamers.associate.SlimeAttack.items.custom.LiquidSurface;
import gamers.associate.SlimeAttack.items.custom.Platform;
import gamers.associate.SlimeAttack.items.custom.SpawnPortal;
import gamers.associate.SlimeAttack.layers.HomeLayer;

import org.cocos2d.nodes.CCDirector;

public class LevelHome extends LevelDefinitionHardCoded {
	public static String Id = "Home";		
	
	@Override
	public boolean buildLevel(Level level) {
		level.setLevelSize(
				CCDirector.sharedDirector().winSize().getWidth() * 2,
				CCDirector.sharedDirector().winSize().getHeight() * 2);
				
		SpawnPortal spawnPortal = SlimeFactory.SpawnPortal.createAndMove(
				level.getLevelWidth() / 2f, 
				level.getLevelHeight() + 32,
				level.getLevelWidth() / 2f,
				5);
				
		// Ground
		float groundHeight = 128f;
		float groundWidth = level.getLevelWidth() / 4f;
		float acidWidth = level.getLevelWidth() / 12f;
		float groundMiddle = level.getLevelWidth() - 2 * groundWidth - 2* acidWidth;
		float acidBaseHeight = 32f;
		SlimeFactory.Platform.createWallBL("None", 0, 0, groundWidth, groundHeight);
		
		SlimeFactory.Platform.createWallBL("None", groundWidth, 0, acidWidth, acidBaseHeight);		
		SlimeFactory.Liquid.createBL(groundWidth, acidBaseHeight, acidWidth, groundHeight - LiquidSurface.Default_Height - acidBaseHeight);
		SlimeFactory.LiquidSurface.createBL(groundWidth, acidBaseHeight + groundHeight - acidBaseHeight - LiquidSurface.Default_Height, acidWidth, LiquidSurface.Default_Height);
				
		SlimeFactory.Platform.createWallBL("None", groundWidth + acidWidth, 0, groundMiddle, groundHeight);
//		float tubeW = 32f;
//		SlimeFactory.Sprite.createBL(level.getLevelWidth() / 2f - tubeW / 2f, groundHeight / 2f - groundMiddle / 2f, tubeW, groundMiddle, "tank", "tank-tube-filled", 1).setAngle(90f);
		
		SlimeFactory.Platform.createWallBL("None", groundWidth + acidWidth + groundMiddle, 0, acidWidth, acidBaseHeight);
		SlimeFactory.Liquid.createBL(groundWidth + acidWidth + groundMiddle, acidBaseHeight, acidWidth, groundHeight - LiquidSurface.Default_Height - acidBaseHeight);
		SlimeFactory.LiquidSurface.createBL(groundWidth + acidWidth + groundMiddle, acidBaseHeight + groundHeight - acidBaseHeight - LiquidSurface.Default_Height, acidWidth, LiquidSurface.Default_Height);
		
		SlimeFactory.Platform.createWallBL("None", groundWidth + acidWidth + groundMiddle + acidWidth, 0, groundWidth, groundHeight);
		
		float middleBlockS = 64f;
		SlimeFactory.Platform.createWallBL("None", level.getLevelWidth() / 2f - middleBlockS / 2f, groundHeight, middleBlockS, middleBlockS);
		
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
		SlimeFactory.Target.create("target", targetCenterX, targetCenterY, 16, 16);
		SlimeFactory.Platform.create("centerPlat", targetCenterX, targetCenterY, 32f, 32f, Platform.Bump);
		
//		SlimeFactory.TeslaCoil.create(targetCenterX, targetCenterY + 16f + TeslaCoil.Default_Height / 2f + 50f, TeslaCoil.Default_Width, TeslaCoil.Default_Height, "tesla", true, 400f);
//		// SlimeFactory.EnergyBall.create("ball", 50, targetCenterY, 0, 0);		
//		SlimeFactory.EnergyBallGun.createBL(0, targetCenterY, EnergyBallGun.Default_Width, EnergyBallGun.Default_Height, "enGun", "target", true, 0, 0).setAngle(180f);		
//		
//		SlimeFactory.Target.create("target2", 0, targetCenterY - 100f, 8, 8);		
//		SlimeFactory.LaserGun.create(targetCenterX - LaserGun.Default_Width, targetCenterY - 100f, LaserGun.Default_Width, LaserGun.Default_Height, "laser2", "target2", false);
//		SlimeFactory.Camera.createBL("camera", 0, targetCenterY + 100f, Camera.Default_Width, Camera.Default_Height, "laser2", true, 0, 0, 2, 30);		
		SlimeFactory.HomeLevelHandler.create().setPortal(spawnPortal);
		
		HomeLayer layer = HomeLayer.get();
		layer.setSpawner(spawnPortal);
		level.addCustomOverLayer(layer);
		level.setIsHudEnabled(false);
		level.setIsTouchEnabled(false);
		
		return true;
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
