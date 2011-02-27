package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;

import com.badlogic.gdx.physics.box2d.World;

public abstract class SlimeFactory {
	public static boolean isAttached;
	public static SlimyFactory Slimy = new SlimyFactory();
	public static SpawnPortalFactory SpawnPortal = new SpawnPortalFactory();
	public static PlatformFactory Platform = new PlatformFactory();
	public static GoalPortalFactory GoalPortal = new GoalPortalFactory();
	public static BumperFactory Bumper = new BumperFactory();
		
	public static void attachAll(CCNode attachNode, World attachWorld, float attachWorldRatio) {
		Slimy.attach(attachNode, attachWorld, attachWorldRatio);
		SpawnPortal.attach(attachNode);
		Platform.attach(attachNode, attachWorld, attachWorldRatio);
		GoalPortal.attach(attachNode, attachWorld, attachWorldRatio);
		Bumper.attach(attachNode, attachWorld, attachWorldRatio);
		isAttached = true;
	}
	
	public static void detachAll() {
		Slimy.detach();
		SpawnPortal.detach();
		Platform.detach();
		GoalPortal.detach();
		Bumper.detach();
		isAttached = false;
	}
	
	public static void destroyAll() {
		Slimy.destroy();
		SpawnPortal.destroy();
		Platform.destroy();
		GoalPortal.destroy();
		Bumper.destroy();
		isAttached = false;
	}
}
