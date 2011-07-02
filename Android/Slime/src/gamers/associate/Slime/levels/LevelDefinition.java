package gamers.associate.Slime.levels;

import gamers.associate.Slime.game.Level;

public abstract class LevelDefinition {
	private String id;
	private boolean isSpecial;
	
	protected LevelDefinition() {
		this.init();
	}
	
	protected abstract void init();
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	protected void setId(String id) {
		this.id = id;
	}
	
	public abstract void buildLevel(Level level);

	/**
	 * @return the isSpecial
	 */
	public boolean isSpecial() {
		return isSpecial;
	}

	/**
	 * @param isSpecial the isSpecial to set
	 */
	protected void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
}
