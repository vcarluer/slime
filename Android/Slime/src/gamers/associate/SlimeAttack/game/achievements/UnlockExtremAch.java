package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.LevelDifficulty;


public class UnlockExtremAch extends Achievement {

	private static final String name = "Extreme mode unlocked";
	private static final String description = "Unlock extreme mode";

	public UnlockExtremAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.unlockDifficulty == LevelDifficulty.Extrem;
	}
}
