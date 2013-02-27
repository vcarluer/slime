package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.Rank;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.WorldPackage;
import gamers.associate.SlimeAttack.levels.LevelDefinition;

public class BronzeAgeAch extends Achievement {

	private static final String name = "Bronze age";
	private static final String description = "5 levels in a row with bronze";

	public BronzeAgeAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		if (AchievementStatistics.isModeStory && !AchievementStatistics.isTuto) {
			WorldPackage world = SlimeFactory.PackageManager.getCurrentPackage();
			int consecutive = 0;
			for(LevelDefinition def : world.getLevels()) {
				if (def.getRank() == Rank.Bronze || def.getRank() == Rank.Silver || def.getRank() == Rank.Gold) {
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
