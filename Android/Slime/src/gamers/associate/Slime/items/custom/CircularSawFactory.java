package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class CircularSawFactory extends GameItemPhysicFactory<CircularSaw>{
	private boolean isOn;
	
	@Override
	protected void createAnimList() {
		this.createAnim(CircularSaw.Anim_Running, 2);
		this.createAnim(CircularSaw.Anim_Wait, 1);
	}

	@Override
	protected String getPlistPng() {
		return "items";
	}

	@Override
	protected CircularSaw instantiate(float x, float y, float width, float height) {
		CircularSaw saw = new CircularSaw(x, y, width, height, this.world, this.worldRatio);		
		return saw;
	}

	@Override
	protected void runFirstAnimations(CircularSaw item) {
		item.initAnimation();
	}
		
	
	public CircularSaw create(float x, float y, float width, float height, String name, boolean startOn) {		
		this.isOn = startOn;

		CircularSaw saw = super.create(x, y, width, height);
		saw.setName(name);				
		return saw;
	}
	
	public CircularSaw createBL(float x, float y, float width, float height, String name, boolean startOn) {
		return this.create(x + width / 2, y + height / 2, width, height, name, startOn);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.ItemFactoryBase#initItem(gamers.associate.Slime.items.base.GameItemCocos)
	 */
	@Override
	protected void initItem(CircularSaw item) {
		item.setStartOn(this.isOn);
		super.initItem(item);
	}	
}
