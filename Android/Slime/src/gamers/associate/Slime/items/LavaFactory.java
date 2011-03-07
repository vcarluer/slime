package gamers.associate.Slime.items;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CCTexParams;

public class LavaFactory extends GameItemPhysicFactory<Lava> {

	@Override
	protected void createAnimList() {
		this.createAnim("lava", 1, 0.5f);
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
		item.initAnimation();
	}
	
	/*@Override
	public Lava create(float x, float y, float width, float height) {		
		Lava lava = super.create(x, y, width, height);		
		if (lava != null) {
			CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(Lava.texture));			
			lava.setSprite(sprite);
			CCTexParams texParams = new CCTexParams(GL10.GL_LINEAR, GL10.GL_LINEAR, GL10.GL_REPEAT, GL10.GL_REPEAT);
			lava.getSprite().getTexture().setTexParameters(texParams);
		}
		
		return lava;				
	}*/
}
