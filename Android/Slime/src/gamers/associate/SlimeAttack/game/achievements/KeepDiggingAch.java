package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.WorldPackage;

public class KeepDiggingAch extends Achievement {

	private static final String name = "Keep digging slime!";
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
