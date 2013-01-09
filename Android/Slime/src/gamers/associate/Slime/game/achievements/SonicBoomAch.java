package gamers.associate.Slime.game.achievements;

public class SonicBoomAch extends Achievement {

	private static final String name = "Sonic boom";
	private static final String description = "High rotation speed";

	public SonicBoomAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.currentRotation > 50000; // How much?
	}
}
