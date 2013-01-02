package gamers.associate.Slime.layers;

import org.cocos2d.layers.CCScene;
import org.cocos2d.transitions.CCFadeTransition;

public class SurvivalModeItemLayer extends ModeItemLayer {
	private static final String SURVIVAL = "Survival";
	private static final String WORLD_ITEMS_BTN_EXTREME_PNG = "world-items/btn-extreme.png";
	@Override
	protected String getTitle() {
		return SURVIVAL;
	}
	@Override
	protected String getBackgroundPath() {
		return WORLD_ITEMS_BTN_EXTREME_PNG;
	}
	@Override
	protected CCScene getTransition() {
		return CCFadeTransition.transition(0.5f, ChooseSurvivalDifficultyLayer.getScene());
	}
}
