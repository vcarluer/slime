package gamers.associate.SlimeAttack.game.achievements;

public class DontStopAch extends Achievement {

	private static final String name = "Don't stop me now";
	private static final String description = "10 consecutives bullet time";

	public DontStopAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return !AchievementStatistics.isTuto && AchievementStatistics.shotInAir >= 10;
	}
}
