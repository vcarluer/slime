package gamers.associate.Slime.game;

import gamers.associate.Slime.R;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.EvacuationPlug;

import java.text.DecimalFormat;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;


public class TimeAttackGame extends GameItem implements IGamePlay {
	private static final float extraBonusTime = 10f;
	private static final float TimeRatioNormal = 2.0f;
	private static final float TimeRatioLow = 0.2f;
	private static final int bonusTime = 3;
	private static float defaultTime = 60;
	private static float defaultCritic = 10;
	private static float adTime = 5.0f; 
	private static float stepNormal = 1.0f;
	private static float stepCritic = 1.0f;
	private static int timeScore = 1000;
	private static int bonusScore = 10000;
	private float startTime;
	private float leftTime;
	private float criticTime;
	private boolean isStarted;
	private boolean isGameOver;
	private Level level;
	private float localRender;
	private float adRender;
	private boolean isCritic;
	private CCAction criticAction;
	private int bonusTaken;
	private boolean adHiddenTimer;
	
	
	public static TimeAttackGame NewGame() {
		return new TimeAttackGame(0, 0, 0, 0);				
	}
	
	public TimeAttackGame(float x, float y, float width, float height) {
		super(x, y, width, height);		
		
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
	
	public String getLeftTime() {
		return Util.getFormatTime(this.leftTime);
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
				this.lose();
			}			
			
			if (!this.isGameOver) {				
				
				if (!this.isCritic) {
					if (this.leftTime <= this.criticTime) {						
						this.isCritic = true;
						this.localRender = stepCritic;
					}
				}
				else {
					if (this.leftTime > this.criticTime) {						
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
	
	private void setNormalTime() {
		this.level.setHudText(Util.getFormatTime(this.leftTime));
	}
	
	private void setStartTime() {
		this.level.setStartText(Util.getFormatTime(this.leftTime));
		this.localRender = 0;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void activateSelection(CGPoint gameReference) {
		if (!this.isPaused && !this.isGameOver) {
			this.isStarted = true;			
			this.level.addItemToRemove(this.level.getHelpItem());
			this.setNormalTime();
			this.level.getCameraManager().follow(this.level.getSelectedGameItem());	
			this.level.setTimeRatio(TimeRatioNormal);
			// this.level.desactivateCameraMoveAndZoomByUser();						
		}
	}

	@Override
	public void setPause(boolean value) {		
		super.setPause(value);
	}

	public int getScore() {		
		// return (int) Math.round(this.leftTime);
		return (int) Math.ceil((double)this.leftTime * timeScore) + this.bonusTaken * bonusScore;
	}
	
	public int getBaseScore() {
		return (int)this.leftTime * timeScore;
	}
	
	public int getBonusScore() {
		return bonusScore;
	}

	public void selectBegin(CGPoint gameReference) {		
		if (!this.isStarted && !this.isPaused && !this.isGameOver) {
			this.level.getCameraManager().cancelActions();
			this.level.getCameraManager().moveInterpolateTo(this.level.getSelectedGameItem(), 0.5f);
			//AMZ replacing 1.0f by SGSDensity 
			this.level.getCameraManager().zoomInterpolateTo(this.level.getSelectedGameItem(), SlimeFactory.SGSDensity, 0.5f);			
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
		this.level.getCameraManager().zoomCameraCenterTo(0);
		this.level.getCameraManager().cancelActions();
		this.enterGameMode(15.0f);		
	}
	
	private void enterGameMode(float speed) {
		if(this.level.getStartItem() != null) {				
			this.level.getCameraManager().moveInterpolateTo(this.level.getStartItem(), speed);
			//AMZ replacing 1.0f by SGSDensity  
			this.level.getCameraManager().zoomInterpolateTo(this.level.getStartItem(), SlimeFactory.SGSDensity, speed);		
			this.level.getCameraManager().follow(this.level.getStartItem());			
		}

		this.level.desactivateCameraMoveAndZoomByUser();
		this.level.setTimeRatio(TimeRatioNormal);
//		this.level.activateCameraMoveAndZoomByUser();
	}
	
	public void setStartTime(int startTime) {
		this.startTime = (float) startTime;
	}
	
	public void setStartTime(float startTime) {
		this.startTime = startTime;
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
		this.isStarted = false;
		
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
	
	private void lose() {		
		this.isGameOver = true;
		if (this.level.lose()) {						
			this.level.getHudLabel().stopAction(this.criticAction);
		}			
	}

	public void setNewBonus() {
		if (!this.isGameOver) {
			this.leftTime += bonusTime;
			this.bonusTaken++;
			
			if (this.bonusTaken >= this.neededBonus()) {
				if (Level.currentLevel != null && Level.currentLevel.getGoal() != null) {
					Level.currentLevel.getGoal().setActive(true);
					EvacuationPlug plug = Level.currentLevel.getPlug();
					if (plug != null) {
						plug.remove();
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
				case LevelDifficulty.Easy: needed = (int) Math.ceil(SlimeFactory.LevelBuilder.getTotalStar() / 4f); break;
				case LevelDifficulty.Normal: needed = (int) Math.ceil(SlimeFactory.LevelBuilder.getTotalStar() / 2f); break;
				case LevelDifficulty.Hard: needed = (int) Math.ceil(SlimeFactory.LevelBuilder.getTotalStar() * 3f / 4f); break;
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
		this.enterGameMode(0.5f);
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
}