package gamers.associate.Slime.game;

import org.cocos2d.types.CGPoint;

public interface IGamePlay {	
	void setLevel(Level level);
	int getScore();
	void activateSelection(CGPoint gameReference);
	void simpleSelect();
	void selectBegin(CGPoint gameReference);	
}
