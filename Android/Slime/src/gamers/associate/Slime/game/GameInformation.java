package gamers.associate.Slime.game;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.achievements.AchievementStatistics;
import gamers.associate.Slime.layers.SurvivalItemLayer;

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
	private boolean survivalGameOverEasy;
	private boolean survivalGameOverNormal;
	private boolean survivalGameOverHard;
	private boolean survivalGameOverExtrem;
	private int totalCurrentEasy;
	private int totalCurrentNormal;
	private int totalCurrentHard;
	private int totalCurrentExtrem;
	private boolean newUnlockSurvival;
	private int newUnlockedDifficulty;
	private int easyInARow;
	private int normalInARow;
	private int hardInARow;
	private int extremInARow;
	private int currentEasyInARow;
	private int currentNormalInARow;
	private int currentHardInARow;
	private int currentExtremInARow;
	private SurvivalItemLayer currentSurvival;
	private boolean story1Finished; // Bad place but quick
	
	public GameInformation() {		
		this.survivalGameOverEasy = true;
		this.survivalGameOverNormal = true;
		this.survivalGameOverHard = true;
		this.survivalGameOverExtrem = true;

		this.maxLevelDifficulty = this.levelDifficulty = LevelDifficulty.Easy;		
		this.setLevelNum(0);
		this.load();
		
		if (resetHighScores) {
			this.totalScoreEasy = 0;
			this.totalScoreNormal = 0;
			this.totalScoreHard = 0;
			this.totalScoreExtrem = 0;
			this.maxLevelDifficulty = LevelDifficulty.Easy;
			this.survivalGameOverEasy = true;
			this.survivalGameOverNormal = true;
			this.survivalGameOverHard = true;
			this.survivalGameOverExtrem = true;
			this.easyInARow = 0;
			this.normalInARow = 0;
			this.hardInARow = 0;
			this.extremInARow = 0;
			this.story1Finished = false;
			this.store();
		}
		
		if (SlimeFactory.unlockAll) {
			this.setStory1Finished(true);
		}
	}
	
	public void unlockDifficulty(int levelDifficulty) {
		if (levelDifficulty > this.maxLevelDifficulty) {
			this.maxLevelDifficulty = levelDifficulty;
			AchievementStatistics.unlockDifficulty = this.maxLevelDifficulty;			
			this.store();
		}
	}
	
	public void unlockNextDifficultySurvival() {
		int nextDiff = LevelDifficulty.getNextDifficulty(this.levelDifficulty);
		if (nextDiff > this.maxLevelDifficulty) {
			this.maxLevelDifficulty = nextDiff;
			AchievementStatistics.unlockDifficulty = this.maxLevelDifficulty;
			this.newUnlockSurvival = true;
			this.newUnlockedDifficulty = this.maxLevelDifficulty;
			this.store();
		}
	}
	
	public boolean consumeNewUnlockSurvival() {
		boolean value = this.newUnlockSurvival;
		this.newUnlockSurvival = false;
		return value;
	}
	
	public int getUnlockedSurvival() {
		return this.newUnlockedDifficulty;
	}
	
	private void setLevelDifficulty(int leveldifficulty) {
		AchievementStatistics.levelDiff = leveldifficulty;
		this.lastScore = 0;
		this.lastIsHighScore = false;
		this.previousDifficulty = this.levelDifficulty;
		this.levelDifficulty = leveldifficulty;
		this.totalCurrent = 0;
//		this.setTotalScore(0);
		if (this.levelDifficulty > this.maxLevelDifficulty) {
			this.maxLevelDifficulty = this.levelDifficulty;
			AchievementStatistics.unlockDifficulty = this.maxLevelDifficulty;
		}
	}
	
	public void setDifficulty(int levelDifficulty) {
		this.setLevelDifficulty(levelDifficulty);
		this.store();
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
		// this.restoreCurrentScore();
		this.levelNum = 0;
		this.store();
	}

	public void levelUp() {		
		this.setLevelNum(levelNum + 1);
		this.lastScore = 0;
		if (this.levelNum > this.getLevelMax() && this.levelDifficulty != LevelDifficulty.Extrem) {
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
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.survivalGameOverEasy));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.survivalGameOverNormal));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.survivalGameOverHard));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.survivalGameOverExtrem));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.totalCurrentEasy));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.totalCurrentNormal));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.totalCurrentHard));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.totalCurrentExtrem));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.easyInARow));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.normalInARow));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.hardInARow));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.extremInARow));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.isStory1Finished()));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.currentEasyInARow));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.currentNormalInARow));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.currentHardInARow));
			buffWriter.newLine();
			buffWriter.write(String.valueOf(this.currentExtremInARow));
			
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
							case 9:
								this.survivalGameOverEasy = Boolean.valueOf(line).booleanValue();
								break;
							case 10:
								this.survivalGameOverNormal = Boolean.valueOf(line).booleanValue();
								break;
							case 11:
								this.survivalGameOverHard = Boolean.valueOf(line).booleanValue();
								break;
							case 12:
								this.survivalGameOverExtrem = Boolean.valueOf(line).booleanValue();
								break;
							case 13:
								this.totalCurrentEasy = Integer.valueOf(line).intValue();
								break;
							case 14:
								this.totalCurrentNormal = Integer.valueOf(line).intValue();
								break;
							case 15:
								this.totalCurrentHard = Integer.valueOf(line).intValue();
								break;
							case 16:
								this.totalCurrentExtrem = Integer.valueOf(line).intValue();
								break;
							case 17:
								this.easyInARow = Integer.valueOf(line).intValue();
								break;
							case 18:
								this.normalInARow = Integer.valueOf(line).intValue();
								break;
							case 19:
								this.hardInARow = Integer.valueOf(line).intValue();
								break;
							case 20:
								this.extremInARow = Integer.valueOf(line).intValue();
								break;
							case 21:
								this.story1Finished = Boolean.valueOf(line).booleanValue();
								break;
							case 22:
								this.currentEasyInARow = Integer.valueOf(line).intValue();
								break;
							case 23:
								this.currentNormalInARow = Integer.valueOf(line).intValue();
								break;
							case 24:
								this.currentHardInARow = Integer.valueOf(line).intValue();
								break;
							case 25:
								this.currentExtremInARow = Integer.valueOf(line).intValue();
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
	
	private int previousTotalCurrent;
	
	public void addLevelScore(int score) {
		this.lastScore = score;
		this.setPreviousTotalCurrent(this.totalCurrent);
		this.totalCurrent += score;
		switch (this.levelDifficulty) {
			default:
			case LevelDifficulty.Easy:
				this.totalCurrentEasy = this.totalCurrent;
				break;
			case LevelDifficulty.Normal:
				this.totalCurrentNormal = this.totalCurrent;
				break;
			case LevelDifficulty.Hard:
				this.totalCurrentHard = this.totalCurrent;
				break;
			case LevelDifficulty.Extrem:
				this.totalCurrentExtrem = this.totalCurrent;
				break;
		}
		
		this.store();
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
		AchievementStatistics.levelNum = levelNum;
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

	public boolean isSurvivalGameOver() {
		return this.isSurvivalGameOver(this.levelDifficulty);
	}
	
	public boolean isSurvivalGameOver(int diff) {
		switch (diff) {
			default:
			case LevelDifficulty.Easy:
				return this.survivalGameOverEasy;
			case LevelDifficulty.Normal:
				return this.survivalGameOverNormal;
			case LevelDifficulty.Hard:
				return this.survivalGameOverHard;
			case LevelDifficulty.Extrem:
				return this.survivalGameOverExtrem;
		}
	}

	public void setSurvivalGameOver(boolean survivalGameOver) {
		switch(this.levelDifficulty) {
			default:
			case LevelDifficulty.Easy:
				this.survivalGameOverEasy = survivalGameOver;
				this.totalCurrentEasy = 0;
				break;
			case LevelDifficulty.Normal:
				this.survivalGameOverNormal = survivalGameOver;
				this.totalCurrentNormal = 0;
				break;
			case LevelDifficulty.Hard:
				this.survivalGameOverHard = survivalGameOver;
				this.totalCurrentHard = 0;
				break;
			case LevelDifficulty.Extrem:
				this.survivalGameOverExtrem = survivalGameOver;
				this.totalCurrentExtrem = 0;
				break;
		}
		
		this.store();
	}
	
	public boolean canContinueSurvival() {
		return this.canContinueSurvival(this.levelDifficulty);
	}
	
	public boolean canContinueSurvival(int difficulty) {
		switch (difficulty) {
			default:
			case LevelDifficulty.Easy:
				return !this.survivalGameOverEasy;
			case LevelDifficulty.Normal:
				return !this.survivalGameOverNormal;
			case LevelDifficulty.Hard:
				return !this.survivalGameOverHard;
			case LevelDifficulty.Extrem:
				return !this.survivalGameOverExtrem;
		}
	}

	public int getPreviousTotalCurrent() {
		return previousTotalCurrent;
	}

	public void setPreviousTotalCurrent(int previousTotalCurrent) {
		this.previousTotalCurrent = previousTotalCurrent;
	}
	
	public void setInARowLose() {
		int score = this.levelNum - 1;
		this.setCurrentInARow(0);
		this.setInARow(score);
		this.store();
	}
	public void setInARow() {
		int score = this.levelNum;
		this.setCurrentInARow(score);
		this.setInARow(score);
		this.store();		
	}
	
	private void setCurrentInARow(int score) {		
		switch (this.levelDifficulty) {
		default:
		case LevelDifficulty.Easy:
			this.currentEasyInARow = score;		
			break;
		case LevelDifficulty.Normal:
			this.currentNormalInARow = score;
			break;
		case LevelDifficulty.Hard:
			this.currentHardInARow = score;
			break;
		case LevelDifficulty.Extrem:
			this.currentExtremInARow = score;
			break;
		}
		
		AchievementStatistics.inARow = score;
	}
	
	private void setInARow(int score) {		
		switch (this.levelDifficulty) {
		default:
		case LevelDifficulty.Easy:
			if (score > this.easyInARow) {
				this.easyInARow = score;
			}			
			break;
		case LevelDifficulty.Normal:
			if (score > this.normalInARow) {
				this.normalInARow = score;
			}	
			break;
		case LevelDifficulty.Hard:
			if (score > this.hardInARow) {
				this.hardInARow = score;
			}	
			break;
		case LevelDifficulty.Extrem:
			if (score > this.extremInARow) {
				this.extremInARow = score;
			}	
			break;
		}
		
		AchievementStatistics.inARow = score;
	}
	
	public int getInARow(int diff) {
		switch (diff) {
		default:
		case LevelDifficulty.Easy:			
			return this.easyInARow;
		case LevelDifficulty.Normal:
			return this.normalInARow;	
		case LevelDifficulty.Hard:
			return this.hardInARow;	
		case LevelDifficulty.Extrem:
			return this.extremInARow;	
		}
	}
	
	public int getCurrentInARow(int diff) {
		switch (diff) {
		default:
		case LevelDifficulty.Easy:			
			return this.currentEasyInARow;
		case LevelDifficulty.Normal:
			return this.currentNormalInARow;	
		case LevelDifficulty.Hard:
			return this.currentHardInARow;	
		case LevelDifficulty.Extrem:
			return this.currentExtremInARow;
		}
	}

	public SurvivalItemLayer getCurrentSurvival() {
		return currentSurvival;
	}

	public void setCurrentSurvival(SurvivalItemLayer currentSurvival) {
		this.currentSurvival = currentSurvival;
	}

	public boolean isStory1Finished() {
		return story1Finished;
	}

	public void setStory1Finished(boolean story1Finished) {
		this.story1Finished = story1Finished;
		this.store();
	}
}