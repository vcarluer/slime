package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.GamePlay;

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
		// TODO Auto-generated method stub
		super.render(delta);
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
		if (bonusIdx <= Level.currentLevel.getGamePlay().neededBonus()) {
			return getBonusScore();
		} else {
			return ultraBonusScore;
		}
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
		
		return bonus * TimeAttackGame.bonusScore + sup * ultraBonusScore;
	}
}