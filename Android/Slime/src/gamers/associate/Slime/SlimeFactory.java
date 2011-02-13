package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;

import com.badlogic.gdx.physics.box2d.World;

public abstract class SlimeFactory {
	public static SlimyFactory Slimy = new SlimyFactory();
	public static SpawnPortalFactory SpawnPortal = new SpawnPortalFactory();
	public static PlatformFactory Platform = new PlatformFactory();
	public static GoalPortalFactory GoalPortal = new GoalPortalFactory();
	public static BumperFactory Bumper = new BumperFactory();
		
	public static void attachAll(CCNode attachNode, World attachWorld, float attachWorldRatio) {
		Slimy.Attach(attachNode, attachWorld, attachWorldRatio);
		SpawnPortal.Attach(attachNode);
		Platform.Attach(attachNode, attachWorld, attachWorldRatio);
		GoalPortal.Attach(attachNode, attachWorld, attachWorldRatio);
		Bumper.Attach(attachNode, attachWorld, attachWorldRatio);
	}
}
