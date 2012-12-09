package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.ILevelBuilder;
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
	}

	public void resetTotalStar() {
		this.totalStar = 0;
	}

	public boolean isBoss() {
		return isBoss;
	}
	
	protected  boolean isTut() {
		return this.gameInfo.getDifficulty() == LevelDifficulty.Easy
				&& this.gameInfo.getLevelNum() <= LevelGraphGeneratorTutorial.tutorialCount;
	}
}	
