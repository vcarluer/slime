package gamers.associate.Slime.game.achievements;

import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.WorldPackage;

public class KeepDiggingAch extends Achievement {

	private static final String name = "Keep digging for your great escape";
	private static final String description = "Finish 40 story levels";

	public KeepDiggingAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		if (AchievementStatistics.isModeStory) {
			WorldPackage world = SlimeFactory.PackageManager.getCurrentPackage();
			if (world.getUnlockLevelCount() > 40) {
				return true;
			}
		}
		
		return false;
	}
}
