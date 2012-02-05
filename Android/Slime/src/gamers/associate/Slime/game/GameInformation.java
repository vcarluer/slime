package gamers.associate.Slime.game;

import gamers.associate.Slime.Slime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;

public class GameInformation {
	private int levelNum;
	private int levelDifficulty;
	private String fileName = "gameInfo.sli";
	private int totalScore;
	private int lastScore;
	
	public GameInformation() {
		this.levelDifficulty = LevelDifficulty.Easy;
		this.levelNum = 0;
		this.load();
	}
	
	public int getLevelMax() {
		return this.levelDifficulty * LevelDifficulty.LevelsPerDiff;
	}
	
	private void difficultyUp() {
		this.levelDifficulty = LevelDifficulty.getNextDifficulty(this.levelDifficulty);
		this.levelNum = 0;		
	}
	
	public void resetDifficulty(int diff) {
		this.levelDifficulty = diff;
		this.levelNum = 0;
		this.store();
	}
	
	public void levelUp() {		
		this.levelNum++;
		this.lastScore = 0;
		if (this.levelNum > this.getLevelMax()) {
			this.difficultyUp();
		}
		this.store();
	}
	
	public void store() {
		BufferedWriter buffWriter = null;
		try {
			Log.d(Slime.TAG, "Storing Game Info in " + fileName);
			FileOutputStream fos = SlimeFactory.ContextActivity.openFileOutput(fileName, Context.MODE_PRIVATE);
			OutputStreamWriter streamWriter = new OutputStreamWriter(fos);
			buffWriter = new BufferedWriter(streamWriter);
			buffWriter.write(String.valueOf(this.levelDifficulty));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.levelNum));			
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.totalScore));
		} catch (FileNotFoundException ex) {
			Log.e(Slime.TAG, "ERROR, file not found " + fileName);
			ex.printStackTrace();
        } catch (IOException e1) {
			Log.e(Slime.TAG, "ERROR during opening or write of " + fileName);
			e1.printStackTrace();
        } finally {
        	 //Close the BufferedWriter
            try {
                if (buffWriter != null) {
                	buffWriter.flush();
                	buffWriter.close();
                }
            } catch (IOException ex) {
            	Log.e(Slime.TAG, "ERROR during close of " + fileName);
                ex.printStackTrace();
            }
        }
	}
	
	private void load() {
		java.io.InputStream inputStream;
		String fileName = this.fileName;
		try {
			if (SlimeFactory.ContextActivity.getFileStreamPath(fileName).exists()) {
				Log.d(Slime.TAG, "Loading User Info from " + fileName);
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
								this.levelDifficulty = Integer.valueOf(line).intValue();
								break;
							case 2:
								this.levelNum = Integer.valueOf(line).intValue();
								break;
							case 3:
								this.totalScore = Integer.valueOf(line).intValue();
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
			}
		} catch (IOException e1) {
			Log.e(Slime.TAG, "ERROR during opening of " + fileName);
			e1.printStackTrace();
		}	
	}

	public int getLevelNum() {
		return this.levelNum;
	}
	
	public int getDifficulty() {
		return this.levelDifficulty;
	}
	
	public int getTotalScore() {
		return this.totalScore;
	}
	
	public void addLevelScore(int score) {
		this.lastScore = score;
		this.totalScore += score;
		this.store();
	}
	
	public void removeLastScore() {
		this.totalScore -= this.lastScore;
		this.lastScore = 0;
		this.store();
	}

	public void resetTotalScore() {
		this.totalScore = 0;
		this.lastScore = 0;
		this.store();		
	}
}
