package gamers.associate.SlimeAttack.game.achievements;

public class TimeOutAch extends Achievement {

	private static final String name = "Time is running out";
	private static final String description = "Lose by time out";

	public TimeOutAch() {
		super(name, description, true, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeStory && AchievementStatistics.leftTime == 0;
	}

}
