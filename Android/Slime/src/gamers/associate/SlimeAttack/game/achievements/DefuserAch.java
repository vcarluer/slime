package gamers.associate.SlimeAttack.game.achievements;

public class DefuserAch extends Achievement {

	private static final String name = "Diffuser";
	private static final String description = "Finish with 1 second remaining in Story";

	public DefuserAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeStory && !AchievementStatistics.isTuto && AchievementStatistics.winLeftTime <= 1.0f;
	}

}
