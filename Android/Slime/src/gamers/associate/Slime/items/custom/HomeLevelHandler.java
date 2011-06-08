package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;

import org.cocos2d.utils.javolution.MathLib;

public class HomeLevelHandler extends GameItem {
	private long startHome;		
	private double nextRand;
	private int maxSlime = 30;
	private double minSpawn = 0.5;
	private double maxSpawn = 3;
	private int slimeCount;
	private boolean isPaused;
	private SpawnPortal spawnPortal;
	
	public HomeLevelHandler(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.startHome = System.currentTimeMillis();		
		this.slimeCount = 0;
	}
	
	public void setPortal(SpawnPortal portal) {
		this.spawnPortal = portal;
	}
				
	@Override
	public void render(float delta) {
		if (this.spawnPortal != null) {
			if (slimeCount < maxSlime && !this.isPaused) {
				Level level = Level.currentLevel;
				if (level != null) {
					long elapsed = (System.currentTimeMillis() - startHome) / 1000;
					nextRand = MathLib.random(minSpawn, maxSpawn);
					if (elapsed > nextRand && !this.isPaused) {
						this.spawnPortal.spawn();
						this.slimeCount++;
						this.startHome = System.currentTimeMillis();										
					}
				}
			}
		}
	}
}
