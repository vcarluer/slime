package gamers.associate.Slime.game.achievements;

public class CallMeMax extends Achievement {

	private static final String name = "Call me Max";
	private static final String description = "Jump after 5 seconds air bullet time";

	public CallMeMax() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.shotTime >= 5000 && !AchievementStatistics.isLanded;
	}

}
