package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemFactory;
import gamers.associate.Slime.items.base.Target;

public class TargetFactory extends GameItemFactory<Target> {

	@Override
	protected Target instantiate(float x, float y, float width, float height) {
		return new Target(x, y, width, height);
	}
	
	public Target create(float x, float y, float width, float height, String name) {
		Target target = super.create(x, y, width, height);
		target.setName(name);
		return target;
	}
	
	public Target createBL(float x, float y, float width, float height, String name) {
		return this.create(x + width / 2, y + height / 2, width, height, name);
	}
}
