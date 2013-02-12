package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.Rank;

public class AchievementStatistics {
	// Not reseted here
	public static boolean isModeStory;
	public static boolean isModeSurvival;
	public static int levelDiff;
	public static int inARow;
	public static int levelNum;
	public static int consecutiveNoTutoWin;
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
	public static long shotSpeed;
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
	public static Rank lastRank;
	public static float currentSpeed;
	public static float currentRotation;
	public static long jumpDuration;
	public static float jumpDistance;
	public static long shotTime;
	public static boolean zoomChanged;
	public static boolean bossKilled;
	public static long lastJumpStartTime;
	public static boolean miniRedKilled;
	public static int bonusTaken;
	public static boolean buttonPushed;
	
	public static void initAll() {
		landCount = 0;
		shotCount = 0;
		winLeftTime = 0f;
		shotSpeed = 0;
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
		lastRank = Rank.None;
		currentSpeed = 0f;
		currentRotation = 0f;
		jumpDuration = 0;
		jumpDistance = 0f;
		shotTime = 0;
		zoomChanged = false;
		bossKilled = false;
		lastJumpStartTime = 0;
		miniRedKilled = false;
		bonusTaken = 0;
		buttonPushed = false;
	}
	
	public static boolean isMode() {
		return isModeStory || isModeSurvival;
	}

	public static void setLastRank(Rank rank) {
		lastRank = rank;
	}
}
