package gamers.associate.SlimeAttack.game.achievements;

public class DontPushAch extends Achievement {

	private static final String name = "Don't push me";
	private static final String description = "Exit critic zone";

	public DontPushAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isModeStory && AchievementStatistics.exitCriticZone;
	}

}
