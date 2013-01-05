package gamers.associate.Slime.game.achievements;

public class RobinHoodAch extends Achievement {

	private static final String name = "Robin Hood";
	private static final String description = "Finish a level with less than 3 shots";

	public RobinHoodAch() {
		super(name, description, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean testInternal() {
		return !AchievementStatistics.isTuto && AchievementStatistics.slimyJumpCount <= 3;
	}
}
