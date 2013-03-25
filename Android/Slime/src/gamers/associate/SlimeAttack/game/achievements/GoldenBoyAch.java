package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.Rank;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.WorldPackage;
import gamers.associate.SlimeAttack.levels.LevelDefinition;

public class GoldenBoyAch extends Achievement {

	private static final String name = "Golden boy";
	private static final String description = "5 consecutive levels in gold";

	public GoldenBoyAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		if (AchievementStatistics.isModeStory && !AchievementStatistics.isTuto) {
			WorldPackage world = SlimeFactory.PackageManager.getCurrentPackage();
			int consecutiveGold = 0;
			for(LevelDefinition def : world.getLevels()) {
				if (def.getRank() == Rank.Gold) {
					consecutiveGold++;
				} else {
					consecutiveGold = 0;
				}
				
				if (consecutiveGold >= 5) {
					return true;
				}
			}			
		}
		
		return false;
	}

}
