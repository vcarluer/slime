package gamers.associate.Slime.items;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class LavaFactory extends GameItemPhysicFactory<Lava> {

	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected Lava instantiate(float x, float y, float width, float height) {
		return new Lava(this.spriteSheet, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Lava item) {
	}
	
	@Override
	public Lava create(float x, float y, float width, float height) {		
		Lava lava = super.create(x, y, width, height);		
		if (lava != null) {
			CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(Lava.texture));			
			lava.setSprite(sprite);
		}
		
		return lava;				
	}
}
