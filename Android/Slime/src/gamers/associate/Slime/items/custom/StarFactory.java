package gamers.associate.Slime.items.custom;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCSprite;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class StarFactory extends GameItemPhysicFactory<Star> {

	@Override
	protected void createAnimList() {
		this.createAnim(Star.Anim_Wait, 4);
		this.createAnim(Star.Anim_Fade, 4);
	}

	@Override
	protected String getPlistPng() {
		return "items";
	}

	@Override
	protected Star instantiate(float x, float y, float width, float height) {		
		return new Star(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(Star item) {		
		item.waitAnim();		
	}
	
	public Star createBL(float x, float y, float width, float height) {
		return this.create(x + width / 2, y + height / 2, width, height);
	}
	
	public CCSprite getAnimatedStar() {
		CCSprite star = new CCSprite();
		CCAnimate animate = CCAnimate.action(this.sharedAnimations.get(Star.Anim_Wait), false);
		CCRepeatForever repeat = CCRepeatForever.action(animate);
		star.runAction(repeat);
		return star;
	}
	
	public float getStarReferenceWidth() {
		return Star.Reference_Width;
	}
}
