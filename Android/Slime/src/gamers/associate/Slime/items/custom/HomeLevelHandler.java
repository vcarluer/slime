package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.utils.javolution.MathLib;

public class HomeLevelHandler extends GameItem {
	private long startHome;		
	private double nextRand;
	private int maxSlime = 50;
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
			if (Level.currentLevel.slimies().size() < maxSlime && !this.isPaused) {
				Level level = Level.currentLevel;
				if (level != null) {
					long elapsed = (System.currentTimeMillis() - startHome) / 1000;
					nextRand = MathLib.random(minSpawn, maxSpawn);
					if (elapsed > nextRand && !this.isPaused) {
						this.getSpawnPortal().spawn();												
						this.startHome = System.currentTimeMillis();
					}
				}
			}
			else {
				if (!this.isPaused) {
					if (Level.currentLevel.slimies().size() > 0) {
						Slimy slimy = Level.currentLevel.slimies().get(0);
						CCFadeTo fi = CCFadeTo.action(0.5f, 0);
						CCCallFunc call = CCCallFunc.action(this, "removeSlimy");
						CCSequence seq = CCSequence.actions(fi, call);
						slimy.getSprite().runAction(seq);						
					}					
				}
			}
		}
	}
	
	public void removeSlimy() {
		Slimy slimy = Level.currentLevel.slimies().get(0);
		Level.currentLevel.addItemToRemove(slimy);
	}

	public SpawnPortal getSpawnPortal() {
		return spawnPortal;
	}

	public void setSpawnPortal(SpawnPortal spawnPortal) {
		this.spawnPortal = spawnPortal;
	}
}
