package gamers.associate.Slime.game.achievements;


public class TheImmortalAch extends Achievement {

	private static final String name = "The immortal";
	private static final String description = "5 consecutive wins";

	public TheImmortalAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return !AchievementStatistics.isTuto && AchievementStatistics.isModeStory && AchievementStatistics.consecutiveWin >= 5;
	}
}
