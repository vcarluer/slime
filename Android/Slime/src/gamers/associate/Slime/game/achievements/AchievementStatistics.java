package gamers.associate.Slime.game.achievements;

public class AchievementStatistics {
	public static int slimyLandCount;
	public static int slimyJumpCount;
	public static boolean isBoss;
	public static boolean isTuto;
	public static float winLeftTime;
	public static int consecutiveWin;
	public static long shotTime;
	public static int shotInAir;
	
	public static void initAll() {
		slimyLandCount = 0;
		slimyJumpCount = 0;
		isBoss = false;
		isTuto = false;
		winLeftTime = 0f;
		shotTime = 0;
		shotInAir = 0;
	}
}
