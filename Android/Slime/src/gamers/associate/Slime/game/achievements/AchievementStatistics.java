package gamers.associate.Slime.game.achievements;

public class AchievementStatistics {
	// Not reseted here
	public static boolean isModeStory;
	public static boolean isModeSurvival;
	public static int levelDiff;
	public static int inARow;
	public static int levelNum;
	public static int consecutiveWin;
	public static boolean isBoss;
	public static boolean isTuto;
	public static int totalStar;
	public static int neededBonus;
	public static float startTime;
	public static float leftTime;
	
	// reseted here
	public static int landCount;
	public static int shotCount;
	public static float winLeftTime;
	public static long shotTime;
	public static int shotInAir;
	public static int unlockDifficulty;
	public static boolean enterCriticZone;
	public static boolean exitCriticZone;
	public static boolean electrified;
	public static boolean dissolved;
	public static boolean sliced;
	public static boolean splashed;
	public static boolean burned;
	public static boolean isLanded;
	public static int finishedSurvivalDifficulty;
	
	public static void initAll() {
		landCount = 0;
		shotCount = 0;
		winLeftTime = 0f;
		shotTime = 0;
		shotInAir = 0;
		unlockDifficulty = 0;
		enterCriticZone = false;
		exitCriticZone = false;
		electrified = false;
		dissolved = false;
		sliced = false;
		splashed = false;
		burned = false;
		isLanded = false;
		finishedSurvivalDifficulty = 0;
	}
}
