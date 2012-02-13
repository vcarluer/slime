package gamers.associate.Slime.game;

import gamers.associate.Slime.R;
import gamers.associate.Slime.items.base.GameItem;

import java.text.DecimalFormat;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;


public class TimeAttackGame extends GameItem implements IGamePlay {
	private static float defaultTime = 60;
	private static float defaultCritic = 10;
	private static float stepNormal = 1.0f;
	private static float stepCritic = 1.0f;
	private float startTime;
	private float leftTime;
	private float criticTime;
	private boolean isStarted;
	private boolean isGameOver;
	private Level level;
	private float localRender;
	private boolean isCritic;
	private CCAction criticAction;
	
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
		if (this.level != null) {
			this.level.hideHudText();
			CCLabel label = this.level.getHudLabel();
			label.setColor(ccColor3B.ccc3( 255,255,255));
			label.stopAllActions();
			label.setOpacity(255);
			this.setStartTime();
		}
		
		this.leftTime = this.startTime;
		this.isGameOver = false;
		this.localRender = 0;
	}
		
	public boolean getGameOver() {
		return this.isGameOver;
	}
	
	public String getLeftTime() {
		return getFormatTime(this.leftTime);
	}
	
	private static String getFormatTime(float millis) {
		// float base = Math.round(millis);
		double base = Math.ceil((double)millis);
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 0 ) ; //arrondi à 2 chiffres apres la virgules 
		df.setMinimumFractionDigits ( 0 ) ; 
		df.setDecimalSeparatorAlwaysShown ( false ) ; 
		return df.format(base);		
	}
	
	private static String getFormatTimeCritic(float millis) {
		// float base = Math.round(millis);
		double base = Math.ceil((double)millis);
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 0 ) ; //arrondi à 2 chiffres apres la virgules 
		df.setMinimumFractionDigits ( 0 ) ; 
		df.setDecimalSeparatorAlwaysShown ( false ) ; 
		return df.format(base);		
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItem#render(float)
	 */
	@Override
	public void render(float delta) {		
		super.render(delta);
		
		float deltaReal = delta  / this.level.getTimeRatio();
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
					}
				}
				else {
					if (this.leftTime > this.criticTime) {						
						this.isCritic = false;
						CCLabel label = this.level.getHudLabel();
						label.setColor(ccColor3B.ccc3( 255,255,255));
						label.stopAllActions();
						label.setOpacity(255);
					}
				}
				
				if (this.isCritic) {
					if (this.localRender >= stepCritic) {
						CCLabel label = this.level.getHudLabel();
						float fadeInTime = (stepCritic / 2) - (this.localRender - stepCritic);
						float fadeOutTime = stepCritic / 2;
						CCFadeIn in = CCFadeIn.action(fadeInTime);
						CCFadeOut out = CCFadeOut.action(fadeOutTime);
						CCSequence seq = CCSequence.actions(in, out);
						this.criticAction = CCRepeatForever.action(seq);
						label.runAction(this.criticAction);
						label.setColor(ccColor3B.ccc3( 255,0,0));
						
						this.level.setHudText(getFormatTimeCritic(this.leftTime));
						this.localRender = 0;
						
						Sounds.playEffect(R.raw.tick);
					}						
				}
				else {
					if (this.localRender >= stepNormal) {						
						this.setNormalTime();
					}						
				}														
			}
		}
	}
	
	private void setNormalTime() {
		this.level.setHudText(getFormatTime(this.leftTime));
		this.localRender = 0;
	}
	
	private void setStartTime() {
		this.level.setStartText(getFormatTime(this.leftTime));
		this.localRender = 0;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void activateSelection(CGPoint gameReference) {
		if (!this.isPaused && !this.isGameOver) {
			this.isStarted = true;
			this.setNormalTime();
			this.level.getCameraManager().follow(this.level.getSelectedGameItem());	
			this.level.setTimeRatio(2.0f);
			// this.level.desactivateCameraMoveAndZoomByUser();
		}
	}

	public int getScore() {		
		// return (int) Math.round(this.leftTime);
		return (int) Math.ceil((double)this.leftTime);
	}

	public void selectBegin(CGPoint gameReference) {		
		if (!this.isStarted && !this.isPaused && !this.isGameOver) {
			this.level.getCameraManager().cancelActions();
			this.level.getCameraManager().moveInterpolateTo(this.level.getSelectedGameItem(), 0.5f);
			this.level.getCameraManager().zoomInterpolateTo(this.level.getSelectedGameItem(), 1.0f, 0.5f);			
			//this.level.getCameraManager().follow(this.level.getSelectedGameItem());			
		}
		
		this.level.setTimeRatio(0.2f);
	}

	public void simpleSelect() {
		/*if (!this.isPaused) {
			this.level.getCameraManager().moveInterpolateTo(this.level.getSelectedGameItem(), 0.5f);
			this.level.setTimeRatio(2.0f);
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
			this.level.getCameraManager().zoomInterpolateTo(this.level.getStartItem(), 1.0f, speed);		
			this.level.getCameraManager().follow(this.level.getStartItem());			
		}

		this.level.desactivateCameraMoveAndZoomByUser();
		this.level.setTimeRatio(2.0f);
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
		CCLabel label = this.level.getHudLabel();
		label.setColor(ccColor3B.ccc3( 255,255,255));
		label.stopAllActions();
		label.setOpacity(255);
		this.level.setTimeRatio(2.0f);
		this.setNormalTime();
		this.isStarted = false;
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
			this.leftTime += 10;
		}
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
}