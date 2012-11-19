package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.GameItemCocosFactory;

public class SlimySuccessFactory extends GameItemCocosFactory<GameItemCocos> {

	@Override
	protected void createAnimList() {
		this.createAnim(SlimySuccess.Anim_Afro, 10);
		this.createAnim(SlimySuccess.Anim_Cosmonaut, 10);
		this.createAnim(SlimySuccess.Anim_Hawaiian, 10);
		this.createAnim(SlimySuccess.Anim_Sombrero, 10);
	}

	@Override
	protected String getPlistPng() {
		return "success";
	}

	@Override
	protected GameItemCocos instantiate(float x, float y, float width,
			float height) {
		return new SlimySuccess(x, y, width, height);
	}

	@Override
	protected void runFirstAnimations(GameItemCocos item) {
	}
}
