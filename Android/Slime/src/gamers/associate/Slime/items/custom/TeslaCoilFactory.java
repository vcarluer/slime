package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;

public class TeslaCoilFactory extends GameItemPhysicFactory<TeslaCoil> {
	private boolean startOn;
	private float strikeDistance;
	
	@Override
	protected void createAnimList() {
		this.createAnim(TeslaCoil.Anim_Ligthning, 5);
		this.createAnim(TeslaCoil.Anim_Wait, 1);
	}

	@Override
	protected String getPlistPng() {
		return "items";
	}

	@Override
	protected TeslaCoil instantiate(float x, float y, float width, float height) {
		return new TeslaCoil(x, y, width, height, this.world, this.worldRatio, this.startOn, this.strikeDistance);
	}

	@Override
	protected void runFirstAnimations(TeslaCoil item) {
		item.initState();
	}

	public TeslaCoil create(float x, float y, float width, float height, String name, boolean startOn, float strikeDistance) {
		this.startOn = startOn;
		this.strikeDistance = strikeDistance;
		return this.create(name, x, y, width, height);
	}
	
	public TeslaCoil createBL(float x, float y, float width, float height, String name, boolean startOn, float strikeDistance) {
		return this.create(x + width / 2f, y + height / 2f, width, height, name, startOn, strikeDistance);
	}
}
