package gamers.associate.Slime.levels;

import gamers.associate.Slime.game.Level;

public abstract class LevelDefinition {
	private String id;
	private boolean isSpecial;
	private GamePlay gamePlay;
	private int lastScore;
	private int maxScore;
	
	protected LevelDefinition() {
		this.gamePlay = GamePlay.None;
		this.init();
	}
	
	protected void init() {
		this.initLevel();
	}
	
	protected abstract void initLevel();
	
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

	/**
	 * @return the gamePlay
	 */
	public GamePlay getGamePlay() {
		return gamePlay;
	}

	/**
	 * @param gamePlay the gamePlay to set
	 */
	protected void setGamePlay(GamePlay gamePlay) {
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
}
