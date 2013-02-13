package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.achievements.AchievementManager;
import gamers.associate.SlimeAttack.items.base.SpriteSheetFactory;
import gamers.associate.SlimeAttack.items.custom.BecBunsenFactory;
import gamers.associate.SlimeAttack.items.custom.BoxFactory;
import gamers.associate.SlimeAttack.items.custom.BumperAngleFactory;
import gamers.associate.SlimeAttack.items.custom.ButtonFactory;
import gamers.associate.SlimeAttack.items.custom.CameraFactory;
import gamers.associate.SlimeAttack.items.custom.CircularSawFactory;
import gamers.associate.SlimeAttack.items.custom.CocosFactory;
import gamers.associate.SlimeAttack.items.custom.DirectorFactory;
import gamers.associate.SlimeAttack.items.custom.EnergyBallFactory;
import gamers.associate.SlimeAttack.items.custom.EnergyBallGunFactory;
import gamers.associate.SlimeAttack.items.custom.EvacuationPlugfactory;
import gamers.associate.SlimeAttack.items.custom.GateFactory;
import gamers.associate.SlimeAttack.items.custom.GoalPortalFactory;
import gamers.associate.SlimeAttack.items.custom.HomeLevelHandlerFactory;
import gamers.associate.SlimeAttack.items.custom.LaserBeamFactory;
import gamers.associate.SlimeAttack.items.custom.LaserGunFactory;
import gamers.associate.SlimeAttack.items.custom.LavaFactory;
import gamers.associate.SlimeAttack.items.custom.LevelEndFactory;
import gamers.associate.SlimeAttack.items.custom.LightningFactory;
import gamers.associate.SlimeAttack.items.custom.LiquidFactory;
import gamers.associate.SlimeAttack.items.custom.LiquidSurfaceFactory;
import gamers.associate.SlimeAttack.items.custom.MenuNodeFactory;
import gamers.associate.SlimeAttack.items.custom.PhysicPolygonFactory;
import gamers.associate.SlimeAttack.items.custom.PlatformFactory;
import gamers.associate.SlimeAttack.items.custom.RedFactory;
import gamers.associate.SlimeAttack.items.custom.SlimyFactory;
import gamers.associate.SlimeAttack.items.custom.SlimySuccessFactory;
import gamers.associate.SlimeAttack.items.custom.SpawnPortalFactory;
import gamers.associate.SlimeAttack.items.custom.StarCounterFactory;
import gamers.associate.SlimeAttack.items.custom.StarFactory;
import gamers.associate.SlimeAttack.items.custom.TargetFactory;
import gamers.associate.SlimeAttack.items.custom.TeslaCoilFactory;
import gamers.associate.SlimeAttack.items.custom.ThumbnailFactory;
import gamers.associate.SlimeAttack.items.custom.TriggerTimeFactory;
import gamers.associate.SlimeAttack.levels.ILevelBuilder;
import gamers.associate.SlimeAttack.levels.generator.LevelGraphGeneratorCorridor3;
import gamers.associate.SlimeAttack.levels.generator.LevelGraphGeneratorRectangle2;
import gamers.associate.SlimeAttack.levels.generator.LevelGraphGeneratorTutorial;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.annotation.SuppressLint;

import com.badlogic.gdx.physics.box2d.World;

@SuppressLint("DefaultLocale") public abstract class SlimeFactory {
	// Debug vars	
	public static final boolean LogOn = false;
	public static boolean IsLevelSelectionOn = false;
	public static boolean IsLevelSelectionShowButtons = false;

	public static boolean IsLevelDebugMode = false;
	public static boolean IsForceDiffDebug =  false;
	public static int ForceDiff = 8;
	public static int ForceLevel = 1;
	public static boolean IsDebugBlocOn = false;
	public static String ForceBlockPath = "blocsRectangle/e_tr_10.slime";	
	public static final boolean IsForceMaxSurvival = false;
	public static final int MaxSurvival = LevelDifficulty.Hard;
	public static final boolean IsForceMaxWorld = false;
	public static final int MaxWorld = LevelDifficulty.Extrem;
	public static final boolean resetHighScores = false; // Run with this = true and then relaunch with false
	public static final boolean clearLevelInfo = false; // Run with this = true and then relaunch with false
	public static final boolean unlockAll = false;
	public static final boolean unlockAllAchievement = false;
	public static final boolean noUnlockHardcoreGamer = false;
	public static final boolean resetAchievements = false; // if true can not unlock allachievements
	public static final boolean debugSpeed = false;
	public static final boolean isBeta = true;
	
	// end debug ---
	private static final float SGSDensityBase = 1.5f; // Samsung Galaxy S density
	public static ccColor3B ColorSlime = ccColor3B.ccc3(0, 170, 54);
	public static ccColor3B ColorSlimeBorder = ccColor3B.ccc3(0, 62, 8);
	public static ccColor3B ColorSlimeLight = ccColor3B.ccc3(178, 229, 194); // 178, 229, 194
	public static float Density; // surface density
	public static float SGSDensity; // Samsung Galaxy S reference density ratio (SGS used as a reference for layout...) 
	public static final float kCGPointEpsilon = 0.00000012f;
	public static final float FntSize = 64f;	
	public static String slimeFileExt = ".slime";
	private static final int SGSWidthBase = 800;
	public static final int BackableLayer = 555;
	private static float WidthRatio;
	
	public static SlimeAttack ContextActivity;
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
//	public static LevelGraphGeneratorCorridor2 LevelGeneratorCorridor2 = new LevelGraphGeneratorCorridor2(); 
	public static LevelGraphGeneratorCorridor3 LevelGeneratorCorridor3 = new LevelGraphGeneratorCorridor3();
	public static LevelGraphGeneratorRectangle2 LevelGeneratorRectangle2 = new LevelGraphGeneratorRectangle2();
	public static LevelGraphGeneratorTutorial LevelGeneratorTutorial = new LevelGraphGeneratorTutorial();
	public static GameInformation GameInfo;
	public static RedFactory Red = new RedFactory();
	public static GateFactory Gate = new GateFactory();
	public static LiquidFactory Liquid = new LiquidFactory();
	public static LiquidSurfaceFactory LiquidSurface = new LiquidSurfaceFactory();
	public static TriggerTimeFactory TriggerTime = new TriggerTimeFactory();
	public static EvacuationPlugfactory EvacuationPlug = new EvacuationPlugfactory();
	public static DirectorFactory Director = new DirectorFactory();
	public static TeslaCoilFactory TeslaCoil = new TeslaCoilFactory();
	public static LightningFactory Lightning = new LightningFactory();
	public static EnergyBallFactory EnergyBall = new EnergyBallFactory();
	public static EnergyBallGunFactory EnergyBallGun = new EnergyBallGunFactory();
	public static CameraFactory Camera = new CameraFactory();
	public static SlimySuccessFactory SlimySuccess = new SlimySuccessFactory();
	
	public static StarCounterFactory StarCounter = new StarCounterFactory(); // Not attached in attach All but in HudLayer	
	
	public static PackageManager PackageManager;
	public static AchievementManager AchievementManager;	
		
	public static void init() {
		if (PackageManager == null) {
			PackageManager = new PackageManager();
		}
		
		if (AchievementManager == null) {
			AchievementManager = new AchievementManager();
		}
	}
	
	public static void attachAll(Level level, CCNode attachNode, World attachWorld, float attachWorldRatio) {		
		// LevelBuilder = new LevelBuilder();
		GameInfo = new GameInformation();
		LevelBuilder = new LevelBuilderGenerator();
//		LevelGeneratorCorridor2.attach(level);	
		LevelGeneratorCorridor3.attach(level);
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
		Target.attach(level, attachNode);
		LaserBeam.attach(level, attachNode, attachWorld, attachWorldRatio);
		Sprite.attach(level, attachNode);
		Red.attach(level, attachNode, attachWorld, attachWorldRatio);
		Gate.attach(level, attachNode);
		Liquid.attach(level, attachNode, attachWorld, attachWorldRatio);
		LiquidSurface.attach(level, attachNode, attachWorld, attachWorldRatio);
		TriggerTime.attach(level);
		EvacuationPlug.attach(level, attachNode, attachWorld, attachWorldRatio);
		Director.attach(level);
		TeslaCoil.attach(level, attachNode, attachWorld, attachWorldRatio);
		EnergyBall.attach(level, attachNode, attachWorld, attachWorldRatio);
		EnergyBallGun.attach(level, attachNode, attachWorld, attachWorldRatio);
		Camera.attach(level, attachNode, attachWorld, attachWorldRatio);
		SlimySuccess.attach(level, attachNode);
		
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
		StarCounter.detach();
		EvacuationPlug.detach();
		Director.detach();
		TeslaCoil.detach();
		EnergyBall.detach();
		EnergyBallGun.detach();
		Camera.detach();
		SlimySuccess.detach();
		
//		LevelGeneratorCorridor2.detach();
		LevelGeneratorCorridor3.detach();
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
		StarCounter.destroy();
		EvacuationPlug.destroy();
		TeslaCoil.destroy();
		EnergyBall.destroy();
		EnergyBallGun.destroy();
		Camera.destroy();
		SlimySuccess.destroy();
		
//		LevelGeneratorCorridor2.destroy();
		LevelGeneratorCorridor3.destroy();
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
	
	public static void triggerZoneColor(GL10 gl) {
		gl.glColor4f(0f, 0f, 1.0f, 0.07f);		
	}
	
	public static void triggerZoneAlertColor(GL10 gl) {
		gl.glColor4f(1.0f, 0f, 0f, 0.07f);
	}
	
	public static final float getScreenMidX() {
		return CCDirector.sharedDirector().winSize().getWidth() / 2f;
	}
	
	public static final float getScreenMidY() {
		return CCDirector.sharedDirector().winSize().getHeight() / 2f;
	}
	
	public static class Log {
		public static void d(String tag, String line) {
			if (LogOn) {
				android.util.Log.d(SlimeAttack.TAG, line);
			}			
		}
		
		public static void e(String tag, String line) {
			if (LogOn) {
				android.util.Log.e(SlimeAttack.TAG, line);
			}			
		}
	}
	
	public static CCLabel getLabel(String text, float size) {
		return CCLabel.makeLabel(text.toUpperCase(), "fonts/Slime.ttf", size);
	}
	
	public static CCLabel getLabel(String text) {
		return getLabel(text, FntSize);
	}
	
	public static float getWidthRatio() {
		if (WidthRatio == 0) {
			WidthRatio = CCDirector.sharedDirector().winSize().getWidth() / SGSWidthBase;
		}
		
		return WidthRatio;
	}
	
	public static ccColor4B getColorLight(int alpha) {
		return ccColor4B.ccc4(178, 229, 194, alpha);
	}
	
	public static ccColor4B getColorBorder(int alpha) {
		return ccColor4B.ccc4(0, 62, 8, alpha);
	}
	
	public static void moveToZeroY(float delayTime, CCLayer layer) {
		layer.setPosition(layer.getPosition().x, CCDirector.sharedDirector().winSize().height);
		CCDelayTime delay = CCDelayTime.action(0.4f + delayTime);
		CCMoveTo moveTo = CCMoveTo.action(0.5f, CGPoint.make(layer.getPosition().x, 0));
		CCSequence seq = CCSequence.actions(delay, moveTo);
		layer.runAction(seq);
	}
	
	public static void moveToZeroYFromBottom(float delayTime, CCLayer layer) {
		layer.setPosition(layer.getPosition().x, - CCDirector.sharedDirector().winSize().height);
		CCDelayTime delay = CCDelayTime.action(0.4f + delayTime);
		CCMoveTo moveTo = CCMoveTo.action(0.5f, CGPoint.make(layer.getPosition().x, 0));
		CCSequence seq = CCSequence.actions(delay, moveTo);
		layer.runAction(seq);
	}
	
	public static void moveToZeroFromBottom(float delayTime, CCLayer layer, float height) {
		layer.setPosition(layer.getPosition().x, - height);
		CCDelayTime delay = CCDelayTime.action(0.4f + delayTime);
		CCMoveTo moveTo = CCMoveTo.action(0.5f, CGPoint.make(layer.getPosition().x, 0));
		CCSequence seq = CCSequence.actions(delay, moveTo);
		layer.runAction(seq);
	}

	public static void playMenuMusic() {
		Sounds.playMusic(R.raw.menumusic, true);
	}

	public static void playCreditMusic() {
		Sounds.playMusic(R.raw.slimy_credits, true);
	}
}
