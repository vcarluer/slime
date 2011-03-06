package gamers.associate.Slime;

import gamers.associate.Slime.items.BumperFactory;
import gamers.associate.Slime.items.GoalPortalFactory;
import gamers.associate.Slime.items.HomeLevelHandlerFactory;
import gamers.associate.Slime.items.LevelEndFactory;
import gamers.associate.Slime.items.PlatformFactory;
import gamers.associate.Slime.items.SlimyFactory;
import gamers.associate.Slime.items.SpawnCannonFactory;
import gamers.associate.Slime.items.SpawnPortalFactory;

import org.cocos2d.nodes.CCNode;

import com.badlogic.gdx.physics.box2d.World;

public abstract class SlimeFactory {
	public static boolean isAttached;
	public static SlimyFactory Slimy = new SlimyFactory();
	public static SpawnPortalFactory SpawnPortal = new SpawnPortalFactory();
	public static PlatformFactory Platform = new PlatformFactory();
	public static GoalPortalFactory GoalPortal = new GoalPortalFactory();
	public static BumperFactory Bumper = new BumperFactory();
	public static SpawnCannonFactory Cannon = new SpawnCannonFactory();
	public static LevelEndFactory LevelEnd = new LevelEndFactory();
	public static HomeLevelHandlerFactory HomeLevelHandler = new HomeLevelHandlerFactory();
		
	public static void attachAll(Level level, CCNode attachNode, World attachWorld, float attachWorldRatio) {
		Slimy.attach(level, attachNode, attachWorld, attachWorldRatio);
		SpawnPortal.attach(level, attachNode);
		Platform.attach(level, attachNode, attachWorld, attachWorldRatio);
		GoalPortal.attach(level, attachNode, attachWorld, attachWorldRatio);
		Bumper.attach(level, attachNode, attachWorld, attachWorldRatio);
		Cannon.attach(level, attachNode, attachWorld, attachWorldRatio);
		LevelEnd.attach(level, attachNode, attachWorld, attachWorldRatio);
		HomeLevelHandler.attach(level);
		isAttached = true;
	}
	
	public static void detachAll() {
		Slimy.detach();
		SpawnPortal.detach();
		Platform.detach();
		GoalPortal.detach();
		Bumper.detach();
		Cannon.detach();
		LevelEnd.detach();
		HomeLevelHandler.detach();
		isAttached = false;
	}
	
	public static void destroyAll() {
		Slimy.destroy();
		SpawnPortal.destroy();
		Platform.destroy();
		GoalPortal.destroy();
		Bumper.destroy();
		Cannon.destroy();
		LevelEnd.destroy();		
		isAttached = false;
	}
}
