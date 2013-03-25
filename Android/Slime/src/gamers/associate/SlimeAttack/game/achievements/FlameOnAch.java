package gamers.associate.SlimeAttack.game.achievements;

public class FlameOnAch extends Achievement {

	private static final String name = "Flame on!";
	private static final String description = "Die on fire";

	public FlameOnAch() {
		super(name, description, false, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isMode() && AchievementStatistics.burned;
	}

}
