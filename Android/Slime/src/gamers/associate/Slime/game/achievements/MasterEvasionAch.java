package gamers.associate.Slime.game.achievements;


public class MasterEvasionAch extends Achievement {

	private static final String name = "Master of evasion";
	private static final String description = "Finish the game in story mode";

	public MasterEvasionAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeStory && AchievementStatistics.isBoss;
	}
}
