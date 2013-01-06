package gamers.associate.Slime.game.achievements;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AchievementManager {
	@SuppressWarnings("rawtypes")
	private HashMap<Class, Achievement> achievements;
	private List<Achievement> orderedAchievements;
	
	
	@SuppressWarnings("rawtypes")
	public AchievementManager() {
		this.achievements = new HashMap<Class, Achievement>();
		this.orderedAchievements = new ArrayList<Achievement>();
		this.initAchievements();
	}
	
	private void initAchievements() {
		this.add(new LikeABirdAch());
		this.add(new SupermanAch());
		this.add(new RobinHoodAch());
		this.add(new CarabinAch());
		this.add(new GreenArrowAch());
		this.add(new DefuserAch());
		this.add(new FlameOnAch());
		this.add(new CallMeMax());
		this.add(new LiveLong());
	}

	@SuppressWarnings("rawtypes")
	public Achievement get(Class achievementClass) {
		return this.achievements.get(achievementClass);
	}
	
	public void add(Achievement achievement) {
		this.achievements.put(achievement.getClass(), achievement);
		this.orderedAchievements.add(achievement);
	}
	
	public void handleEndLevelAchievements() {
		for(Achievement ach : this.achievements.values()) {
			   if (ach.isEndLevel()) {
				ach.test();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void test(Class achievementClass) {
		this.achievements.get(achievementClass).test();
	}

	@SuppressWarnings("rawtypes")
	public List<Achievement> getAchievementsList() {
		return this.orderedAchievements;
	}

	public int getAchievementsCount() {
		return this.orderedAchievements.size();
	}
}
