package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;

import org.cocos2d.utils.javolution.MathLib;

public class HomeLevelHandler extends GameItem {
	private long startHome;		
	private double nextRand;
	private int maxSlime = 42;
	private double minSpawn = 0.5;
	private double maxSpawn = 3;
	private boolean isPaused;
	private SpawnPortal spawnPortal;
	
	public HomeLevelHandler(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.startHome = System.currentTimeMillis();
	}
	
	public void setPortal(SpawnPortal portal) {
		this.setSpawnPortal(portal);
	}
				
	@Override
	public void render(float delta) {
		if (this.getSpawnPortal() != null) {
			if (!this.isPaused) {
				Level level = Level.currentLevel;
				if (level != null) {
					long elapsed = (System.currentTimeMillis() - startHome) / 1000;
					nextRand = MathLib.random(minSpawn, maxSpawn);
					if (elapsed > nextRand) {
						this.getSpawnPortal().spawn();												
						this.startHome = System.currentTimeMillis();
					}
				}
			}					
			
			if (Level.currentLevel.slimies().size() > maxSlime) {
				float delCount = Level.currentLevel.slimies().size() - maxSlime;
				for(int i = 0; i < delCount; i++) {
					Slimy slimy = Level.currentLevel.slimies().get(i);
					slimy.fadeDestroy();
				}				
			}								
		}
	}

	public SpawnPortal getSpawnPortal() {
		return spawnPortal;
	}

	public void setSpawnPortal(SpawnPortal spawnPortal) {
		this.spawnPortal = spawnPortal;
	}
}
