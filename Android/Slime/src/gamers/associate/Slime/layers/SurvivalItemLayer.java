package gamers.associate.Slime.layers;

import android.annotation.SuppressLint;
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

@SuppressLint("DefaultLocale") public class SurvivalItemLayer extends CanvasItemLayer {	
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
	private CCLabel inProgressLevel;
	
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
		this.inProgress.setVisible(false);
		this.addChild(this.inProgress);
		
		this.inProgressLevel = SlimeFactory.getLabel("Level x", fontSizeInfo);
		this.inProgressLevel.setColor(ccColor3B.ccc3(255, 0, 0));
		this.inProgressLevel.setVisible(false);
		this.addChild(this.inProgressLevel);
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
		this.toDestroy.add(this.diffSprite);
		
		int score = SlimeFactory.GameInfo.getScore(this.levelDiff);
		if (score > 0) {
			CCSprite star = SlimeFactory.Star.getAnimatedSprite(Star.Anim_Wait);
			float starY = this.labelY - this.getFontSize() / 2 - paddingSprite - star.getContentSize().height / 2;
			if (starY > this.height * (2f / 3f)) {
				starY = this.height * (2f / 3f);
			}
			
			star.setPosition(this.labelX, starY);
			this.addChild(star);
			this.toDestroy.add(star);
			
			CCLabel scoreLabel = SlimeFactory.getLabel(String.valueOf(score), this.getFontSize());
			scoreLabel.setPosition(this.labelX, star.getPosition().y - star.getContentSize().height / 2 - paddingSprite - this.getFontSize() / 2);
			this.addChild(scoreLabel);
			this.toDestroy.add(scoreLabel);
			
			int inARow = SlimeFactory.GameInfo.getInARow(this.levelDiff);	
			if (inARow > 0) {
				String endPhrase = "";
				if (inARow < 2) {
					endPhrase = " level in a row!";			
				} else {
					endPhrase = " levels in a row!";			
				}
				
				CCLabel levelLabel = CCLabel.makeLabel(String.valueOf(inARow) + endPhrase, "fonts/Slime.ttf", fontSizeInfo);

				levelLabel.setPosition(this.labelX, scoreLabel.getPosition().y - this.getFontSize() / 2 - paddingInfo - fontSizeInfo / 2);
				this.addChild(levelLabel);
				this.toDestroy.add(levelLabel);
				
				boolean canContinue = SlimeFactory.GameInfo.canContinueSurvival(this.levelDiff);
				if (canContinue) {
					this.inProgress.setPosition(this.labelX, levelLabel.getPosition().y - fontSizeInfo / 2 - paddingInfo - fontSizeInfo / 2);
					String currentLevel = "LEVEL " + String.valueOf(SlimeFactory.GameInfo.getCurrentInARow(this.levelDiff));
					this.inProgressLevel.setString(currentLevel);
					this.inProgressLevel.setPosition(this.labelX, this.inProgress.getPosition().y - fontSizeInfo / 2 - paddingInfo - fontSizeInfo / 2);
				}
				
				this.inProgress.setVisible(canContinue);
				this.inProgressLevel.setVisible(canContinue);
				
			}
		}
		
		SlimeFactory.ContextActivity.showAndNextAd();				
	}
	
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
		if (!SlimeFactory.GameInfo.canContinueSurvival(this.levelDiff)) {
			SlimeFactory.GameInfo.resetDifficulty(this.levelDiff);
		} else {
			SlimeFactory.GameInfo.setDifficulty(this.levelDiff);
			SlimeFactory.GameInfo.setLevel(SlimeFactory.GameInfo.getCurrentInARow(this.levelDiff) + 1);
		}
		 
		Level level = Level.get(LevelBuilderGenerator.defaultId, true, GamePlay.Survival);
		
		CCFadeTransition transition = CCFadeTransition.transition(0.5f, level.getScene());
		
		Vibe.vibrate();
		Sounds.stopMusic();
		SlimeFactory.GameInfo.setCurrentSurvival(this);

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
	
	@Override
	public void select() {
		if (this.isUnlocked) {
			super.select();
		}		
	}
}
