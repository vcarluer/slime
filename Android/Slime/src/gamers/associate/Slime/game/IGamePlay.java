package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.GamePlay;

import org.cocos2d.types.CGPoint;

public interface IGamePlay {	
	void setLevel(Level level);
	void startLevel();
	void stop();
	int getScore();
	int getBaseScore();
	int getBonusScore();
	int getBonusScore(int bonusIdx);
	void activateSelection(CGPoint gameReference);
	void simpleSelect();
	void selectBegin(CGPoint gameReference);	
	void reset();	
	boolean isGameOver();
	void setNewAliveSlimyCount(int count);
	void setNewBonus();
	void setNewBonusTime();
	void setPause(boolean value);
	boolean isStarted();
	int bonusCount();
	int neededBonus();
	float getNormalTimeRatio();
	float getNormalBonusPoints();
	float getExtraBonusPoints();
	GamePlay getType();
	void endMode();
	float getLeftTime();
}
