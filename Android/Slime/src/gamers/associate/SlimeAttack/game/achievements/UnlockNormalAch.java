package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.LevelDifficulty;


public class UnlockNormalAch extends Achievement {

	private static final String name = "Normal mode unlocked";
	private static final String description = "Unlock normal mode";

	public UnlockNormalAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.unlockDifficulty == LevelDifficulty.Normal;
	}
}
