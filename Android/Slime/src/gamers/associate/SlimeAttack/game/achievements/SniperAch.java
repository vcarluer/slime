package gamers.associate.SlimeAttack.game.achievements;

public class SniperAch extends Achievement {

	private static final String name = "Sniper";
	private static final String description = "Really long jump";

	public SniperAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isMode() && AchievementStatistics.jumpDistance > 2000;
	}
}
