package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.IElectrificable;

import org.cocos2d.types.CGPoint;

public class LightningFactory {
	public Lightning create(CGPoint source, IElectrificable target, float life) {
		return new Lightning(source, target, life);
	}
}
