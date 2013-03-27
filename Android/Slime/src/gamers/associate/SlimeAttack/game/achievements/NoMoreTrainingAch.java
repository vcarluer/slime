package gamers.associate.SlimeAttack.game.achievements;

public class NoMoreTrainingAch extends Achievement {
	private static final String name = "No more training needed!";
	private static final String description = "Finish the tutorial";

	public NoMoreTrainingAch() {
		super(name, description, false);
	}
	
	@Override
	protected boolean testInternal() {
		return AchievementStatistics.levelNum == 8;
	}

}
