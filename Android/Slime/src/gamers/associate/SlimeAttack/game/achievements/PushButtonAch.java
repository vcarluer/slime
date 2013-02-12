package gamers.associate.SlimeAttack.game.achievements;


public class PushButtonAch extends Achievement {

	private static final String name = "Push the button";
	private static final String description = "Desactivate dangers using a button";

	public PushButtonAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isMode() && AchievementStatistics.buttonPushed;
	}

}
