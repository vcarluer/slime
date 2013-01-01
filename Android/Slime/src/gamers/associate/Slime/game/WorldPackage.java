package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.GamePlay;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelDefinitionParser;

import java.util.ArrayList;
import java.util.List;

public abstract class WorldPackage {
	private static final String LVLSEP = "-";
	private String name;
	private List<LevelDefinition> levels;
	private int levelCount;
	private boolean isLock;
	private int order;
	private String backgroundPath;
	
	public WorldPackage() {
		this.levels = new ArrayList<LevelDefinition>();
		this.defineName();
		this.defineLevelCount();
		this.defineBackgroundPath();
		this.createLevelList();
	}
	
	protected abstract void defineBackgroundPath();

	protected abstract void defineName();
	
	protected abstract void defineLevelCount();

	public List<LevelDefinition> getLevels() {
		return this.levels;
	}
	
	public String getName() {
		return this.name;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	protected void setLevelCount(int count) {
		this.levelCount = count;
	}
	
	protected void createLevelList() {
		for(int i = 0; i < this.levelCount; i++) {
			LevelDefinition definition = new LevelDefinitionParser(this.getResourceName(i));
			definition.setGamePlay(GamePlay.TimeAttack);
			this.getLevels().add(definition);
		}
	}
	
	private String getResourceName(int i) {
		return this.getName() + LVLSEP + String.valueOf(i) + SlimeFactory.slimeFileExt;
	}

	public boolean isLock() {
		return isLock;
	}

	protected void setLock(boolean isLock) {
		this.isLock = isLock;
	}

	public int getOrder() {
		return order;
	}

	protected void setOrder(int order) {
		this.order = order;
	}

	public String getBackgroundPath() {
		return backgroundPath;
	}

	protected void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}

	public int getLevelCount() {
		return this.levelCount;
	}
}
