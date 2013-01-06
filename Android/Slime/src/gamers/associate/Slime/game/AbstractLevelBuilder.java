package gamers.associate.Slime.game;

import gamers.associate.Slime.game.achievements.AchievementStatistics;
import gamers.associate.Slime.levels.ILevelBuilder;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.generator.LevelGraphGeneratorTutorial;

public abstract class AbstractLevelBuilder implements ILevelBuilder {
	protected GameInformation gameInfo;
	protected int totalStar;
	protected boolean isBoss;
	
	public AbstractLevelBuilder() {
		this.gameInfo = SlimeFactory.GameInfo;
	}
	
	public int getTotalStar() {
		return this.totalStar;
	}

	public void addStar() {
		this.totalStar++;
		AchievementStatistics.totalStar = this.totalStar;
	}

	public void resetTotalStar() {
		this.totalStar = 0;
		AchievementStatistics.totalStar = this.totalStar;
	}

	public boolean isBoss() {
		return isBoss;
	}
	
	protected  boolean isTut() {
		return this.gameInfo.getDifficulty() == LevelDifficulty.Easy
				&& this.gameInfo.getLevelNum() <= LevelGraphGeneratorTutorial.tutorialCount;
	}
	
	protected  boolean isTut(LevelDefinition definition) {
		return definition != null && 
				definition.getNumber() <= LevelGraphGeneratorTutorial.tutorialCount && 
				definition.getWorld().getOrder() == 1;
	}
}	
