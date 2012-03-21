package gamers.associate.Slime.game;

import gamers.associate.Slime.items.base.GameItem;

public interface IGameItemHandler {
	void addItemToAdd(GameItem item);
	void addItemToRemove(GameItem item);
	void attachToFactory();
	void resetLevel();
}
