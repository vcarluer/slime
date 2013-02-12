package gamers.associate.SlimeAttack.game;

import android.util.FloatMath;
import gamers.associate.SlimeAttack.levels.GamePlay;

public class SurvivalGame extends TimeAttackGame {
	private static final int ultraBonusScore = 15000;
	
	public static SurvivalGame NewGame() {
		return new SurvivalGame(0, 0, 0, 0);				
	}
	
	public SurvivalGame(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void reset() {
		super.reset();
		if (this.level != null)	 {
			this.level.setHideCount(true);
		}
	}

	@Override
	public void render(float delta) {
	}
	
	@Override
	public GamePlay getType() {
		return GamePlay.Survival;
	}
	
	@Override
	public int getBaseScore() {
		return 0;
	}
	
	@Override
	public int getBonusScore(int bonusIdx) {
		float score = 0;
		if (bonusIdx <= Level.currentLevel.getGamePlay().neededBonus()) {
			score = this.getNormalBonus();
		} else {
			score = this.getExtraBonus();
		}
		
		return (int) FloatMath.ceil(score);
	}
	
	private float getExtraDiff() {
		return 1 + SlimeFactory.GameInfo.getDifficulty() / 10f;
	}
	
	private int getNormalBonus() {
		return (int) FloatMath.ceil(this.getBonusScore() * this.getExtraDiff());
	}
	
	private int getExtraBonus() {
		return (int) FloatMath.ceil(ultraBonusScore * this.getExtraDiff());
	}

	@Override
	public int getScore() {
		int bonus = this.bonusTaken;
		int needed = Level.currentLevel.getGamePlay().neededBonus();
		int sup = 0;
		if (bonus > needed) {
			sup = bonus - needed;
			bonus = needed;
		}
		
		return bonus * this.getNormalBonus() + sup * this.getExtraBonus();
	}

	@Override
	protected void lose() {
		
		super.lose();
	}
	
	@Override
	protected void startModeInternal() {
		SlimeFactory.GameInfo.setSurvivalGameOver(false);
	}
	
	@Override
	protected void endModeInternal() {
		
	}
}