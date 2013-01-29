package gamers.associate.Slime.game.achievements;

import gamers.associate.Slime.game.LevelDifficulty;

public class AreYouSeriousAch extends Achievement {

	private static final String name = "Are you serious?";
	private static final String description = "More than 80 level in extreme";

	public AreYouSeriousAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeSurvival && AchievementStatistics.levelDiff == LevelDifficulty.Extrem && AchievementStatistics.levelNum >= 81;
	}

}
