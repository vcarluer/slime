package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemCocosFactory;
import gamers.associate.SlimeAttack.items.base.SpriteSheetFactory;

public class GateFactory extends GameItemCocosFactory<Gate> {

	@Override
	protected void createAnimList() {		
		this.createAnim(Gate.Anim_Closed, 1);
		this.createAnim(Gate.Anim_Opened, 1);
		this.createAnim(Gate.Anim_Opening, 3, 2f);		
	}

	@Override
	protected String getPlistPng() {
		return "worlds-items";
	}

	@Override
	protected Gate instantiate(float x, float y, float width, float height) {		
		return new Gate(x, y, width, height);
	}

	@Override
	protected void runFirstAnimations(Gate item) {
		item.closed();
	}

	public void attachSpritesheet() {
		SpriteSheetFactory.attach(this.getPlistPng(), this.rootNode);
	}
	
	public void detachSpritesheet() {
		SpriteSheetFactory.detach(this.getPlistPng(), this.rootNode);
	}
}
