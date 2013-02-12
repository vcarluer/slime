package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.LevelDifficulty;

public class LiveLongAch extends Achievement {

	private static final String name = "Live long and prosper";
	private static final String description = "Finish survival in easy";

	public LiveLongAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeSurvival && AchievementStatistics.finishedSurvivalDifficulty == LevelDifficulty.Easy;
	}

}
