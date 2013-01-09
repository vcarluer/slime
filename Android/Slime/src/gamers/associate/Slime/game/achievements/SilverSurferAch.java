package gamers.associate.Slime.game.achievements;

import gamers.associate.Slime.game.Rank;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.WorldPackage;
import gamers.associate.Slime.levels.LevelDefinition;

public class SilverSurferAch extends Achievement {

	private static final String name = "Silver surfer";
	private static final String description = "5 levels in a row with silver";

	public SilverSurferAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		if (AchievementStatistics.isModeStory && !AchievementStatistics.isTuto) {
			WorldPackage world = SlimeFactory.PackageManager.getCurrentPackage();
			int consecutive = 0;
			for(LevelDefinition def : world.getLevels()) {
				if (def.getRank() == Rank.Silver) {
					consecutive++;
				} else {
					consecutive = 0;
				}
				
				if (consecutive >= 5) {
					return true;
				}
			}			
		}
		
		return false;
	}

}
