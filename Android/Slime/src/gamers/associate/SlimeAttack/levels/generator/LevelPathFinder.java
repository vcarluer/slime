package gamers.associate.SlimeAttack.levels.generator;

import gamers.associate.SlimeAttack.items.base.GameItem;

import java.util.ArrayList;
import java.util.List;

public class LevelPathFinder {
	private List<GameItem> wallPlatforms;
	
	public LevelPathFinder() {
		this.wallPlatforms = new ArrayList<GameItem>();
	}
	
	public void reset() {
		this.wallPlatforms.clear();
	}
	
	public void addWall(GameItem wall) {
		this.wallPlatforms.add(wall);
	}
}
