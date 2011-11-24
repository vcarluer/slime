package gamers.associate.Slime.items.base;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public interface ISelectable {
	boolean trySelect(CGPoint gameReference);
		
	boolean canSelect(CGPoint gameReference);
	
	void selectionMove(CGPoint gameReference);
	
	void selectionStop(CGPoint gameReference);
	
	void select();
	
	void select(CGPoint gameReference);
	
	void unselect();
	
	boolean isSelected();
	
	CGPoint getPosition();
	
	CGRect getSelectionRect();
	
	CCSprite getThumbail();
	
	CCNode getRootNode();
	
	boolean isActive();
	
	boolean simpleSelect();
}