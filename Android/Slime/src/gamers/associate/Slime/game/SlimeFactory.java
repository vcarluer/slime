package gamers.associate.Slime.game;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.items.base.SpriteSheetFactory;
import gamers.associate.Slime.items.custom.BecBunsenFactory;
import gamers.associate.Slime.items.custom.BoxFactory;
import gamers.associate.Slime.items.custom.BumperAngleFactory;
import gamers.associate.Slime.items.custom.ButtonFactory;
import gamers.associate.Slime.items.custom.CircularSawFactory;
import gamers.associate.Slime.items.custom.CocosFactory;
import gamers.associate.Slime.items.custom.GateFactory;
import gamers.associate.Slime.items.custom.GoalPortalFactory;
import gamers.associate.Slime.items.custom.HomeLevelHandlerFactory;
import gamers.associate.Slime.items.custom.LaserBeamFactory;
import gamers.associate.Slime.items.custom.LaserGunFactory;
import gamers.associate.Slime.items.custom.LavaFactory;
import gamers.associate.Slime.items.custom.LevelEndFactory;
import gamers.associate.Slime.items.custom.LiquidFactory;
import gamers.associate.Slime.items.custom.LiquidSurfaceFactory;
import gamers.associate.Slime.items.custom.MenuNodeFactory;
import gamers.associate.Slime.items.custom.PhysicPolygonFactory;
import gamers.associate.Slime.items.custom.PlatformFactory;
import gamers.associate.Slime.items.custom.RedFactory;
import gamers.associate.Slime.items.custom.SlimyFactory;
import gamers.associate.Slime.items.custom.SpawnPortalFactory;
import gamers.associate.Slime.items.custom.StarFactory;
import gamers.associate.Slime.items.custom.TargetFactory;
import gamers.associate.Slime.items.custom.ThumbnailFactory;
import gamers.associate.Slime.items.custom.TriggerTimeFactory;
import gamers.associate.Slime.levels.ILevelBuilder;
import gamers.associate.Slime.levels.generator.LevelGraphGeneratorCorridor;
import gamers.associate.Slime.levels.generator.LevelGraphGeneratorRectangle;
import gamers.associate.Slime.levels.generator.LevelGraphGeneratorRectangle2;
import gamers.associate.Slime.levels.generator.LevelGraphGeneratorTutorial;
import gamers.associate.Slime.levels.generator.hardcoded.BlocHardInit;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.ccColor3B;

import android.app.Activity;

import com.badlogic.gdx.physics.box2d.World;

public abstract class SlimeFactory {
	private static final float SGSDensityBase = 1.5f; // Samsung Galaxy S density
	public static ccColor3B ColorSlime = ccColor3B.ccc3(0, 170, 54);
	public static ccColor3B ColorSlimeBorder = ccColor3B.ccc3(0, 62, 8);
	public static ccColor3B ColorSlimeLight = ccColor3B.ccc3(255, 255, 255); // 178, 229, 194
	public static float Density; // surface density
	public static float SGSDensity; // Samsung Galaxy S reference density ratio (SGS used as a reference for layout...) 
	
	public static Slime ContextActivity;
	public static ILevelBuilder LevelBuilder;
	public static boolean isAttached;
	public static SlimyFactory Slimy = new SlimyFactory();
	public static SpawnPortalFactory SpawnPortal = new SpawnPortalFactory();
	public static PlatformFactory Platform = new PlatformFactory();
	public static GoalPortalFactory GoalPortal = new GoalPortalFactory();
	public static BumperAngleFactory BumperAngle = new BumperAngleFactory();
	public static LevelEndFactory LevelEnd = new LevelEndFactory();
	public static HomeLevelHandlerFactory HomeLevelHandler = new HomeLevelHandlerFactory();
	public static LavaFactory Lava = new LavaFactory();
	public static BoxFactory Box = new BoxFactory();
	public static PhysicPolygonFactory Polygon = new PhysicPolygonFactory();
	public static ThumbnailFactory Thumbnail = new ThumbnailFactory();
	public static BecBunsenFactory BecBunsen = new BecBunsenFactory();
	public static ButtonFactory Button = new ButtonFactory();
	public static CircularSawFactory CircularSaw = new CircularSawFactory();
	public static StarFactory Star = new StarFactory();
	public static MenuNodeFactory MenuNode = new MenuNodeFactory();
	public static LaserGunFactory LaserGun = new LaserGunFactory();
	public static TargetFactory Target = new TargetFactory();
	public static LaserBeamFactory LaserBeam = new LaserBeamFactory();
	public static CocosFactory Sprite = new CocosFactory();
	public static LevelGraphGeneratorCorridor LevelGeneratorCorridor = new LevelGraphGeneratorCorridor(); 
	public static LevelGraphGeneratorRectangle LevelGeneratorRectangle = new LevelGraphGeneratorRectangle();
	public static LevelGraphGeneratorRectangle2 LevelGeneratorRectangle2 = new LevelGraphGeneratorRectangle2();
	public static LevelGraphGeneratorTutorial LevelGeneratorTutorial = new LevelGraphGeneratorTutorial();
	public static GameInformation GameInfo;
	public static RedFactory Red = new RedFactory();
	public static GateFactory Gate = new GateFactory();
	public static LiquidFactory Liquid = new LiquidFactory();
	public static LiquidSurfaceFactory LiquidSurface = new LiquidSurfaceFactory();
	public static TriggerTimeFactory TriggerTime = new TriggerTimeFactory();
		
	public static void attachAll(Level level, CCNode attachNode, World attachWorld, float attachWorldRatio) {		
		// LevelBuilder = new LevelBuilder();
		GameInfo = new GameInformation();
		LevelBuilder = new LevelBuilderGenerator();
		LevelGeneratorCorridor.attach(level);		
		LevelGeneratorRectangle.attach(level);		
		LevelGeneratorRectangle2.attach(level);
		LevelGeneratorTutorial.attach(level);
		
		
		Slimy.attach(level, attachNode, attachWorld, attachWorldRatio);
		SpawnPortal.attach(level, attachNode);
		Platform.attach(level, attachNode, attachWorld, attachWorldRatio);
		GoalPortal.attach(level, attachNode, attachWorld, attachWorldRatio);
		BumperAngle.attach(level, attachNode, attachWorld, attachWorldRatio);
		LevelEnd.attach(level, attachNode, attachWorld, attachWorldRatio);
		HomeLevelHandler.attach(level);
		Lava.attach(level, attachNode, attachWorld, attachWorldRatio);
		Box.attach(level, attachNode, attachWorld, attachWorldRatio);
		Polygon.attach(level, attachNode, attachWorld, attachWorldRatio);
		Thumbnail.attach(level, attachNode);
		BecBunsen.attach(level, attachNode, attachWorld, attachWorldRatio);
		Button.attach(level, attachNode, attachWorld, attachWorldRatio);
		CircularSaw.attach(level, attachNode, attachWorld, attachWorldRatio);
		Star.attach(level, attachNode, attachWorld, attachWorldRatio);
		MenuNode.attach(level, attachNode);
		LaserGun.attach(level, attachNode, attachWorld, attachWorldRatio);
		Target.attach(level);
		LaserBeam.attach(level, attachNode, attachWorld, attachWorldRatio);
		Sprite.attach(level, attachNode);
		Red.attach(level, attachNode, attachWorld, attachWorldRatio);
		Gate.attach(level, attachNode);
		Liquid.attach(level, attachNode, attachWorld, attachWorldRatio);
		LiquidSurface.attach(level, attachNode, attachWorld, attachWorldRatio);
		TriggerTime.attach(level);
		
		SpriteSheetFactory.attachAll(attachNode);
		isAttached = true;
	}
	
	public static void detachAll() {
		Slimy.detach();
		SpawnPortal.detach();
		Platform.detach();
		GoalPortal.detach();
		BumperAngle.detach();
		LevelEnd.detach();
		HomeLevelHandler.detach();
		Lava.detach();
		Box.detach();
		Polygon.detach();
		Thumbnail.detach();
		BecBunsen.detach();
		Button.detach();
		CircularSaw.detach();
		Star.detach();
		MenuNode.detach();
		LaserGun.detach();
		Target.detach();
		LaserBeam.detach();
		Sprite.detach();
		Red.detach();
		Gate.detach();
		Liquid.detach();
		LiquidSurface.detach();
		TriggerTime.detach();
		
		LevelGeneratorCorridor.detach();
		LevelGeneratorRectangle.detach();
		LevelGeneratorRectangle2.detach();
		LevelGeneratorTutorial.detach();
		
		SpriteSheetFactory.detachAll();
		isAttached = false;
	}
	
	public static void destroyAll() {
		Slimy.destroy();
		SpawnPortal.destroy();
		Platform.destroy();
		GoalPortal.destroy();
		BumperAngle.destroy();
		LevelEnd.destroy();		
		Lava.destroy();
		Box.destroy();
		Polygon.destroy();
		Thumbnail.destroy();
		BecBunsen.destroy();
		Button.destroy();
		CircularSaw.destroy();
		Star.destroy();
		MenuNode.destroy();
		LaserGun.destroy();
		LaserBeam.destroy();
		Sprite.destroy();
		Red.destroy();
		Gate.destroy();
		Liquid.destroy();
		LiquidSurface.destroy();
		
		LevelGeneratorCorridor.destroy();
		LevelGeneratorRectangle.destroy();
		LevelGeneratorRectangle2.destroy();
		LevelGeneratorTutorial.destroy();
		
		SpriteSheetFactory.destroy();
		isAttached = false;
	}
	
	public static void setDensity(float density) {
		// Should never happen...
		if (density <= 0) {
			Density = 1.0f;
		} else {
			Density = density;
		}
				
		SGSDensity = Density / SGSDensityBase;
	}
}
