package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.WorldPackage;

public class PrisonBreakAch extends Achievement {

	private static final String name = "Prison break mid season";
	private static final String description = "Finish 20 story levels";

	public PrisonBreakAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		if (AchievementStatistics.isModeStory) {
			WorldPackage world = SlimeFactory.PackageManager.getCurrentPackage();
			if (world.getUnlockLevelCount() > 20) {
				return true;
			}
		}
		
		return false;
	}
}
