package gamers.associate.SlimeAttack.layers;

import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.WorldPackage;
import gamers.associate.SlimeAttack.game.achievements.AchievementStatistics;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import android.annotation.SuppressLint;
import android.util.FloatMath;

@SuppressLint("DefaultLocale") public class StoryModeItemLayer extends ModeItemLayer {
	private static final String STORY = "STORY";
	private static final String WORLD_ITEMS_BTN_HARD_PNG = "world-items/button-story.png";
	private static float shiftScore = - 75f;
	
	private CCLabel lblScore;
	
	public StoryModeItemLayer() {
		this.lblScore = CCLabel.makeLabel("0%".toUpperCase(), "fonts/Slime.ttf", 60.0f * SlimeFactory.SGSDensity);
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
			totalAd = (int) FloatMath.floor(totalAd / (float)size) - 1;
			// hack
			if (SlimeFactory.GameInfo.isStory1Finished()) {
				totalAd++;
			}
		}
		
		if (totalAd > 0) {
			this.lblScore.setVisible(true);
			String score = String.valueOf(totalAd) + "%";
			this.lblScore.setString(score.toUpperCase());
			this.lblScore.setColor(SlimeFactory.ColorSlimeLight);
		} else {
			this.lblScore.setVisible(false);
		}
		
		super.onEnter();
	}
	
	@Override
	protected String getTitle() {
		return STORY.toUpperCase();
	}
	@Override
	protected String getBackgroundPath() {
		return WORLD_ITEMS_BTN_HARD_PNG;
	}
	@Override
	protected CCScene getTransition() {
		AchievementStatistics.isModeStory = true;
		AchievementStatistics.isModeSurvival = false;
		return CCFadeTransition.transition(0.5f, StoryWorldLayer.getScene(1));
	}	
}
