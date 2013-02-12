package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.base.IElectrificable;

public class LightningFactory {
	public Lightning create(GameItem source, IElectrificable target, float life) {
		return new Lightning(source, target, life);
	}
}
