package gamers.associate.Slime.game.achievements;

public class GreenFlashAch extends Achievement {

	private static final String name = "Are you green flash?";
	private static final String description = "High speed";

	public GreenFlashAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isMode() && AchievementStatistics.currentSpeed > 1500; // How much?
	}
}
