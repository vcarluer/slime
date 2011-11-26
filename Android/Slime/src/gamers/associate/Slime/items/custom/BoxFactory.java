package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;


public class BoxFactory extends GameItemPhysicFactory<Box>{
	private int currentType;
	private boolean isStatic;
	private boolean isStickable;	

	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "glasswork";
	}

	@Override
	protected Box instantiate(float x, float y, float width, float height) {
		return new Box(x, y, width, height, this.world, this.worldRatio, this.currentType, this.isStatic, this.isStickable);
	}

	@Override
	protected void runFirstAnimations(Box item) {
	}
		
	public Box create(float x, float y, float width, float height, int boxType, boolean isStatic, boolean isStickable) {
		this.currentType = boxType;
		this.isStatic = isStatic;
		this.isStickable = isStickable;
		Box box = this.create(x, y, width, height);
		return box;
	}
	
	public Box createTubeBL(float x, float y, float width, float height, boolean isStatic, boolean isStickable) {
		return this.create(x + width / 2, y + height / 2, width, height, Box.tube, isStatic, isStickable);
	}
	
	public Box createMultitubesBL(float x, float y, float width, float height, boolean isStatic, boolean isStickable) {
		return this.create(x + width / 2, y + height / 2, width, height, Box.multitubes, isStatic, isStickable);
	}
	
	public Box createGlassboxBL(float x, float y, float width, float height, boolean isStatic, boolean isStickable) {
		return this.create(x + width / 2, y + height / 2, width, height, Box.glassbox, isStatic, isStickable);
	}
	
	public Box createBottleBL(float x, float y, float width, float height, boolean isStatic, boolean isStickable) {
		return this.create(x + width / 2, y + height / 2, width, height, Box.bottle, isStatic, isStickable);
	}
}
