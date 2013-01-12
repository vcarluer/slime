package gamers.associate.Slime.game.achievements;

public class CallMeMaxAch extends Achievement {

	private static final String name = "Call me Max";
	private static final String description = "Jump after 5 seconds air bullet time";

	public CallMeMaxAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.shotSpeed >= 5000 && !AchievementStatistics.isLanded;
	}

}