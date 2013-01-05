package gamers.associate.Slime.game.achievements;

public class SupermanAch extends Achievement {

	private static final String name = "Superman";
	private static final String description = "Jump before touching first ground";

	public SupermanAch() {
		super(name, description, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.slimyLandCount == 0 && AchievementStatistics.slimyJumpCount == 1;
	}

}
