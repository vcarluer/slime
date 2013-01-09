package gamers.associate.Slime.game.achievements;

public class LuckyLukeAch extends Achievement {

	private static final String name = "Lucky Luke";
	private static final String description = "Quick shot";

	public LuckyLukeAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.shotSpeed > 0 && AchievementStatistics.shotSpeed < 200 && AchievementStatistics.lastJumpStartTime > 0;
	}
}
