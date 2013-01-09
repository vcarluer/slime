package gamers.associate.Slime.game.achievements;

import gamers.associate.Slime.game.SlimeFactory;

public class HardcoreAch extends Achievement {

	private static final String name = "Hardcore gamer";
	private static final String description = "All achievements unlocked";

	public HardcoreAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		return SlimeFactory.AchievementManager.getAchievedCount() == SlimeFactory.AchievementManager.getAchievementsCount();
	}

}
