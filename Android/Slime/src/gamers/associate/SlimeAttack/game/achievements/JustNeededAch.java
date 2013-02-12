package gamers.associate.SlimeAttack.game.achievements;


public class JustNeededAch extends Achievement {

	private static final String name = "Just what is needed";
	private static final String description = "Finish quickly with minimum star";

	public JustNeededAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return !AchievementStatistics.isTuto &&
			(AchievementStatistics.startTime - AchievementStatistics.leftTime) <= 5 && 
			AchievementStatistics.bonusTaken == AchievementStatistics.neededBonus;
	}
}
