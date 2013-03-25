package gamers.associate.SlimeAttack.game.achievements;

public class RedAlertAch extends Achievement {

	private static final String name = "Red alert";
	private static final String description = "Enter critic zone";

	public RedAlertAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeStory && AchievementStatistics.enterCriticZone;
	}

}
