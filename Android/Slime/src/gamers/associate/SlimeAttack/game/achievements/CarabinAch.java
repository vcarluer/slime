package gamers.associate.SlimeAttack.game.achievements;

public class CarabinAch extends Achievement {

	private static final String name = "Carrabin";
	private static final String description = "Jump 50 times in a level";

	public CarabinAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.shotCount >= 50;
	}
}
