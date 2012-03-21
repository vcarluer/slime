package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemCocosFactory;

public class StarCounterFactory extends GameItemCocosFactory<StarCounter> {

	public static final String CONTROL_STARS = "control-stars";
	private StarCounterType currentType;

	@Override
	protected void createAnimList() {
		this.createAnim(StarCounter.Anim_Cell_Empty, 1);
		this.createAnim(StarCounter.Anim_Cell_Gold, 1);		
		this.createAnim(StarCounter.Anim_Cell_Green, 1);
		this.createAnim(StarCounter.Anim_Cell_Target_Empty, 1);
		this.createAnim(StarCounter.Anim_Cell_Target_Green, 1);
		this.createAnim(StarCounter.Anim_End_Empty, 1);
		this.createAnim(StarCounter.Anim_End_Gold, 1);
		this.createAnim(StarCounter.Anim_End_Green, 1);
		this.createAnim(StarCounter.Anim_End_Target_Empty, 1);
		this.createAnim(StarCounter.Anim_End_Target_Green, 1);
		this.createAnim(StarCounter.Anim_Start_Empty, 1);
		this.createAnim(StarCounter.Anim_Start_Gold, 1);
		this.createAnim(StarCounter.Anim_Start_Green, 1);
	}

	@Override
	protected String getPlistPng() {
		return CONTROL_STARS;
	}

	@Override
	protected StarCounter instantiate(float x, float y, float width,
			float height) {
		return new StarCounter(x, y, width, height, this.currentType);
	}

	@Override
	protected void runFirstAnimations(StarCounter item) {		
	}
	
	public StarCounter create(float x, float y, StarCounterType counterType) {
		this.currentType = counterType;
		return this.create(x, y);
	}
}
