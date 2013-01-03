package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.LevelBuilderGenerator;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;
import gamers.associate.Slime.game.Vibe;
import gamers.associate.Slime.items.custom.Star;
import gamers.associate.Slime.levels.GamePlay;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;

public class SurvivalItemLayer extends CanvasItemLayer {
	private static final String GREY = "-grey";
	private static final String BKGEXT = ".png";
	protected static final float fontSize = 32f;
	protected static final float fontSizeInfo = 20f;
	private boolean isUnlocked;
	private String title;
	private String baseBackground;
	private int levelDiff;
	private CCSprite diffSprite;
	private CCLabel inProgress;
	
	private static float iconSize = 70f;
	private static final float iconSizeReference = 87f;
	private static final float paddingSprite = - 11;
	private static final float paddingInfo = 28;
	
	private List<CCNode> toDestroy;
	
	public SurvivalItemLayer(String baseBackground, int levelDiff, float referenceHeight) {
		super(10f, 4f, true, referenceHeight);
		this.toDestroy = new ArrayList<CCNode>();		
		this.levelDiff = levelDiff;
		this.isUnlocked = this.levelDiff <= SlimeFactory.GameInfo.getMaxLevelDifficulty();
		this.title = LevelDifficulty.getText(this.levelDiff);
		this.baseBackground = baseBackground;
		
		// override label position
		this.diffSprite = getLevelSprite(this.levelDiff, this.isUnlocked);
		this.labelY -= this.diffSprite.getContentSize().height / 2f - paddingSprite;
		this.labelX = this.width / 2f + this.paddingX;
		this.label.setPosition(this.labelX, this.labelY);
		
		this.inProgress = SlimeFactory.getLabel("In progress", fontSizeInfo);
		this.inProgress.setColor(ccColor3B.ccc3(255, 0, 0));
		this.addChild(this.inProgress);
	}
	
	@Override
	public void onEnter() {
		super.onEnter();

		this.diffSprite = getLevelSprite(this.levelDiff, this.isUnlocked);				
		
		diffSprite.setPosition(this.labelX, 
				this.labelY + this.label.getContentSize().height / 2 
				+ this.diffSprite.getContentSize().height / 2 
				+ paddingSprite);
		this.addChild(this.diffSprite);
		
		CCSprite star = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);
		star.setPosition(this.labelX, this.labelY - this.getFontSize() / 2 - paddingSprite - star.getContentSize().height / 2);
		this.addChild(star);
		int score = SlimeFactory.GameInfo.getScore(this.levelDiff);
		CCLabel scoreLabel = SlimeFactory.getLabel(String.valueOf(score), this.getFontSize());
		scoreLabel.setPosition(this.labelX, star.getPosition().y - star.getContentSize().height / 2 - paddingSprite - this.getFontSize() / 2);
		this.addChild(scoreLabel);
				
		int inARow = SlimeFactory.GameInfo.getInARow(this.levelDiff);		
		String endPhrase = "";
		if (inARow < 2) {
			endPhrase = " level in a row!";			
		} else {
			endPhrase = " levels in a row!";			
		}
		
		CCLabel levelLabel = CCLabel.makeLabel(String.valueOf(inARow) + endPhrase, "fonts/Slime.ttf", fontSizeInfo);

		levelLabel.setPosition(this.labelX, scoreLabel.getPosition().y - this.getFontSize() / 2 - paddingInfo - fontSizeInfo / 2);
		this.addChild(levelLabel);
		
		this.inProgress.setPosition(this.labelX, levelLabel.getPosition().y - fontSizeInfo / 2 - paddingInfo - fontSizeInfo / 2);
		this.inProgress.setVisible(SlimeFactory.GameInfo.canContinueSurvival(this.levelDiff));
		
		this.toDestroy.add(star);
		this.toDestroy.add(this.diffSprite);
		this.toDestroy.add(scoreLabel);
		this.toDestroy.add(levelLabel);		
		
		SlimeFactory.ContextActivity.showAndNextAd();				
	}
	
//	if (isEnable) {
//		CCSprite star = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);
//		//star.setPosition(tmp2.x + this.normalMenuLabel.getContentSize().width + padding + (Star.Default_Width / 2), tmp.y);
//		star.setPosition(tmp.x - iconPadding - (iconSize / 2), tmp.y - iconSize); // Use Normal x as reference not label
//		int score = SlimeFactory.GameInfo.getScore(diffRef); 
//		CCLabel scoreLabel = CCLabel.makeLabel(String.valueOf(score).toUpperCase(), "fonts/Slime.ttf", 60.0f);
//		scoreLabel.setPosition(tmp.x, tmp.y - iconSize); // Use Normal x as reference not label
//		scoreLabel.setAnchorPoint(0, 0.5f);
//		CCLabel levelLabel = CCLabel.makeLabel("10 level in a row!", "fonts/Slime.ttf", 30.0f);
//		levelLabel.setPosition(tmp.x - iconPadding - (iconSize / 2), tmp.y - scoreLabel.getContentSize().height - iconSize); // Use Normal x as reference not label
//		levelLabel.setAnchorPoint(0, 0.5f);			
//		this.addChild(star);
//		this.addChild(scoreLabel);
//		this.addChild(levelLabel);
//		
//		this.toDestroy.add(star);
//		this.toDestroy.add(scoreLabel);
//		this.toDestroy.add(levelLabel);
//	}
	
	@Override
	public void onExit() {
		SlimeFactory.ContextActivity.hideAd();
		for(CCNode node : this.toDestroy) {
			this.removeChild(node, true);
		}
		
		this.toDestroy.clear();
		super.onExit();
	}

	private static CCSprite getLevelSprite(int diff, boolean isEnable) {
		String suf = "";
		if (!isEnable) {
			suf = "-grey";
		}
		String diffTxt = LevelDifficulty.getText(diff).toLowerCase();
		CCSprite spr = CCSprite.sprite("mode-" + diffTxt + suf + "-01.png");
		spr.setScale(iconSize / iconSizeReference);
		
		return spr;
	}

	@Override
	protected String getBackgroundPath() {
		String path = "world-items/";
		if (this.isUnlocked) {
			path += this.baseBackground;
		} else {
			path += this.baseBackground + GREY;
		}
		
		path += BKGEXT;
		return path;
	}

	@Override
	protected CCScene getTransition() {
		SlimeFactory.GameInfo.resetDifficulty(this.levelDiff);
		Level level = Level.get(LevelBuilderGenerator.defaultId, true, GamePlay.Survival);
		
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, level.getScene());
		
		Vibe.vibrate();
		Sounds.stopMusic();
		
		return transition;
	}

	@Override
	protected void defineLabelPosition() {				
		this.labelX = this.width / 2f;
		this.labelY = this.height - fontSize / 2f;
	}

	@Override
	protected String getTitle() {
		return this.title;
	}

	@Override
	protected float getFontSize() {
		return 42f;
	}
}
