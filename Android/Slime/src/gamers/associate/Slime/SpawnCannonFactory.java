package gamers.associate.Slime;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class SpawnCannonFactory extends GameItemPhysicFxFactory<SpawnCannon>{
		
	@Override
	public SpawnCannon create(float x, float y, float width, float height) {		
		SpawnCannon canon = super.create(x, y, width, height);		
		if (canon != null) {
			CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(SpawnCannon.texture));			
			canon.setSprite(sprite);
		}
		
		return canon;				
	}

	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected SpawnCannon instantiate(float x, float y, float width, float height) {
		return new SpawnCannon(this.spriteSheet, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(SpawnCannon item) {	
	}	
}
