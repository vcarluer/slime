package gamers.associate.SlimeAttack.game.achievements;

public class MotherShipAch extends Achievement {

	private static final String name = "Back to the Mother ship";
	private static final String description = "Die by acid";

	public MotherShipAch() {
		super(name, description, false, true);
	}

	@Override
	protected boolean testInternal() {
		return AchievementStatistics.isMode() && AchievementStatistics.dissolved;
	}

}
