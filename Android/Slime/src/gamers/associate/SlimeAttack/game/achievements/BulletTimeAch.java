package gamers.associate.SlimeAttack.game.achievements;

public class BulletTimeAch extends Achievement {

	private static final String name = "Bullet time";
	private static final String description = "Find the bullet time gameplay";

	public BulletTimeAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.shotSpeed >= 700;
	}

}
