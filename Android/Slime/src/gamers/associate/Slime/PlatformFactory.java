package gamers.associate.Slime;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class PlatformFactory extends GameItemPhysicFactory<Platform>{
		
	@Override
	public Platform create(float x, float y, float width, float height) {		
		Platform platform = super.create(x, y, width, height);		
		if (platform != null) {
			CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(Platform.texture));			
			platform.setSprite(sprite);
		}
		
		return platform;				
	}

	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected Platform instantiate(float x, float y, float width, float height) {
		return new Platform(this.spriteSheet, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Platform item) {	
	}	
}
