package gamers.associate.Slime.game.achievements;

public class SupermanAch extends Achievement {

	private static final String name = "Superman";
	private static final String description = "Jump before touching first ground";

	public SupermanAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.landCount == 0 && AchievementStatistics.shotCount == 1;
	}

}
