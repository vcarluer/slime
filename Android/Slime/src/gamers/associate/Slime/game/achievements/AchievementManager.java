package gamers.associate.Slime.game.achievements;


import android.util.SparseArray;

public class AchievementManager {
	private SparseArray<Achievement> achievements;
	
	
	public AchievementManager() {
		this.achievements = new SparseArray<Achievement>();
		this.initAchievements();
	}
	
	private void initAchievements() {
		this.add(new LikeABirdAch());
	}

	public Achievement get(Integer code) {
		return this.achievements.get(code);
	}
	
	public void add(Achievement achievement) {
		this.achievements.append(achievement.getCode(), achievement);
	}
	
	public void handleEndLevelAchievements() {
		int key = 0;
		for(int i = 0; i < this.achievements.size(); i++) {
		   key = this.achievements.keyAt(i);
		   // get the object by the key.
		   Achievement ach = this.achievements.get(key);
		   
		   if (ach.isEndLevel()) {
				ach.test();
			}
		}
	}
}
