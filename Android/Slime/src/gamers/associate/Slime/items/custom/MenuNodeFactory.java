package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemCocosFactory;


public class MenuNodeFactory extends GameItemCocosFactory<MenuNode> {			
	@Override
	protected void createAnimList() {
		this.createAnim(MenuNode.Anim_Node, 4);
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected MenuNode instantiate(float x, float y, float width,
			float height) {
		return new MenuNode(x, y, width, height);
	}

	@Override
	protected void runFirstAnimations(MenuNode item) {
		item.createPortal();
	}
}