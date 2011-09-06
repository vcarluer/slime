package gamers.associate.Slime.game;

import java.text.DecimalFormat;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import gamers.associate.Slime.R;
import gamers.associate.Slime.items.base.GameItem;


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

	@Override
	public void setLevel(Level level) {
		this.level = level;
	}

	@Override
	public void activateSelection(CGPoint gameReference) {
		this.isStarted = true;
		this.setNormalTime();
		this.level.getCameraManager().follow(this.level.getSelectedGameItem());		
		this.level.desactivateCameraMoveAndZoomByUser();
	}

	@Override
	public int getScore() {		
		// return (int) Math.round(this.leftTime);
		return (int) Math.ceil((double)this.leftTime);
	}

	@Override
	public void selectBegin(CGPoint gameReference) {		
		this.level.getCameraManager().moveInterpolateTo(this.level.getSelectedGameItem(), 0.5f);
		this.level.getCameraManager().zoomInterpolateTo(this.level.getSelectedGameItem(), 1.0f, 0.5f);			
		//this.level.getCameraManager().follow(this.level.getSelectedGameItem());		
	}

	@Override
	public void simpleSelect() {
		this.level.getCameraManager().moveInterpolateTo(this.level.getSelectedGameItem(), 0.5f);
	}

	@Override
	public void startLevel() {
		if(this.level.getStartItem() != null) {						
			this.level.getCameraManager().centerCameraOn(this.level.getStartItem().getPosition());
			this.level.getCameraManager().zoomInterpolateTo(this.level.getStartItem(), 1.0f, 1.0f);			
			this.level.getCameraManager().follow(this.level.getStartItem());			
		}
		
		this.reset();
		
		this.level.activateCameraMoveAndZoomByUser();
	}
	
	public void setStartTime(int startTime) {
		this.startTime = (float) startTime;
	}
	
	public void setCriticTime(int criticTime) {
		this.criticTime = (float) criticTime;
	}

	@Override
	public void stop() {
		CCLabel label = this.level.getHudLabel();
		label.setColor(ccColor3B.ccc3( 255,255,255));
		label.stopAllActions();
		label.setOpacity(255);
		this.setNormalTime();
		this.isStarted = false;
	}

	@Override
	public boolean isGameOver() {
		return this.isGameOver;
	}

	@Override
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
}