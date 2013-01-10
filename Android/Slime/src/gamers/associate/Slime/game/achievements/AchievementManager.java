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
		this.add(new DividedAch());
		this.add(new RoxedElectronAch());
		this.add(new SquishAch());
		this.add(new MotherShipAch());				
		this.add(new CallMeMaxAch());
		this.add(new LiveLongAch());
		this.add(new NormalSlimeAch());
		this.add(new AreYouSeriousAch());		
		this.add(new YipikayeAch());		
		this.add(new GoldenBoyAch());
		this.add(new GoldFeverAch());		
		this.add(new SilverSurferAch());
		this.add(new BronzeAgeAch());
		this.add(new JustNeededAch());
		this.add(new DontStopAch());
		this.add(new SonicBoomAch());
		this.add(new GreenFlashAch());
		this.add(new PrisonBreakAch());
		this.add(new KeepDiggingAch());
		this.add(new MonteCristoAch());
		this.add(new MasterEvasionAch());		
		this.add(new GreenSquidAch());		
		this.add(new LuckyLukeAch());	
		this.add(new SniperAch());		
		this.add(new TheDoctorAch());		
		this.add(new RedAlertAch());
		this.add(new DontPushAch());
		this.add(new PuppetMasterAch());
		this.add(new NaniAch());
		this.add(new TheImmortalAch());
		this.add(new UnlockNormalAch());
		this.add(new UnlockHardAch());
		this.add(new UnlockExtremAch());
		this.add(new MarioStyleAch());
		this.add(new TimeOutAch());
		this.add(new PushButtonAch());
		this.add(new HardcoreAch());
	}

	@SuppressWarnings("rawtypes")
	public Achievement get(Class achievementClass) {
		return this.achievements.get(achievementClass);
	}
	
	public void add(Achievement achievement) {
		this.achievements.put(achievement.getClass(), achievement);
		this.orderedAchievements.add(achievement);
	}
	
	public void handleEndLevelAchievements(boolean lose) {		
		for(Achievement ach : this.orderedAchievements) {
			if (ach.isEndLevel()) {
				if (ach.isLose() == lose) {				
					ach.test();
				}
			}
		}
	}
	
	public int getAchievedCount() {
		int count = 0;
		for(Achievement ach : this.achievements.values()) {
			if (ach.isAchieved()) {
				count++;
			}
		}
		
		return count;
	}
	
	@SuppressWarnings("rawtypes")
	public void test(Class achievementClass) {
		this.achievements.get(achievementClass).test();
	}

	public List<Achievement> getAchievementsList() {
		return this.orderedAchievements;
	}

	public int getAchievementsCount() {
		return this.orderedAchievements.size();
	}
}
