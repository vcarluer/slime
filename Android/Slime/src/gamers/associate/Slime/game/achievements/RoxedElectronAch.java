package gamers.associate.Slime.game.achievements;

public class RoxedElectronAch extends Achievement {

	private static final String name = "Rocked by an electron";
	private static final String description = "Die electrified";

	public RoxedElectronAch() {
		super(name, description, false, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.electrified;
	}

}
