package gamers.associate.Slime.levels;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Rank;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.WorldPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;

public abstract class LevelDefinition {
	private String id;
	protected boolean noStore;
	protected boolean isSpecial;
	protected GamePlay gamePlay;
	protected int lastScore;
	protected int maxScore;
	protected Context context;
	protected boolean isUnlock;
	protected boolean isFinished;
	protected boolean isCurrentSelection;
	private Rank rank;
	private boolean isBoss;
	private int number;
	private int difficulty;
	private WorldPackage world;
	private int previousMaxScore;
	// for level generation pick
	private boolean isInvalidated;
	
	protected LevelDefinition() {
		this.gamePlay = GamePlay.None;
		this.noStore = this.getNoStore();
		this.setRank(Rank.Lock);
	}
	
	public boolean isUnlock() {
		return isUnlock;
	}

	public void setUnlock(boolean isUnlock) {
		this.isUnlock = isUnlock;
		if (!this.isUnlock) {
			this.setRank(Rank.Lock);
		} else {
			if (this.rank == Rank.Lock) {
				this.setRank(Rank.None);
			}
		}
	}
	
	protected boolean getNoStore() {
		return false;
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
		// todo: lazy load info on demand
		if (this.id != null && this.id.length() > 0) {
			if (!this.noStore) {
				this.loadUserInfo();
			}
		}
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
		this.setMaxScore(this.lastScore);
	}

	public int getLastScore() {
		return lastScore;
	}
	
	public int getMaxScore() {
		return this.maxScore;
	}
	
	private void setMaxScore(int score) {
		if (score > this.maxScore) {
			this.setPreviousMaxScore(this.maxScore);
			this.maxScore = score;
			this.setFinished(true);
		}
	}
	
	public void handlePersistancy() {
		if (!this.noStore) {
			this.storeUserInfo();
		}
	}
	
	private void storeUserInfo() {
		BufferedWriter buffWriter = null;
		try {
			SlimeFactory.Log.d(Slime.TAG, "Storing User Info in " + this.id);
			FileOutputStream fos = SlimeFactory.ContextActivity.openFileOutput(this.id, Context.MODE_PRIVATE);
			OutputStreamWriter streamWriter = new OutputStreamWriter(fos);
			buffWriter = new BufferedWriter(streamWriter);
			buffWriter.write(String.valueOf(this.maxScore));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.isUnlock));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.isFinished));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.isCurrentSelection));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.getRank()));
			buffWriter.newLine();
			this.storeUserInfoNext(buffWriter);
		} catch (FileNotFoundException ex) {
			SlimeFactory.Log.e(Slime.TAG, "ERROR, file not found " + this.id);
			ex.printStackTrace();
        } catch (IOException e1) {
			SlimeFactory.Log.e(Slime.TAG, "ERROR during opening or write of " + this.id);
			e1.printStackTrace();
        } finally {
        	 //Close the BufferedWriter
            try {
                if (buffWriter != null) {
                	buffWriter.flush();
                	buffWriter.close();
                }
            } catch (IOException ex) {
            	SlimeFactory.Log.e(Slime.TAG, "ERROR during close of " + this.id);
                ex.printStackTrace();
            }
        }
	}
	
	protected void storeUserInfoNext(BufferedWriter buffWriter)  throws IOException {		
	}

	@SuppressWarnings("unused")
	private void loadUserInfo() {
		java.io.InputStream inputStream;		
		String fileName = this.id;
		try {
			if (SlimeFactory.clearLevelInfo) {
				SlimeFactory.ContextActivity.deleteFile(fileName);
			} else {
				if (SlimeFactory.ContextActivity.getFileStreamPath(fileName).exists()) {
					SlimeFactory.Log.d(Slime.TAG, "Loading User Info from " + fileName);
					inputStream = SlimeFactory.ContextActivity.openFileInput(fileName);
					InputStreamReader inputreader = new InputStreamReader(inputStream);
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line;		
					
					int i = 0;
					try {
						while (( line = buffreader.readLine()) != null) {
							try {
								i++;
								switch(i) {
								case 1:
									this.maxScore = Integer.valueOf(line).intValue();
									this.previousMaxScore = this.maxScore;
									break;
								case 2:
									if (SlimeFactory.unlockAll) {
										this.isUnlock = true;
									} else {
										this.isUnlock = Boolean.valueOf(line).booleanValue();
									}								
									break;
								case 3:
									this.isFinished = Boolean.valueOf(line).booleanValue();
									break;
								case 4:
									this.isCurrentSelection = Boolean.valueOf(line).booleanValue();
									break;
								case 5:
									this.setRank(Rank.valueOf(line));
									if (SlimeFactory.unlockAll && this.rank == Rank.Lock) {
										this.setRank(Rank.None);
									}

									break;
								default:
									this.loadUserInfoNext(line, i);
									break;
								}												
							} catch (Exception e) {
								SlimeFactory.Log.e(Slime.TAG, "ERROR during read of " + fileName + " line " + String.valueOf(i));
								e.printStackTrace();
							}									
						}
					} catch (IOException e) {
						SlimeFactory.Log.e(Slime.TAG, "ERROR during read of " + fileName + " line " + String.valueOf(i));
						e.printStackTrace();
					} finally {
						if (buffreader != null) {
							buffreader.close();
						}
					}
				}  else {
					SlimeFactory.Log.d(Slime.TAG, "No need to load User Info from " + fileName);
					if (SlimeFactory.unlockAll) {
						this.setUnlock(true);
					} else {
						this.setUnlock(false);
					}					
				}				
			}			
		} catch (IOException e1) {
			SlimeFactory.Log.e(Slime.TAG, "ERROR during opening of " + fileName);
			e1.printStackTrace();
		}	
	}
	
	protected void loadUserInfoNext(String line, int idx) {		
	}

	public abstract boolean buildLevel(Level level);

	/**
	 * @return the isFinished
	 */
	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * @param isFinished the isFinished to set
	 */
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	/**
	 * @return the isCurrentSelection
	 */
	public boolean isCurrentSelection() {
		return isCurrentSelection;
	}

	/**
	 * @param isCurrentSelection the isCurrentSelection to set
	 */
	public void setCurrentSelection(boolean isCurrentSelection) {
		this.isCurrentSelection = isCurrentSelection;
		this.handlePersistancy();
	}
	
	public void resetUserInfo() {
		this.maxScore = 0;
		this.isFinished = false;
		this.setUnlock(false);
		this.isCurrentSelection = false;
		this.resetUserInfoNext();
	}
	
	protected void resetUserInfoNext() {		
	}

	public void resetAndSave() {
		this.resetUserInfo();
		this.handlePersistancy();
	}
	
	public void resetAll() {
		this.resetUserInfo();
		this.resetAllNext();
	}

	protected void resetAllNext() {		
	}
	
	public void resetAllAndSave() {
		this.resetAll();
		this.handlePersistancy();
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {		
		this.rank = rank;			
	}
	
	public void upgradeRank(Rank rank) {
		if (this.rank == null || rank.index() > this.rank.index()) {
			this.setRank(rank);
		}
	}

	public boolean isBoss() {
		return isBoss;
	}

	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public WorldPackage getWorld() {
		return world;
	}

	public void setWorld(WorldPackage world) {
		this.world = world;
	}

	public int getPreviousMaxScore() {
		return previousMaxScore;
	}

	public void setPreviousMaxScore(int previousMaxScore) {
		this.previousMaxScore = previousMaxScore;
	}

	public boolean isInvalidated() {
		return isInvalidated;
	}

	public void setInvalidated(boolean isInvalidated) {
		this.isInvalidated = isInvalidated;
	}
}