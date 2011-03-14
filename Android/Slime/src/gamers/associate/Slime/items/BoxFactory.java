package gamers.associate.Slime.items;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class BoxFactory extends GameItemPhysicFactory<Box>{

	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected Box instantiate(float x, float y, float width, float height) {
		return new Box(this.spriteSheet, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Box item) {
	}
	
	@Override
	public Box create(float x, float y, float width, float height) {		
		Box box = super.create(x, y, width, height);		
		if (box != null) {
			CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(Box.texture));			
			box.setSprite(sprite);
		}
		
		return box;				
	}
}
