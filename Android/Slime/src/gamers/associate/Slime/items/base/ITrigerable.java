package gamers.associate.Slime.items.base;

import org.cocos2d.types.CGPoint;

public interface ITrigerable {
	String getName();
	void trigger(Object source, String data);
	CGPoint getPosition();
}
