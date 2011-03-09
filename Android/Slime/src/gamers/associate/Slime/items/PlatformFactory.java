package gamers.associate.Slime.items;


import gamers.associate.Slime.CCSpriteRepeat;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;

import android.graphics.Bitmap;

public class PlatformFactory extends GameItemPhysicFactory<Platform>{
		
	@Override
	public Platform create(float x, float y, float width, float height) {		
		Platform platform = super.create(x, y, width, height);		
		platform.setSprite(null);
		if (platform != null) {
			//CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(Platform.texture));			
			//CCTexture2D tex = CCTextureCache.sharedTextureCache().addImage(Platform.texture);			
			//CCSprite sprite = CCSprite.sprite(tex);
			CCSprite sprite = CCSpriteRepeat.sprite(Platform.texture, width, height);
			platform.setSprite(sprite);
			//platform.setTexture(tex);
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
		return new Platform(this.rootNode, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Platform item) {	
	}	
}
