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

public class GameInformation {
	private int levelNum;
	private int levelDifficulty;
	private String fileName = "gameInfo.sli";
	private int totalScoreEasy;
	private int totalScoreNormal;
	private int totalScoreHard;
	private int totalScoreExtrem;
	private int lastScore;
	private int maxLevelDifficulty;
	private int previousTotalScore;
	private int previousDifficulty;
	private String lastBgk;
	private int totalCurrent;
	private boolean lastIsHighScore;
	private static boolean resetHighScores = SlimeFactory.resetHighScores;
	private int worldId;
	
	public GameInformation() {		
		this.maxLevelDifficulty = this.levelDifficulty = LevelDifficulty.Easy;
		this.setLevelNum(0);
		this.load();
		if (resetHighScores) {
			this.totalScoreEasy = 0;
			this.totalScoreNormal = 0;
			this.totalScoreHard = 0;
			this.totalScoreExtrem = 0;
			this.store();
		}
	}
	
	public void unlockDifficulty(int levelDifficulty) {
		if (levelDifficulty > this.maxLevelDifficulty) {
			this.maxLevelDifficulty = levelDifficulty;
			this.store();
		}
	}
	
	private void setLevelDifficulty(int leveldifficulty) {		
		this.lastScore = 0;
		this.lastIsHighScore = false;
		this.previousDifficulty = this.levelDifficulty;
		this.levelDifficulty = leveldifficulty;
		this.totalCurrent = 0;
//		this.setTotalScore(0);
		if (this.levelDifficulty > this.maxLevelDifficulty) {
			this.maxLevelDifficulty = this.levelDifficulty;
		}
	}
	
	private void setTotalScore(int score, int levelDifficulty) {
		switch (levelDifficulty) {		
		case LevelDifficulty.Normal:
			this.totalScoreNormal = score;
			break;
		case LevelDifficulty.Hard:
			this.totalScoreHard = score;
			break;
		case LevelDifficulty.Extrem:
			this.totalScoreExtrem = score;
			break;
		case LevelDifficulty.Easy:
		default:
			this.totalScoreEasy = score;
			break;
		}
	}
	
	public int getCurrentScore() {
		return this.totalCurrent;
	}
	
	private int getScore() {
		return this.totalScoreEasy + this.totalScoreNormal + this.totalScoreHard + this.totalScoreExtrem;
	}
	
	private int getDifficultyScore() {
		return this.getDifficultyScore(this.levelDifficulty);
	}
	
	private int getDifficultyScore(int levelDifficulty) {
		switch (levelDifficulty) {		
		case LevelDifficulty.Normal:
			return this.totalScoreNormal;
		case LevelDifficulty.Hard:
			return this.totalScoreHard;
		case LevelDifficulty.Extrem:
			return this.totalScoreExtrem;
		case LevelDifficulty.Easy:
		default:
			return this.totalScoreEasy;
		}
	}
	
	public int getLevelMax() {
		return this.getLevelMax(this.levelDifficulty);
	}
	
	public int getLevelMax(int difficulty) {
		return difficulty * LevelDifficulty.LevelsPerDiff;
	}
	
	private void difficultyUp() {
		this.setLevelDifficulty(LevelDifficulty.getNextDifficulty(this.levelDifficulty));
		this.setLevelNum(0);		
	}
	
	public void endDifficulty() {
		this.previousTotalScore = this.getDifficultyScore();
		this.difficultyUp();
		this.store();
	}
	
	public void resetDifficulty(int diff) {
		this.setLevelDifficulty(diff);
		this.levelNum = 0;
		this.store();
	}
	
	public void levelUp() {		
		this.setLevelNum(levelNum + 1);
		this.lastScore = 0;
		if (this.levelNum > this.getLevelMax()) {
			this.difficultyUp();
		}
		this.store();
	}
	
	public void setLevel(int level) {
		this.setLevelNum(level);
		this.store();
	}
	
	public void forceLevel(int diff, int level) {
		this.setLevelDifficulty(diff);
		this.setLevelNum(level);
		this.store();
	}
	
	public void store() {
		BufferedWriter buffWriter = null;
		try {
			SlimeFactory.Log.d(Slime.TAG, "Storing Game Info in " + fileName);
			FileOutputStream fos = SlimeFactory.ContextActivity.openFileOutput(fileName, Context.MODE_PRIVATE);
			OutputStreamWriter streamWriter = new OutputStreamWriter(fos);
			buffWriter = new BufferedWriter(streamWriter);
			buffWriter.write(String.valueOf(this.levelDifficulty));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.levelNum));			
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.totalScoreEasy));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.totalScoreNormal));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.totalScoreHard));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.totalScoreExtrem));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.maxLevelDifficulty));
			buffWriter.newLine();
			buffWriter.write(this.lastBgk);
		} catch (FileNotFoundException ex) {
			SlimeFactory.Log.e(Slime.TAG, "ERROR, file not found " + fileName);
			ex.printStackTrace();
        } catch (IOException e1) {
			SlimeFactory.Log.e(Slime.TAG, "ERROR during opening or write of " + fileName);
			e1.printStackTrace();
        } finally {
        	 //Close the BufferedWriter
            try {
                if (buffWriter != null) {
                	buffWriter.flush();
                	buffWriter.close();
                }
            } catch (IOException ex) {
            	SlimeFactory.Log.e(Slime.TAG, "ERROR during close of " + fileName);
                ex.printStackTrace();
            }
        }
	}
	
	private void load() {
		java.io.InputStream inputStream;
		String fileName = this.fileName;
		try {
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
								this.levelDifficulty = Integer.valueOf(line).intValue();
								break;
							case 2:
								this.levelNum = Integer.valueOf(line).intValue();
								break;
							case 3:
								this.setTotalScore(Integer.valueOf(line).intValue(), LevelDifficulty.Easy);
								break;
							case 4:
								this.setTotalScore(Integer.valueOf(line).intValue(), LevelDifficulty.Normal);
								break;
							case 5:
								this.setTotalScore(Integer.valueOf(line).intValue(), LevelDifficulty.Hard);
								break;
							case 6:
								this.setTotalScore(Integer.valueOf(line).intValue(), LevelDifficulty.Extrem);
								break;
							case 7:
								this.maxLevelDifficulty = Integer.valueOf(line).intValue();
								break;
							case 8:
								this.lastBgk = line;
								break;
							default:
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
			}
		} catch (IOException e1) {
			SlimeFactory.Log.e(Slime.TAG, "ERROR during opening of " + fileName);
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
		return this.getScore();
	}
	
	public void addLevelScore(int score) {
		this.lastScore = score;
		this.totalCurrent += score;
//		this.setTotalScore(this.getDifficultyScore() + score);
//		this.store();
	}
	
	public void removeLastScore() {
		this.totalCurrent -= this.lastScore;
//		this.setTotalScore(this.getDifficultyScore() - this.lastScore);
		this.lastScore = 0;
//		this.store();
	}

	public int getMaxLevelDifficulty() {
		if (SlimeFactory.IsForceMaxSurvival) {
			return SlimeFactory.MaxSurvival;
		} else {
			return maxLevelDifficulty;
		}		
	}
	
	// for debug
	public void resetMaxLevelDifficulty() {
		this.maxLevelDifficulty = LevelDifficulty.Easy;
	}

	public int getPreviousTotalScore() {
		return previousTotalScore;
	}
		
	public int getPreviousDifficulty() {
		return this.previousDifficulty;
	}

	public String getLastBkg() {
		return this.lastBgk;
	}
	
	private void setLastBkg(String lastBkg) {
		this.lastBgk = lastBkg;
	}
	
	public void setLastBkgandSave(String lastBkg) {
		this.setLastBkg(lastBkg);
		this.store();
	}
	
	private void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
		this.setLastBkg("");
	}

	public int getScore(int diff) {
		return this.getDifficultyScore(diff);
	}
	
	public boolean storeIfBetterScore() {
		int diff = this.getDifficulty();
		boolean better = false;
		if (this.getDifficultyScore(diff) < this.totalCurrent) {
			this.setTotalScore(this.totalCurrent, diff);
			this.store();
			better = true;			
		}
			
		this.lastIsHighScore = better;
		return better;
	}
	
	public boolean isLastHighScore() {
		return this.lastIsHighScore;
	}

	public int getWorldId() {
		// return worldId;
		// todo: fix me!
		return this.getDifficulty();
	}

	public void setWorldId(int worldId) {
		this.worldId = worldId;
	}
}