package gamers.associate.SlimeAttack.game.achievements;

public class SonicBoomAch extends Achievement {

	private static final String name = "Sonic boom";
	private static final String description = "High rotation speed";

	public SonicBoomAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isMode() && Math.abs(AchievementStatistics.currentRotation) > 50; // How much?
	}
}
