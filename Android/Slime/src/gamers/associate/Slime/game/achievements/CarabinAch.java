package gamers.associate.Slime.game.achievements;

public class CarabinAch extends Achievement {

	private static final String name = "Carrabin";
	private static final String description = "Make Slimy jump 50 times in a level";

	public CarabinAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.shotCount >= 50;
	}
}
