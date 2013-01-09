package gamers.associate.Slime.game.achievements;

import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.WorldPackage;

public class MonteCristoAch extends Achievement {

	private static final String name = "The Count of Monte Crisco";
	private static final String description = "Finish 60 story levels";

	public MonteCristoAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		if (AchievementStatistics.isModeStory) {
			WorldPackage world = SlimeFactory.PackageManager.getCurrentPackage();
			if (world.getUnlockLevelCount() > 60) {
				return true;
			}
		}
		
		return false;
	}
}
