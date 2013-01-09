package gamers.associate.Slime.game.achievements;


public class MarioStyleAch extends Achievement {

	private static final String name = "Mario style";
	private static final String description = "Kill a mini red monster";

	public MarioStyleAch() {
		super(name, description, false, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.miniRedKilled;
	}
}
