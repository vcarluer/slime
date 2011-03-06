package gamers.associate.Slime.items;

import gamers.associate.Slime.Level;

import org.cocos2d.utils.javolution.MathLib;

public class HomeLevelHandler extends GameItem {
	private long startHome;		
	private double nextRand;
	private int maxSlime = 30;
	private double minSpawn = 0.5;
	private double maxSpawn = 3;
	private int slimeCount;
	private boolean isPaused;
	
	public HomeLevelHandler(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.startHome = System.currentTimeMillis();		
		this.slimeCount = 0;
	}
				
	@Override
	public void render(float delta) {				
		if (slimeCount < maxSlime) {
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
}
