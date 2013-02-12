package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemFactory;

public class DirectorFactory extends GameItemFactory<Director> {
	private String target;
	private SpriteAction action;
	
	@Override
	protected Director instantiate(float x, float y, float width, float height) {
		return new Director(x, y, width, height, this.target, this.action);
	}
	
	public Director create(String name, String target, SpriteAction action) {
		this.target = target;
		this.action = action;
		return this.create(name);
	}
}
