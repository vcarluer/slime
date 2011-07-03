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
	private float LocalRender;
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
		this.leftTime = this.startTime;
	}
		
	public boolean getGameOver() {
		return this.isGameOver;
	}
	
	public String getLeftTime() {
		return getFormatTime(this.leftTime);
	}
	
	private static String getFormatTime(float millis) {		
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 0 ) ; //arrondi à 2 chiffres apres la virgules 
		df.setMinimumFractionDigits ( 0 ) ; 
		df.setDecimalSeparatorAlwaysShown ( false ) ; 
		return df.format(millis);		
	}
	
	private static String getFormatTimeCritic(float millis) {		
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 0 ) ; //arrondi à 2 chiffres apres la virgules 
		df.setMinimumFractionDigits ( 0 ) ; 
		df.setDecimalSeparatorAlwaysShown ( false ) ; 
		return df.format(millis);		
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
			this.LocalRender += deltaReal;
			if (this.leftTime <= 0) {
				this.leftTime = 0;
				this.isGameOver = true;
				this.level.gameOver();
				this.level.setHudText("Game Over");				
				this.level.getHudLabel().stopAction(this.criticAction);
				this.criticAction = CCFadeIn.action(0.5f);
				this.level.getHudLabel().runAction(this.criticAction);
			}			
			
			if (!this.isGameOver) {								
				if (!this.isCritic) {
					if (this.leftTime <= this.criticTime) {
						CCLabel label = this.level.getHudLabel();
						// label.runAction(CCBlink.action(this.criticTime, 1));
						CCFadeIn in = CCFadeIn.action(0.5f);
						CCFadeOut out = CCFadeOut.action(0.5f);
						CCSequence seq = CCSequence.actions(in, out);
						this.criticAction = CCRepeatForever.action(seq);
						label.runAction(this.criticAction);
						label.setColor(ccColor3B.ccc3( 255,0,0));
						this.isCritic = true;
					}
				}
					
				if (this.isCritic) {
					if (this.LocalRender >= stepCritic) {						
						this.level.setHudText(getFormatTimeCritic(this.leftTime));
						this.LocalRender = 0;
					}						
				}
				else {
					if (this.LocalRender >= stepNormal) {						
						this.setNormalTime();
					}						
				}												
			}
		}
	}
	
	private void setNormalTime() {
		this.level.setHudText(getFormatTime(this.leftTime));
		this.LocalRender = 0;
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
	}

	@Override
	public int getScore() {		
		return (int) this.leftTime;
	}

	@Override
	public void selectBegin(CGPoint gameReference) {		
		this.level.getCameraManager().moveInterpolateTo(this.level.getSelectedGameItem(), 0.5f);
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
			this.reset();
		}
	}
	
	public void setStartTime(int startTime) {
		this.startTime = (float) startTime;
	}
	
	public void setCriticTime(int criticTime) {
		this.criticTime = (float) criticTime;
	}
}
