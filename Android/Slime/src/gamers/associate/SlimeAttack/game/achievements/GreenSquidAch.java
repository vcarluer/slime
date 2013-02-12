package gamers.associate.SlimeAttack.game.achievements;

public class GreenSquidAch extends Achievement {

	private static final String name = "Green squid";
	private static final String description = "Quick re-jump";

	public GreenSquidAch() {
		super(name, description, false);
	}

	@Override
	protected boolean testInternal() {
		if (AchievementStatistics.shotTime > 0 && AchievementStatistics.lastJumpStartTime > 0) {
			return AchievementStatistics.shotTime - AchievementStatistics.lastJumpStartTime < 200;
		}		
		
		return false;
	}
}
