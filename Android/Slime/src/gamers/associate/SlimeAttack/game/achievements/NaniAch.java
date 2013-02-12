package gamers.associate.SlimeAttack.game.achievements;


public class NaniAch extends Achievement {

	private static final String name = "Nani";
	private static final String description = "Die on first jump";

	public NaniAch() {
		super(name, description, true, true);
	}

	@Override
	protected boolean testInternal() {
		return !AchievementStatistics.isTuto && AchievementStatistics.shotCount == 1;
	}
}
