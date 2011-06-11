package gamers.associate.Slime.items.base;

import org.cocos2d.types.CGPoint;

public interface ISelectable {
	boolean trySelect(CGPoint gameReference);
	
	void selectionMove(CGPoint gameReference);
	
	void selectionStop(CGPoint gameReference);
	
	void select();
	
	void unselect();
	
	boolean isSelected();
	
	CGPoint getPosition();
}