package gamers.associate.Slime.game.achievements;


import gamers.associate.Slime.layers.MessageLayer;
import android.util.SparseArray;

public class AchievementManager {
	private SparseArray<Achievement> achievements;
	private int slimyLandCount;
	
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
	
	public void slimyLand() {
		this.slimyLandCount++;
	}
	
	public void handleEndLevelAchievements() {
		Achievement ach = null; 
		if (this.slimyLandCount == 0) {
			ach = this.get(LikeABirdAch.Code);
			MessageLayer.get().show(ach.getName());
		} else {
			this.slimyLandCount = 0;
		}
	}
}
