package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.WorldPackage;
import gamers.associate.Slime.items.custom.Star;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import android.util.FloatMath;

public class StoryModeItemLayer extends ModeItemLayer {
	private static final String STORY = "Story";
	private static final String WORLD_ITEMS_BTN_HARD_PNG = "world-items/btn-hard.png";
	private static float shiftScore = - 75f;
	
	private CCLabel lblScore;
	
	public StoryModeItemLayer() {
		this.lblScore = CCLabel.makeLabel("0%".toUpperCase(), "fonts/Slime.ttf", 60.0f);
		this.lblScore.setPosition(CGPoint.make(
				this.labelX,
				this.labelY + shiftScore
				));
		this.addChild(this.lblScore);
	}
	
	@Override
	public void onEnter() {
		int totalAd = 0;
		int size = 0;
		for(WorldPackage world : SlimeFactory.PackageManager.getPackages()) {
			if (world.getLevelCount() > 0) {
				int ad = (int) FloatMath.floor((world.getUnlockLevelCount() / (float) world.getLevelCount()) * 100);
				totalAd += ad;
				size++;
			}
		}
		
		if (size > 0) {
			totalAd = (int) FloatMath.floor(totalAd / (float)size);
		}
		
		String score = String.valueOf(totalAd) + "%";
		this.lblScore.setString(score.toUpperCase());
		this.lblScore.setColor(SlimeFactory.ColorSlimeLight);
		super.onEnter();
	}
	
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
