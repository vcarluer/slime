package gamers.associate.Slime.items;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class BumperFactory extends GameItemPhysicFactory<Bumper> {

	@Override
	protected void createAnimList() {
		// this.createAnim(Bumper.Anim_Wait, 1);
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected Bumper instantiate(float x, float y, float width, float height) {
		// TODO: change this to spriteSheet when using compressed texture file
		return new Bumper(this.rootNode, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Bumper item) {
		item.waitAnim();		
	}

	@Override
	public Bumper create(float x, float y, float width, float height) { 
		return this.create(x, y, width, height, Bumper.Default_Powa);		
	}
	
	public Bumper create(float x, float y, float width, float height, float powa) {
		Bumper bumper = super.create(x, y, width, height);
		bumper.setPowa(powa);
		if (bumper != null) {
			// TODO : use sprite frame here
			CCSprite sprite = CCSprite.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(Bumper.Texture_Wait));			
			//CCSprite sprite = CCSprite.sprite(Bumper.Texture_Wait);
			bumper.setSprite(sprite);
			
		}
		
		return bumper;
	}

}
