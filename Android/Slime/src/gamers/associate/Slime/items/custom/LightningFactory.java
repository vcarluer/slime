package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.IElectrificable;

public class LightningFactory {
	public Lightning create(GameItem source, IElectrificable target, float life) {
		return new Lightning(source, target, life);
	}
}
