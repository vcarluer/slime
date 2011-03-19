package gamers.associate.Slime.items;

import org.cocos2d.nodes.CCSprite;

public class SpawnPortalFactory extends GameItemCocosFactory<SpawnPortal> {
		
	public SpawnPortal createAndMove(float x, float y, float moveBy, float speed) {
		SpawnPortal portal = this.create(x, y);
		if (portal != null) {
			portal.MovePortalInLine(moveBy, speed);
		}
		
		return portal;			
	}
	
	@Override
	protected void createAnimList() {
		this.createAnim(SpawnPortal.Anim_Spawn_Portal, 4);
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected SpawnPortal instantiate(float x, float y, float width,
			float height) {
		return new SpawnPortal(this.spriteSheet, x, y, width, height);
	}

	@Override
	protected void runFirstAnimations(SpawnPortal item) {
		item.createPortal();
	}
}