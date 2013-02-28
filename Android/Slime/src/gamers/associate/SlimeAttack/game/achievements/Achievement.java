package gamers.associate.SlimeAttack.game.achievements;

import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.layers.MessageLayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;

public abstract class Achievement implements Comparable<Achievement> {
	private String name;
	private String description;
	private boolean achieved;
	private boolean isEndLevel;
	private boolean lose;
	
	public Achievement(String name, String description, boolean endLevel, boolean lose) {
		this(name, description, endLevel);
		this.setLose(lose);
	}
	
	@SuppressWarnings("unused")
	public Achievement(String name, String description, boolean endLevel) {
		this.setName(name);
		this.setDescription(description);
		this.setEndLevel(endLevel);
		
		this.load();
		
		if (SlimeFactory.resetAchievements || SlimeFactory.unlockAllAchievement) {
			this.store();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
	
	public void test() {
		if (!this.achieved && this.testInternal()) {
			MessageLayer.get().show(this.getName());
			this.achieved = true;
			this.store();
		}
	}

	protected abstract boolean testInternal();

	public boolean isEndLevel() {
		return isEndLevel;
	}

	public void setEndLevel(boolean isEndLevel) {
		this.isEndLevel = isEndLevel;
	}
	
	protected void store() {
		BufferedWriter buffWriter = null;
		try {
			SlimeFactory.Log.d(SlimeAttack.TAG, "Storing User Info in " + this.getFileName());
			FileOutputStream fos = SlimeFactory.ContextActivity.openFileOutput(this.getFileName(), Context.MODE_PRIVATE);
			OutputStreamWriter streamWriter = new OutputStreamWriter(fos);
			buffWriter = new BufferedWriter(streamWriter);
			buffWriter.write(String.valueOf(this.achieved));
		} catch (FileNotFoundException ex) {
			SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR, file not found " + this.getFileName());
			ex.printStackTrace();
        } catch (IOException e1) {
			SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during opening or write of " + this.getFileName());
			e1.printStackTrace();
        } finally {
        	 //Close the BufferedWriter
            try {
                if (buffWriter != null) {
                	buffWriter.flush();
                	buffWriter.close();
                }
            } catch (IOException ex) {
            	SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during close of " + this.getFileName());
                ex.printStackTrace();
            }
        }
	}
	
	private String getFileName() {
		return this.getClass().getName();
	}

	protected void load() {
		java.io.InputStream inputStream;		
		String fileName = this.getFileName();
		try {
			if (SlimeFactory.clearLevelInfo) {
				SlimeFactory.ContextActivity.deleteFile(fileName);
			} else {
				if (SlimeFactory.ContextActivity.getFileStreamPath(fileName).exists()) {
					SlimeFactory.Log.d(SlimeAttack.TAG, "Loading User Info from " + fileName);
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
									if (!SlimeFactory.resetAchievements) {
										if (SlimeFactory.unlockAllAchievement) {
											this.setAchieved(true);
										} else {
											this.setAchieved(Boolean.valueOf(line).booleanValue());
										}										
									} else {
										this.setAchieved(false);
									}
									
									break;
								default:
									break;
								}												
							} catch (Exception e) {
								SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during read of " + fileName + " line " + String.valueOf(i));
								e.printStackTrace();
							}									
						}
					} catch (IOException e) {
						SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during read of " + fileName + " line " + String.valueOf(i));
						e.printStackTrace();
					} finally {
						if (buffreader != null) {
							buffreader.close();
						}
					}
				}  else {
					SlimeFactory.Log.d(SlimeAttack.TAG, "No need to load User Info from " + fileName);
					if (SlimeFactory.unlockAllAchievement) {
						this.setAchieved(true);
					} else {
						this.setAchieved(false);
					}					
				}				
			}			
		} catch (IOException e1) {
			SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during opening of " + fileName);
			e1.printStackTrace();
		}	
	}

	public boolean isLose() {
		return lose;
	}

	public void setLose(boolean lose) {
		this.lose = lose;
	}
	
	public int compareTo(Achievement otherAchievement) {
        return this.name.compareTo(otherAchievement.name);
    }
}
