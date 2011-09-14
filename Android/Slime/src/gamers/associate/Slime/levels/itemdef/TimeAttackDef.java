package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.TimeAttackGame;

public class TimeAttackDef extends ItemDefinition {
	private static String Handled_TimeAttack = "TimeAttack";
	
	private int levelTime;
	private int criticTime;
	
	@Override
	public void createItem(Level level) {
		TimeAttackGame taGame = TimeAttackGame.NewGame();
		level.addGamePlay(taGame);
		taGame.setStartTime(this.levelTime);
		taGame.setCriticTime(this.criticTime);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_TimeAttack);
	}

	@Override
	protected void parseNext(String[] infos) {
		this.levelTime = Integer.valueOf(infos[5]).intValue();
		if (infos.length > 5) {
			this.criticTime = Integer.valueOf(infos[6]).intValue();
		}
		else {
			this.criticTime = 0;
		}
	}

}
