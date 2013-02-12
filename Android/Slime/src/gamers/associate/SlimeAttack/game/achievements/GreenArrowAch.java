package gamers.associate.SlimeAttack.game.achievements;

public class GreenArrowAch extends Achievement {

	private static final String name = "Green Arrow";
	private static final String description = "One shot in tutorial";

	public GreenArrowAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isTuto && AchievementStatistics.shotCount == 1;
	}

}
