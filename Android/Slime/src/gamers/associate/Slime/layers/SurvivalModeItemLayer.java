package gamers.associate.Slime.layers;

import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.Star;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

public class SurvivalModeItemLayer extends ModeItemLayer {
	private static final String SURVIVAL = "Survival";
	private static final String WORLD_ITEMS_BTN_EXTREME_PNG = "world-items/btn-extreme.png";
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
				this.lblScore.getPosition().x,
				this.lblScore.getPosition().y
				));
		this.addChild(this.starSprite);
	}
	@Override
	public void onEnter() {
		String score = String.valueOf(SlimeFactory.GameInfo.getTotalScore());
		this.lblScore.setString(score.toUpperCase());
		this.lblScore.setColor(SlimeFactory.ColorSlimeLight);		
		
		float starPadding = -10f;
		float starX = this.lblScore.getPosition().x - this.lblScore.getContentSize().width / 2 - SlimeFactory.Star.getStarReferenceWidth() / 2 + starPadding;
		this.starSprite.setPosition(CGPoint.make(
				starX,
				this.starSprite.getPosition().y
				));
		
		this.starSprite.runAction(SlimeFactory.Star.getAnimation(Star.Anim_Wait));
		super.onEnter();
	}
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
