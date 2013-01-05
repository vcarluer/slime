package gamers.associate.Slime.game.achievements;


import java.util.HashMap;

public class AchievementManager {
	@SuppressWarnings("rawtypes")
	private HashMap<Class, Achievement> achievements;
	
	
	@SuppressWarnings("rawtypes")
	public AchievementManager() {
		this.achievements = new HashMap<Class, Achievement>();
		this.initAchievements();
	}
	
	private void initAchievements() {
		this.add(new LikeABirdAch());
		this.add(new SupermanAch());
	}

	@SuppressWarnings("rawtypes")
	public Achievement get(Class achievementClass) {
		return this.achievements.get(achievementClass);
	}
	
	public void add(Achievement achievement) {
		this.achievements.put(achievement.getClass(), achievement);
	}
	
	public void handleEndLevelAchievements() {
		int key = 0;
		for(Achievement ach : this.achievements.values()) {
			   if (ach.isEndLevel()) {
				ach.test();
			}
		}
	}
	
	public void test(Class achievementClass) {
		this.achievements.get(achievementClass).test();
	}
}
