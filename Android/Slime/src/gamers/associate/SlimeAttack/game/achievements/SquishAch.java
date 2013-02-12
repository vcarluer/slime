package gamers.associate.SlimeAttack.game.achievements;

public class SquishAch extends Achievement {

	private static final String name = "Squish";
	private static final String description = "Die splashed";

	public SquishAch() {
		super(name, description, false, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isMode() && AchievementStatistics.splashed && AchievementStatistics.leftTime > 0;
	}

}
