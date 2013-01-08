package gamers.associate.Slime.game.achievements;

public class FlameOnAch extends Achievement {

	private static final String name = "Flame on!";
	private static final String description = "Die by fire";

	public FlameOnAch() {
		super(name, description, false, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.burned;
	}

}
