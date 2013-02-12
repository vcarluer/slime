package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.game.Rank;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.WorldPackage;
import gamers.associate.SlimeAttack.levels.LevelDefinition;

public class GoldFeverAch extends Achievement {

	private static final String name = "Gold fever";
	private static final String description = "All levels gold!";

	public GoldFeverAch() {
		super(name, description, true);
	}

	@Override
	protected boolean testInternal() {
		boolean allGold = false;
		if (AchievementStatistics.isModeStory) {
			WorldPackage world = SlimeFactory.PackageManager.getCurrentPackage();
			allGold = true;
			for(LevelDefinition def : world.getLevels()) {
				if (def.getRank() != Rank.Gold) {
					allGold = false;
					break;
				}				
			}			
		}
		
		return allGold;
	}
}
