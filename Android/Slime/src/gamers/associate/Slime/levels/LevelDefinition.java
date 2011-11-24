package gamers.associate.Slime.levels;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public abstract class LevelDefinition {
	private String id;
	private boolean noStore;
	protected boolean isSpecial;
	protected GamePlay gamePlay;
	protected int lastScore;
	protected int maxScore;
	protected Context context;
	protected boolean isUnlock;
	
	protected LevelDefinition() {
		this.gamePlay = GamePlay.None;
		this.noStore = this.getNoStore();
	}
	
	public boolean isUnlock() {
		return isUnlock;
	}

	public void setUnlock(boolean isUnlock) {
		this.isUnlock = isUnlock;
		this.handlePersistancy();
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
			this.maxScore = score;
			this.handlePersistancy();
		}
	}
	
	private void handlePersistancy() {
		if (!this.noStore) {
			this.storeUserInfo();
		}
	}
	
	private void storeUserInfo() {
		BufferedWriter buffWriter = null;
		try {
			FileOutputStream fos = SlimeFactory.ContextActivity.openFileOutput(this.id, Context.MODE_PRIVATE);
			OutputStreamWriter streamWriter = new OutputStreamWriter(fos);
			buffWriter = new BufferedWriter(streamWriter);
			buffWriter.write(String.valueOf(this.maxScore));
			buffWriter.write(String.valueOf(this.isUnlock));
			// buffWriter.newLine();		
		} catch (FileNotFoundException ex) {
			Log.e(Slime.TAG, "ERROR, file not found " + this.id);
			ex.printStackTrace();
        } catch (IOException e1) {
			Log.e(Slime.TAG, "ERROR during opening or write of " + this.id);
			e1.printStackTrace();
        } finally {
        	 //Close the BufferedWriter
            try {
                if (buffWriter != null) {
                	buffWriter.flush();
                	buffWriter.close();
                }
            } catch (IOException ex) {
            	Log.e(Slime.TAG, "ERROR during close of " + this.id);
                ex.printStackTrace();
            }
        }
	}
	
	private void loadUserInfo() {
		java.io.InputStream inputStream;
		String fileName = this.id;
		try {			
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
							break;
						case 2:
							this.isUnlock = Boolean.valueOf(line).booleanValue();
							break;
						default:
							break;
						}												
					} catch (Exception e) {
						Log.e(Slime.TAG, "ERROR during read of " + fileName + " line " + String.valueOf(i));
						e.printStackTrace();
					}									
				}
			} catch (IOException e) {
				Log.e(Slime.TAG, "ERROR during read of " + fileName + " line " + String.valueOf(i));
				e.printStackTrace();
			} finally {
				if (buffreader != null) {
					buffreader.close();
				}
			}
		} catch (IOException e1) {
			Log.e(Slime.TAG, "ERROR during opening of " + fileName);
			e1.printStackTrace();
		}	
	}
	
	public abstract void buildLevel(Level level);
}