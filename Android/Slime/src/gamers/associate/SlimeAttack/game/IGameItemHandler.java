package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttack.items.base.GameItem;

public interface IGameItemHandler {
	void addItemToAdd(GameItem item);
	void addItemToRemove(GameItem item);
	void attachToFactory();
	void resetLevel();
}
