package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemCocosFactory;


public class MenuNodeFactory extends GameItemCocosFactory<MenuNode> {			
	private String name;
	private String target;
	
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
	
	public MenuNode create(float x, float y, float width, float height, String name, String target) {
		this.name = name;
		this.target = target;
		MenuNode node = super.create(x, y, width, height);			
		return node;
	}
	
	public MenuNode createBL(float x, float y, float width, float height, String name, String target) {
		return this.create(x + width / 2, y + height / 2, width, height, name, target);
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.ItemFactoryBase#initItem(gamers.associate.Slime.items.base.GameItemCocos)
	 */
	@Override
	protected void initItem(MenuNode item) {
		item.setNodeId(this.name);	
		item.setTargetLevel(this.target);
		super.initItem(item);
	}
}