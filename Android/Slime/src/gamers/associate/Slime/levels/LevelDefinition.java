package gamers.associate.Slime.levels;

import gamers.associate.Slime.game.Level;

public abstract class LevelDefinition {
	protected String id;
	protected boolean isSpecial;
	protected GamePlay gamePlay;
	protected int lastScore;
	protected int maxScore;
	
	protected LevelDefinition() {
		this.gamePlay = GamePlay.None;	
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isSpecial
	 */
	public boolean isSpecial() {
		return isSpecial;
	}

	/**
	 * @param isSpecial the isSpecial to set
	 */
	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	/**
	 * @return the gamePlay
	 */
	public GamePlay getGamePlay() {
		return gamePlay;
	}

	/**
	 * @param gamePlay the gamePlay to set
	 */
	public void setGamePlay(GamePlay gamePlay) {
		this.gamePlay = gamePlay;
	}

	public void setLastScore(int lastScore) {
		this.lastScore = lastScore;
		if (this.lastScore > this.maxScore) {
			this.maxScore = this.lastScore;
		}
	}

	public int getLastScore() {
		return lastScore;
	}
	
	public int getMaxScore() {
		return this.maxScore;
	}
	
	public abstract void buildLevel(Level level);
}
