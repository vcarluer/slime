package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.LevelDifficulty;


public class UnlockHardAch extends Achievement {

	private static final String name = "Hard mode unlocked";
	private static final String description = "Unlock hard mode";

	public UnlockHardAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.unlockDifficulty == LevelDifficulty.Hard;
	}
}
