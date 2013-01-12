package gamers.associate.Slime.game.achievements;


public class PushButtonAch extends Achievement {

	private static final String name = "Push the button";
	private static final String description = "Desactivate dangers with a button";

	public PushButtonAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isMode() && AchievementStatistics.buttonPushed;
	}

}