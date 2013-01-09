package gamers.associate.Slime.game.achievements;

public class PuppetMasterAch extends Achievement {

	private static final String name = "Puppet Master";
	private static final String description = "Change zoom to play";

	public PuppetMasterAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.zoomChanged;
	}

}
