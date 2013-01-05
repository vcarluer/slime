package gamers.associate.Slime.game.achievements;


public class LikeABirdAch extends Achievement {

	private static final String name = "Like a bird";
	private static final String description = "Finish a level without touching the ground";

	public LikeABirdAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return !AchievementStatistics.isTuto && AchievementStatistics.landCount == 0;
	}
}
