package gamers.associate.Slime;

import org.cocos2d.utils.javolution.MathLib;

public class HomeLevelHandler {
	private long startHome;	
	private boolean runHome;
	private double nextRand;
	private int maxSlime = 30;
	private double minSpawn = 0.5;
	private double maxSpawn = 3;
	private int slimeCount;
	private boolean isPaused;
	
	public void startHomeLevel() {
		this.startHome = System.currentTimeMillis();		
		this.runHome = true;
		this.slimeCount = 0;		
	}	
	
	public void setPause(boolean pause) {
		this.isPaused = pause;
	}
				
	public void tick() {				
		if (slimeCount < maxSlime && runHome) {
			Level level = Level.currentLevel;
			if (level != null) {
				long elapsed = (System.currentTimeMillis() - startHome) / 1000;
				nextRand = MathLib.random(minSpawn, maxSpawn);
				if (elapsed > nextRand && !this.isPaused) {
					level.spawnSlime();
					this.slimeCount++;
					this.startHome = System.currentTimeMillis();										
				}
			}
		}
	}
	
	public void stopHomeLevel() {
		this.runHome = false;
	}
}
