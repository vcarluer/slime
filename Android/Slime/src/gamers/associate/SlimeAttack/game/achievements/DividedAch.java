package gamers.associate.SlimeAttack.game.achievements;

public class DividedAch extends Achievement {

	private static final String name = "Should I not be divided?";
	private static final String description = "Die sliced";

	public DividedAch() {
		super(name, description, false, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isMode() && AchievementStatistics.sliced;
	}

}
