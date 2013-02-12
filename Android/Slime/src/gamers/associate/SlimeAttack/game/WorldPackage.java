package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.LevelDefinition;
import gamers.associate.SlimeAttack.levels.LevelDefinitionParser;

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
			int num = i + 1;
			LevelDefinitionParser definition = new LevelDefinitionParser(this.getResourceName(num));
			definition.setNumber(num);
			definition.setGamePlay(GamePlay.TimeAttack);
			definition.setDifficulty(this.getDifficulty(num));
			definition.setWorld(this);

			if (i == 0) {
				definition.setUnlock(true);
			}
			
			if (num == this.levelCount) {
				definition.setBoss(true);
			}
			
			this.getLevels().add(definition);
		}
	}
	
	protected abstract int getDifficulty(int lvlNumber);

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
	
	public int getUnlockLevelCount() {
		int unlocked = 0;
		for(LevelDefinition level : this.levels) {
			if (!level.isUnlock()) {
				break;
			}
			
			unlocked++;
		}
		
		return unlocked;
	}

	public LevelDefinition getNext(LevelDefinition levelDefinition) {
		int nextNumber = levelDefinition.getNumber() + 1;
		return this.getLevel(nextNumber);
	}

	private LevelDefinition getLevel(int number) {
		if (number <= this.levels.size()) {
			return this.levels.get(number - 1);
		} else {
			SlimeFactory.Log.e(SlimeAttack.TAG, "Level does not exist: " + this.getName() + " - " + String.valueOf(number));
			return null;
		}
	}
}
