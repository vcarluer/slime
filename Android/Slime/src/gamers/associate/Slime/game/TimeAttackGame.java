package gamers.associate.Slime.game;

import java.text.DecimalFormat;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.ccColor3B;

import gamers.associate.Slime.items.base.GameItem;


public class TimeAttackGame extends GameItem implements IGamePlay {
	private static float defaultTime = 20;
	private static float defaultCritic = 10;
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
					if (this.leftTime < this.criticTime) {
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
					if (this.LocalRender >= 1f) {						
						this.level.setHudText(getFormatTimeCritic(this.leftTime));
						this.LocalRender = 0;
					}						
				}
				else {
					if (this.LocalRender >= 1f) {						
						this.level.setHudText(getFormatTime(this.leftTime));
						this.LocalRender = 0;
					}						
				}												
			}
		}
	}

	@Override
	public void start() {
		this.isStarted = true;
	}

	@Override
	public void setLevel(Level level) {
		this.level = level;
	}
}
