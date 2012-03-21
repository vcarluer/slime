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
	
	public StarCounter create(float x, float y, float width, float height, StarCounterType counterType) {
		this.currentType = counterType;
		return this.create(x, y, width, height);
	}
	
	public StarCounter create(float x, float y, StarCounterType counterType) {
		this.currentType = counterType;
		return this.create(x, y);
	}
	
	public StarCounterType getFromIndicators(int current, int min, int max) {	
		// green
		if (current < min) {
			if (current == max) {
				// Should never happen
				return StarCounterType.End_Green;
			}
			
			if (current == 1) {
				return StarCounterType.Start_Green;
			}
			
			return StarCounterType.Cell_Green;
		}
		
		// Target
		if (current == min) {
			if (current == max) {
				return StarCounterType.End_Target;
			}
			
			return StarCounterType.Cell_Target;
		}
		
		// Gold
		if (current > min) {
			if (current == max) {
				return StarCounterType.End_Gold;
			}
			
			if (current == 1) {
				return StarCounterType.Start_Gold;
			}
			
			return StarCounterType.Cell_Gold;
		}
		
		return StarCounterType.Cell_Green;
	}
	
	public StarCounter create(float x, float y, float width, float height, int current, int min, int max) {
		StarCounterType cType = this.getFromIndicators(current, min, max);
		return this.create(x, y, width, height, cType);
	}
	
	public StarCounter create(float x, float y, int current, int min, int max) {
		StarCounterType cType = this.getFromIndicators(current, min, max);
		return this.create(x, y, cType);
	}
}
