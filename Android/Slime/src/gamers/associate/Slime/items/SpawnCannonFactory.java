package gamers.associate.Slime.items;


import gamers.associate.Slime.CCSpriteRepeat;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class SpawnCannonFactory extends GameItemPhysicFactory<SpawnCannon>{
		
	@Override
	public SpawnCannon create(float x, float y, float width, float height) {		
		SpawnCannon canon = super.create(x, y, width, height);		
		if (canon != null) {
			CCSprite sprite = CCSpriteRepeat.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(SpawnCannon.texture), SpawnCannon.Default_Width, SpawnCannon.Default_Height);			
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
		return new SpawnCannon(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(SpawnCannon item) {	
	}	
}
