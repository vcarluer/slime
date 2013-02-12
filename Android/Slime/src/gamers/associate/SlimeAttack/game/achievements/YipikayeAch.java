package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.LevelDifficulty;

public class YipikayeAch extends Achievement {

	private static final String name = "Yipikayay!";
	private static final String description = "Finish survival in hard";

	public YipikayeAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeSurvival && AchievementStatistics.finishedSurvivalDifficulty == LevelDifficulty.Hard;
	}

}
