package gamers.associate.SlimeAttack.items.base;

import org.cocos2d.types.CGPoint;

public interface IElectrificable {
	void electrify();
	CGPoint getPosition();
	boolean isActive();
}
