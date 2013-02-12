package gamers.associate.SlimeAttack.items.base;

import org.cocos2d.types.CGPoint;

public interface ITrigerable {
	String getName();
	void trigger(Object source, String data);
	void triggerOn(Object source);
	void triggerOff(Object source);
	CGPoint getPosition();
}
