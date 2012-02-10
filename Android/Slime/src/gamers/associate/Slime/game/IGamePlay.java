package gamers.associate.Slime.game;

import org.cocos2d.types.CGPoint;

public interface IGamePlay {	
	void setLevel(Level level);
	void startLevel();
	void stop();
	int getScore();
	void activateSelection(CGPoint gameReference);
	void simpleSelect();
	void selectBegin(CGPoint gameReference);	
	void reset();	
	boolean isGameOver();
	void setNewAliveSlimyCount(int count);
	void setNewBonus();
	void setPause(boolean value);
	boolean isStarted();
}
