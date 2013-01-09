package gamers.associate.Slime.game.achievements;

public class TheDoctorAch extends Achievement {

	private static final String name = "The Doctor";
	private static final String description = "More time at end than at the begining";

	public TheDoctorAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeStory && !AchievementStatistics.isTuto && AchievementStatistics.winLeftTime > AchievementStatistics.startTime;
	}

}
