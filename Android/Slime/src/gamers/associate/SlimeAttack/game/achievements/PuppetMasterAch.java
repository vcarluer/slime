package gamers.associate.SlimeAttack.game.achievements;

public class PuppetMasterAch extends Achievement {

	private static final String name = "Master of Puppets";
	private static final String description = "Change zoom to play";

	public PuppetMasterAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.zoomChanged;
	}

}
