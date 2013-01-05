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
	
	// reseted here
	public static int landCount;
	public static int shotCount;
	
	public static float winLeftTime;
	public static long shotTime;
	public static int shotInAir;
	public static int unlockDifficulty;
	
	public static void initAll() {
		landCount = 0;
		shotCount = 0;
		winLeftTime = 0f;
		shotTime = 0;
		shotInAir = 0;
		unlockDifficulty = 0;
	}
}
