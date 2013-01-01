package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.LevelDefinition;

import java.util.ArrayList;
import java.util.List;

public abstract class WorldPackage {
	private String name;
	private List<LevelDefinition> levels;
	
	public WorldPackage() {
		this.levels = new ArrayList<LevelDefinition>();
		this.defineName();
		this.createLevelList();
	}
	
	protected abstract void defineName();

	protected abstract void createLevelList();

	public List<LevelDefinition> getLevels() {
		return this.levels;
	}
	
	public String getName() {
		return this.name;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
}
