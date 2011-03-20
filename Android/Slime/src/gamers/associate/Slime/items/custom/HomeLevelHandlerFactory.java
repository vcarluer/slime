package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemFactory;

public class HomeLevelHandlerFactory extends GameItemFactory<HomeLevelHandler>{

	@Override
	protected HomeLevelHandler instantiate(float x, float y, float width,
			float height) {
		return new HomeLevelHandler(x, y, width, height);
	}
}
