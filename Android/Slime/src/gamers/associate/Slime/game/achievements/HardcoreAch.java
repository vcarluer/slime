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
		return (SlimeFactory.AchievementManager.getAchievedCount() + 1) == SlimeFactory.AchievementManager.getAchievementsCount();
	}

	@SuppressWarnings("unused")
	@Override
	protected void load() {
		super.load();
		if (SlimeFactory.unlockAllAchievement && SlimeFactory.noUnlockHardcoreGamer) {
			this.setAchieved(false);
		}
	}

}
