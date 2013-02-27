package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttack.R;
import gamers.associate.SlimeAttack.game.achievements.AchievementStatistics;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.EvacuationPlug;
import gamers.associate.SlimeAttack.levels.GamePlay;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.util.FloatMath;


public class TimeAttackGame extends GameItem implements IGamePlay {
	private static final float ZOOM_BASE = 1.0f;
	private static final float extraBonusTime = 10f;
	private static final float TimeRatioNormal = 2.0f;
	private static final float TimeRatioLow = 0.2f;
	private static final int bonusTime = 3;
	
	private static float defaultTime = 60;
	private static float defaultCritic = 10;
	private static float adTime = 5.0f;
	private static float stepCritic = 1.0f;
	private static int timeScore = 1000;
	protected static int bonusScore = 10000;
	private float startTime;
	private float leftTime;
	private float criticTime;
	private boolean isStarted;
	private boolean isGameOver;
	protected Level level;
	private float localRender;
	private float adRender;
	private boolean isCritic;
	private CCAction criticAction;
	protected int bonusTaken;
	protected boolean adHiddenTimer;
	private float zoomRatio;
	
	public static TimeAttackGame NewGame() {
		return new TimeAttackGame(0, 0, 0, 0);				
	}
	
	public TimeAttackGame(float x, float y, float width, float height) {
		super(x, y, width, height);		
		this.zoomRatio = SlimeFactory.getWidthRatio() * ZOOM_BASE;
		this.startTime = defaultTime;
		this.criticTime = defaultCritic;
		this.reset();		
	}
	
	public void reset()	{
		this.bonusTaken = 0;
		this.leftTime = this.startTime;
		this.isGameOver = false;
		this.adHiddenTimer = false;
		this.localRender = 0;	
		this.adRender = 0;
		
		if (this.level != null) {
			this.level.setHideCount(false);
			this.level.hideHudText();
			CCBitmapFontAtlas label = this.level.getHudLabel();
			label.setColor(SlimeFactory.ColorSlime);
			label.stopAllActions();
			label.setOpacity(255);
			this.setStartTime();
		}
		
		if (Level.currentLevel != null && Level.currentLevel.getGoal() != null) {
			Level.currentLevel.getGoal().setActive(this.neededBonus() == 0);
		}
	}
		
	public boolean getGameOver() {
		return this.isGameOver;
	}
	
	public float getLeftTime() {
		return this.leftTime;
	}				

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItem#render(float)
	 */
	@Override
	public void render(float delta) {		
		super.render(delta);
		
		float deltaReal = delta  / this.level.getTimeRatio();
		
		if (!this.adHiddenTimer) {
			if (this.adRender > adTime) {
				SlimeFactory.ContextActivity.hideAd();
				this.adHiddenTimer = true;
			} else {
				this.adRender += deltaReal;
			}
		}		

		if (this.isStarted && !this.isGameOver) {			
			this.leftTime -= deltaReal;
			this.localRender += deltaReal;
			
			if (this.leftTime <= 0) {
				this.leftTime = 0;
				AchievementStatistics.leftTime = this.leftTime;
				this.level.killAllSlimies();
				this.level.timesUp();
			}
			
			AchievementStatistics.leftTime = this.leftTime;
			
			if (!this.isGameOver) {				
				
				if (!this.isCritic) {
					if (this.leftTime <= this.criticTime) {						
						this.isCritic = true;
						this.localRender = stepCritic;
						AchievementStatistics.enterCriticZone = true;
					}
				}
				else {
					if (this.leftTime > this.criticTime) {
						AchievementStatistics.exitCriticZone = true;
						this.isCritic = false;
						CCBitmapFontAtlas label = this.level.getHudLabel();
						label.setColor(SlimeFactory.ColorSlime);
						label.stopAllActions();
						label.setOpacity(255);
					}
				}
				
				if (this.isCritic) {
					CCBitmapFontAtlas label = this.level.getHudLabel();
					if (this.localRender >= stepCritic) {						
						float fadeInTime = (stepCritic / 2) - (this.localRender - stepCritic);
						float fadeOutTime = stepCritic / 2;
						CCFadeIn in = CCFadeIn.action(fadeInTime);
						CCFadeOut out = CCFadeOut.action(fadeOutTime);
						CCSequence seq = CCSequence.actions(in, out);
//						this.criticAction = CCRepeatForever.action(seq);
						this.criticAction = seq;
						label.runAction(this.criticAction);						
												
						this.localRender = 0;
						
						Sounds.playEffect(R.raw.tick);
					}	
					
					label.setColor(ccColor3B.ccc3( 255,0,0));
					this.level.setHudText(Util.getFormatTimeCritic(this.leftTime));
				}
				else {
					this.setNormalTime();					
				}														
			}
		}
	}
	
	private int lastNormalTime;
	private boolean isModeStarted;
	
	private void setNormalTime() {
		int normalTime = (int)FloatMath.ceil(this.leftTime);
		if (normalTime != lastNormalTime) {
			this.level.setHudText(Util.getFormatTime(normalTime));
			this.lastNormalTime = normalTime;
		}		
	}
	
	private void setStartTime() {
		int normalTime = (int)FloatMath.ceil(this.leftTime);
		this.level.setStartText(Util.getFormatTime(normalTime));
		this.lastNormalTime = normalTime;
		this.localRender = 0;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void activateSelection(CGPoint gameReference) {
		if (!this.isPaused && !this.isGameOver) {
			this.setIsStarted(true);			
//			this.level.addItemToRemove(this.level.getHelpItem());
			this.setNormalTime();
			this.level.getCameraManager().follow(this.level.getSelectedGameItem());	
			this.level.setTimeRatio(TimeRatioNormal);
			// this.level.desactivateCameraMoveAndZoomByUser();						
		}
	}

	private void setIsStarted(boolean value) {
		this.isStarted = value;		
		Level.currentLevel.getPauseLayer().gameStarted(this.isStarted);
	}

	@Override
	public void setPause(boolean value) {
		if (value == true) {
			this.hasPaused = true;
		}
		super.setPause(value);
	}

	public int getScore() {		
		// return (int) Math.round(this.leftTime);
		return this.getBaseScore() + this.bonusTaken * this.getBonusScore();
	}
	
	public int getBaseScore() {
		return (int)FloatMath.ceil(this.leftTime * timeScore);
	}
	
	public int getBonusScore() {
		return bonusScore;
	}
	
	public int getBonusScore(int bonusIdx) {
		return getBonusScore();
	}

	public void selectBegin(CGPoint gameReference) {
		if (!this.isStarted && !this.isPaused && !this.isGameOver) {
//			this.level.desactivateCameraMoveAndZoomByUser();
			this.level.getCameraManager().cancelActions();			
			this.level.getCameraManager().moveInterpolateTo(this.level.getSelectedGameItem(), 0.5f);
			//AMZ replacing 1.0f by SGSDensity 
			if(!this.hasPaused) {
				this.level.getCameraManager().zoomInterpolateTo(this.level.getSelectedGameItem(), this.zoomRatio, 0.5f);
			}
						
			//this.level.getCameraManager().follow(this.level.getSelectedGameItem());			
		}
		
		if (!this.adHiddenTimer) {
			SlimeFactory.ContextActivity.hideAd();
			this.adHiddenTimer = true;
		}		
		
		// todo: Slow down animation? http://cocos2d-central.com/topic/1049-changing-speed-of-an-action-after-its-started/
		this.level.setTimeRatio(TimeRatioLow);
	}

	public void simpleSelect() {
		/*if (!this.isPaused) {
			this.level.getCameraManager().moveInterpolateTo(this.level.getSelectedGameItem(), 0.5f);
			this.level.setTimeRatio(TimeRatioNormal);
		}*/
	}

	public void startLevel() {
		this.reset();
		this.startMode();
//		this.level.getCameraManager().zoomCameraCenterTo(0);
		this.level.getCameraManager().cancelActions();
		this.hasPaused = false;
		this.level.getCameraManager().zoomCameraTo(this.zoomRatio);
		this.level.getCameraManager().centerCameraOn(this.level.getGoal().getPosition());
		this.level.pause();
		this.enterGameMode(15.0f);		
	}
	
	private boolean hasPaused;
	
	private void enterGameMode(float speed) {
		if(this.level.getStartItem() != null) {			
			this.level.getCameraManager().moveInterpolateTo(this.level.getStartItem(), speed);
//			//AMZ replacing 1.0f by SGSDensity  
//			if (!this.hasPaused) {
//				this.level.getCameraManager().zoomInterpolateTo(this.level.getStartItem(), this.zoomRatio, speed);
//			}
					
//			this.level.getCameraManager().follow(this.level.getStartItem());			
		}		
		
		this.level.setTimeRatio(TimeRatioNormal);
	}
	
	public void setStartTime(int startTime) {
		this.startTime = (float) startTime;
		AchievementStatistics.startTime = this.startTime;
		AchievementStatistics.leftTime = this.startTime;
	}
	
	public void setStartTime(float startTime) {
		this.startTime = startTime;
		AchievementStatistics.startTime = this.startTime;
		AchievementStatistics.leftTime = this.startTime;
	}
	
	public float getStartTime() {
		return this.startTime;
	}
	
	public void setCriticTime(int criticTime) {
		this.criticTime = (float) criticTime;
	}
	
	public void setCriticTime(float criticTime) {
		this.criticTime = criticTime;
	}
	
	public float getCriticTime() {
		return this.criticTime;
	}

	public void stop() {
		CCBitmapFontAtlas label = this.level.getHudLabel();
		label.setColor(SlimeFactory.ColorSlime);
		label.stopAllActions();
		label.setOpacity(255);
		this.level.setTimeRatio(TimeRatioNormal);
		this.setNormalTime();
		this.setIsStarted(false);
		
		SlimeFactory.ContextActivity.showAndNextAd();
	}

	public boolean isGameOver() {
		return this.isGameOver;
	}

	public void setNewAliveSlimyCount(int count) {
		if (count == 0) {
			this.lose();
		}
	}
	
	protected void lose() {		
		this.isGameOver = true;
		this.endMode();
		SlimeFactory.GameInfo.setSurvivalGameOver(true);
		if (this.level.lose()) {			
			this.level.getHudLabel().stopAction(this.criticAction);
		}
	}

	public void setNewBonusTime() {
		if (!this.isGameOver) {
			this.leftTime += bonusTime;
		}
	}
	
	public void setNewBonus() {
		if (!this.isGameOver) {
			this.leftTime += bonusTime;
			this.bonusTaken++;
			AchievementStatistics.bonusTaken = this.bonusTaken;
			
			if (this.bonusTaken >= this.neededBonus()) {
				if (Level.currentLevel != null && Level.currentLevel.getGoal() != null) {
					Level.currentLevel.getGoal().setActiveNextStep();
					EvacuationPlug plug = Level.currentLevel.getPlug();
					if (plug != null) {
						plug.removeNextStep();
					}
				}
			}
			
			if (this.bonusTaken == SlimeFactory.LevelBuilder.getTotalStar()) {
				this.leftTime += extraBonusTime;
			}
		}
	}
	
	public int neededBonus() {
		int needed = 0;
		if (!SlimeFactory.LevelBuilder.isBoss()) {
			switch (SlimeFactory.GameInfo.getDifficulty()) {
				default:
				case LevelDifficulty.Easy: needed = (int) FloatMath.ceil(SlimeFactory.LevelBuilder.getTotalStar() / 4f); break;
				case LevelDifficulty.Normal: needed = (int) FloatMath.ceil(SlimeFactory.LevelBuilder.getTotalStar() / 2f); break;
				case LevelDifficulty.Hard: needed = (int) FloatMath.ceil(SlimeFactory.LevelBuilder.getTotalStar() * 3f / 4f); break;
				case LevelDifficulty.Extrem: needed = SlimeFactory.LevelBuilder.getTotalStar(); break;
			}
		}		
		
		return needed;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItem#pause()
	 */
	@Override
	protected void pause() {
		super.pause();		
		this.level.activateCameraMoveAndZoomByUser();
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItem#resume()
	 */
	@Override
	protected void resume() {
		super.resume();
		this.level.desactivateCameraMoveAndZoomByUser();
		this.enterGameMode(0.3f);
	}

	@Override
	public boolean isStarted() {
		return this.isStarted;
	}

	@Override
	public int bonusCount() {
		return this.bonusTaken;
	}
	
	public float getNormalTimeRatio() {
		return TimeRatioNormal;
	}

	@Override
	public float getNormalBonusPoints() {
		return bonusTime;
	}

	@Override
	public float getExtraBonusPoints() {
		return extraBonusTime;
	}

	@Override
	public GamePlay getType() {
		return GamePlay.TimeAttack;
	}
	
	private void startMode() {
		if (!this.isModeStarted) {
//			this.level.activateCameraZoomByUser();
			this.startModeInternal();
			this.isModeStarted = true;
			AchievementStatistics.initAll();
		}
	}
	
	public void endMode() {
		this.isModeStarted = false;		
		this.endModeInternal();
	}

	protected void endModeInternal() {
	}

	protected void startModeInternal() {
	}

	public static Rank getRank(int bonusCount, int totalStar) {
		int rank = 0;
		if (totalStar == 0 || bonusCount == totalStar) {
			rank = 4;
		} else {
			rank = (int) FloatMath.ceil(3f * (float)bonusCount / (float)totalStar);			
		}		

		switch(rank) {
			default:
			case 1:
				return Rank.None;
			case 2:
				return Rank.Bronze;
			case 3:
				return Rank.Silver;
			case 4:
				return Rank.Gold;
		}
	}
}