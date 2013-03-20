package gamers.associate.SlimeAttack.layers;

import android.annotation.SuppressLint;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.achievements.AchievementStatistics;
import gamers.associate.SlimeAttack.items.custom.Star;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

@SuppressLint("DefaultLocale") public class SurvivalModeItemLayer extends ModeItemLayer {
	private static final String SURVIVAL = "SURVIVAL";
	private static final String WORLD_ITEMS_BTN_EXTREME_PNG = "world-items/button-survival.png";
	private CCLabel lblScore;
	private CCSprite starSprite;
	private static float shiftScore = - 75f; // shiftInfo - 70f;	
	
	public SurvivalModeItemLayer() {
		this.lblScore = CCLabel.makeLabel("0".toUpperCase(), "fonts/Slime.ttf", 60.0f);
		this.lblScore.setPosition(CGPoint.make(
				this.labelX,
				this.labelY + shiftScore
				));
		this.addChild(this.lblScore);
				
		this.starSprite = CCSprite.sprite("star-01.png", true); // SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);		
		this.starSprite.setPosition(CGPoint.make(
				this.lblScore.getPositionRef().x,
				this.lblScore.getPositionRef().y
				));
		this.addChild(this.starSprite);
	}
	@Override
	public void onEnter() {
		int sc = SlimeFactory.GameInfo.getTotalScore();
		if (sc > 0) {
			this.lblScore.setVisible(true);
			this.starSprite.setVisible(true);
			String score = String.valueOf(sc);
			this.lblScore.setString(score.toUpperCase());
			this.lblScore.setColor(SlimeFactory.ColorSlimeLight);		
			
			float starPadding = -10f;
			float starX = this.lblScore.getPositionRef().x - this.lblScore.getContentSize().width / 2 - SlimeFactory.Star.getStarReferenceWidth() / 2 + starPadding;
			this.starSprite.setPosition(CGPoint.make(
					starX,
					this.starSprite.getPositionRef().y
					));
			
			this.starSprite.runAction(SlimeFactory.Star.getAnimation(Star.Anim_Wait));
		} else {
			this.lblScore.setVisible(false);
			this.starSprite.setVisible(false);
		}
		
		super.onEnter();
	}
	@Override
	protected String getTitle() {
		return SURVIVAL.toUpperCase();
	}
	@Override
	protected String getBackgroundPath() {
		return WORLD_ITEMS_BTN_EXTREME_PNG;
	}
	@Override
	protected CCScene getTransition() {
		AchievementStatistics.isModeSurvival = true;
		AchievementStatistics.isModeStory = false;
		return CCFadeTransition.transition(0.5f, ChooseSurvivalDifficultyLayer.getScene());
	}	
}
