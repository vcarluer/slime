package gamers.associate.Slime.layers;

import org.cocos2d.layers.CCScene;
import org.cocos2d.transitions.CCFadeTransition;

public class StoryModeItemLayer extends ModeItemLayer {
	
	private static final String STORY = "Story";
	private static final String WORLD_ITEMS_BTN_HARD_PNG = "world-items/btn-hard.png";
	@Override
	protected String getTitle() {
		return STORY;
	}
	@Override
	protected String getBackgroundPath() {
		return WORLD_ITEMS_BTN_HARD_PNG;
	}
	@Override
	protected CCScene getTransition() {
		return CCFadeTransition.transition(0.5f, StoryWorldLayer.getScene(1));
	}

}
