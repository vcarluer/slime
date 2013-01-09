package gamers.associate.Slime.game.achievements;

import gamers.associate.Slime.game.LevelDifficulty;

public class NormalSlimeAch extends Achievement {

	private static final String name = "I'm a normal slime baby";
	private static final String description = "Finish survival in normal";

	public NormalSlimeAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeSurvival && AchievementStatistics.finishedSurvivalDifficulty == LevelDifficulty.Normal;
	}

}
